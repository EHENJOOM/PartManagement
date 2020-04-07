package login;

import bean.LoginBean;
import bean.UserBean;
import constant.Config;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.query.Query;
import thread.ThreadPoolEnum;
import util.SessionFactoryEnum;

/**
 * @author 赵洪苛
 * @date 2020/03/21 18:09
 * @description 登录的数据处理器
 */
public class LoginModel {

    /**
     * 采用线程池、数据库连接池技术从数据库中读出账户数据
     * @param loginBean 界面获取的账户数据
     * @param baseCallBack 数据读取的回调
     */
    public void getData(LoginBean loginBean, LoginCallBack<LoginBean> baseCallBack) {
        ThreadPoolEnum.getInstance().execute(() -> {
            // 从数据库连接池获取连接对象
            Session session = SessionFactoryEnum.getInstance().getSession();
            if (session == null) {
                return;
            }
            try {
                Query query = session.createQuery("from LoginBean login where login.account=:account and login.type=:type")
                    .setParameter("account", loginBean.getAccount())
                    .setParameter("type", loginBean.getType());
                if (query.list().size() <= 0) {
                    baseCallBack.onFailed("账号或密码不正确，请重新输入！");
                } else {
                    if (loginBean.getType() == Config.USER_LOGIN) {
                        UserBean userBean = (UserBean) session.createQuery("from UserBean userBean where userBean.loginBean.account=:account and userBean.loginBean.type=:type")
                            .setParameter("account", loginBean.getAccount())
                            .setParameter("type", loginBean.getType())
                            .uniqueResult();
                        Hibernate.initialize(userBean.getLoginBean());
                        if (userBean.getAddress() == null || userBean.getAddress().isEmpty() ||
                            userBean.getPhone() == null || userBean.getPhone().isEmpty() ||
                            userBean.getSex() == null || userBean.getSex().isEmpty()) {
                            baseCallBack.onInit(userBean);
                            return;
                        }
                    }
                    baseCallBack.onSucceed((LoginBean) query.uniqueResult());
                }
            } catch (Exception e) {
                e.printStackTrace();
                baseCallBack.onFailed("登录失败！");
            }
        });
    }

}
