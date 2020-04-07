package register;

import bean.LoginBean;
import bean.UserBean;
import bean.VerifyBean;
import constant.Config;
import email.EmailBean;
import email.EmailSenderFactory;
import email.MessageSender;
import mvp.BaseCallBack;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import thread.ThreadPoolEnum;
import util.SessionFactoryEnum;

import java.util.*;

/**
 * @author 赵洪苛
 * @date 2020/03/24 20:20
 * @description 注册或忘记密码数据处理器
 */
public class RegisterModel {

    /**
     * 向指定账号发送验证码
     * @param type 验证码类型
     * @param account 邮箱账号
     * @param baseCallBack 发送邮件回调
     */
    public void sendVerifyCode(int type, String account, String suffix, BaseCallBack<String> baseCallBack) {
        ThreadPoolEnum.getInstance().execute(() -> {
            Session session = SessionFactoryEnum.getInstance().getSession();
            if (session == null) {
                return;
            }

            String verifyCode = UUID.randomUUID().toString();
            VerifyBean verifyBean = new VerifyBean(account, verifyCode.substring(2, 8), System.currentTimeMillis(), type);

            EmailBean emailBean = new EmailBean();
            emailBean.setSuffix(suffix);
            emailBean.setReceiveAccount(account);
            emailBean.setDate(new Date());
            if (type == Config.REGISTER_DIALOG) {
                emailBean.setSubject("注册账号验证码");
                emailBean.setContent("尊敬的用户" + account + "：\n\t您好！\n\t您正在使用“零件管理系统”，您正在注册账号，现在是验证您的身份是否合法。\n\t您的验证码是 "
                        + verifyBean.getCode() + " ，此验证码有效期为 1 分钟。为了您的个人信息安全，请不要将此验证码告知他人。\n\t此邮件为系统发送，请不要回复！\n谢谢！");
            } else if (type == Config.FORGET_PASSWORD_DIALOG) {
                emailBean.setSubject("忘记密码验证码");
                emailBean.setContent("尊敬的用户" + account + "：\n\t您好！\n\t您正在使用“零件管理系统”，您正在更改密码，现在是验证您的身份是否合法。\n\t您的验证码是 "
                        + verifyBean.getCode() + " ，此验证码有效期为 1 分钟。为了您的个人信息安全，请不要将此验证码告知他人。\n\t此邮件为系统发送，请不要回复！\n谢谢！");
            }

            try {
                // 发送验证码邮件
                MessageSender messageSender = EmailSenderFactory.getDefaultEmailSender();
                messageSender.init();
                messageSender.setEmailContent(emailBean);
                messageSender.sendMessage();
                baseCallBack.onSucceed("验证码已发送，请前往您的邮箱查看！");

                // 向数据库中写入验证码
                Transaction transaction = session.beginTransaction();
                Query query = session.createQuery("from VerifyBean verify where verify.account=:account and verify.type=:type")
                    .setParameter("account", account)
                    .setParameter("type", type);
                List<VerifyBean> list = query.list();
                if (list.size() > 0) {
                    session.createQuery("update VerifyBean verify set verify.code=:code, verify.timestamp=:time where verify.account=:account and verify.type=:type")
                        .setParameter("code", verifyBean.getCode())
                        .setParameter("time", verifyBean.getTimestamp())
                        .setParameter("account", verifyBean.getAccount())
                        .setParameter("type", type)
                        .executeUpdate();
                } else {
                    session.save(verifyBean);
                }
                transaction.commit();
                session.close();
            } catch (Exception e) {
                e.printStackTrace();
                baseCallBack.onFailed("验证码写入错误！");
            }
        });
    }

    /**
     * 验证验证码是否合法
     * @param account 账号
     * @param verifyCode 验证码
     * @param baseCallBack 验证回调
     */
    public void verify(int type, String account, String verifyCode, BaseCallBack<String> baseCallBack) {
        Session session = SessionFactoryEnum.getInstance().getSession();
        if (session == null) {
            return;
        }
        try {
            List<VerifyBean> data = session.createQuery("from VerifyBean verify where verify.account=:account and verify.type=:type")
                .setParameter("account", account)
                .setParameter("type", type)
                .list();
            // 查看数据库中是否有验证码
            if (data.size() <= 0) {
                baseCallBack.onFailed("该验证码已超时，请重新获取验证码！");
                return;
            }
            // 计算该验证码是否超时
            if (System.currentTimeMillis() - data.get(0).getTimestamp() > Config.VERIFY_CODE_TIME_OUT) {
                session.delete(data.get(0));
                baseCallBack.onFailed("该验证码已超时，请重新获取验证码！");
                return;
            }
            // 验证验证码是否匹配
            if (!verifyCode.equals(data.get(0).getCode())) {
                baseCallBack.onFailed("验证码错误，请重新输入！");
                return;
            }
            baseCallBack.onSucceed("验证码正确");
        } catch (Exception e) {
            baseCallBack.onFailed("验证失败！");
        }
    }

    /**
     * 更新数据库中的登录密码
     * @param loginBean 账户信息
     * @param baseCallBack 更新回调
     */
    public void updatePassword(LoginBean loginBean, BaseCallBack<LoginBean> baseCallBack) {
        ThreadPoolEnum.getInstance().execute(() -> {
            Session session = SessionFactoryEnum.getInstance().getSession();
            if (session == null) {
                return;
            }
            Transaction transaction = session.beginTransaction();
            try {
                Query query = session.createQuery("from LoginBean login where login.account =:account");
                query.setParameter("account", loginBean.getAccount());
                LoginBean updateLoginBean;
                if (query.list().size() <= 0) {
                    baseCallBack.onFailed("该账号尚未注册，请先返回注册！");
                    return;
                } else {
                    updateLoginBean = (LoginBean) query.list().get(0);
                    updateLoginBean.setPassword(loginBean.getPassword());
                    session.update(updateLoginBean);
                }
                transaction.commit();
                baseCallBack.onSucceed(updateLoginBean);
            } catch (Exception e) {
                if (transaction != null) {
                    transaction.rollback();
                }
                baseCallBack.onFailed("忘记密码失败，请稍后重试！");
            } finally {
                session.close();
            }

        });
    }

    /**
     * 向数据库中写入新账户信息
     * @param loginBean 账户信息
     * @param baseCallBack 回调
     */
    public void register(String name, LoginBean loginBean, BaseCallBack<LoginBean> baseCallBack) {
        ThreadPoolEnum.getInstance().execute(() -> {
            Session session = SessionFactoryEnum.getInstance().getSession();
            if (session == null) {
                return;
            }
            Query query = session.createQuery("from LoginBean login where login.account=:account").setParameter("account", loginBean.getAccount());
            if (query.list().size() > 0) {
                baseCallBack.onFailed("该账号已被注册，请更换账号重新注册！");
                return;
            }

            Transaction transaction = null;
            try {
                transaction = session.beginTransaction();
                UserBean userBean = new UserBean();
                userBean.setLoginBean(loginBean);
                userBean.setName(name);
                userBean.setCode("USER" + UUID.randomUUID().toString().substring(4, 10).toUpperCase());
                session.save(loginBean);
                session.save(userBean);
                transaction.commit();
                baseCallBack.onSucceed(loginBean);
            } catch (Exception e) {
                if (transaction != null) {
                    transaction.rollback();
                }
                e.printStackTrace();
                baseCallBack.onFailed("注册失败，请重新注册！");
            } finally {
                session.close();
            }
        });
    }

}
