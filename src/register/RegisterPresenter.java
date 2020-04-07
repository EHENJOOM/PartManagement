package register;

import bean.LoginBean;
import constant.Config;
import mvp.BaseCallBack;
import mvp.BasePresenter;

import javax.swing.*;

/**
 * @author 赵洪苛
 * @date 2020/03/24 20:18
 * @description 注册或忘记密码逻辑处理器
 */
public class RegisterPresenter extends BasePresenter<RegisterView> {

    private RegisterModel model = new RegisterModel();

    /**
     *
     * @param type
     * @param account
     * @param suffix
     */
    public void sendVerifyCode(int type, String account, String suffix) {
        model.sendVerifyCode(type, account, suffix, new BaseCallBack<String>() {
            @Override
            public void onSucceed(String data) {
                if (isViewAttached()) {
                    getView().showMessage("验证码已发送至您的邮箱，请注意查收！");
                }
            }

            @Override
            public void onFailed(String msg) {
                if (isViewAttached()) {
                    getView().showError(msg);
                }
            }
        });
    }

    /**
     *
     * @param type
     * @param verifyCode
     * @param loginBean
     */
    public void verify(int type, String verifyCode, LoginBean loginBean) {
        model.verify(type, loginBean.getAccount(), verifyCode, new BaseCallBack<String>() {
            @Override
            public void onSucceed(String data) {
                if (isViewAttached()) {
                    if (type == Config.REGISTER_DIALOG) {
                        getView().toRegister(loginBean);
                    } else if (type == Config.FORGET_PASSWORD_DIALOG) {
                        getView().toUpdatePassword(loginBean);
                    }
                }
            }

            @Override
            public void onFailed(String msg) {
                if (isViewAttached()) {
                    getView().showError(msg);
                }
            }
        });
    }

    /**
     * @see RegisterModel#updatePassword(LoginBean, BaseCallBack)
     * @param loginBean 账户数据
     */
    public void updatePassword(LoginBean loginBean) {
        model.updatePassword(loginBean, new BaseCallBack<LoginBean>() {
            @Override
            public void onSucceed(LoginBean data) {
                if (isViewAttached()) {
                    if (getView().showConfirm("密码修改成功。\n快去登录吧！") == JOptionPane.YES_OPTION) {
                        getView().toLogin(data);
                    }
                }
            }

            @Override
            public void onFailed(String msg) {
                if (isViewAttached()) {
                    getView().showError(msg);
                }
            }
        });
    }

    /**
     * @param loginBean 账户数据
     */
    public void register(String name, LoginBean loginBean) {
        model.register(name, loginBean, new BaseCallBack<LoginBean>() {
            @Override
            public void onSucceed(LoginBean data) {
                if (isViewAttached()) {
                    if (getView().showConfirm("账号注册成功！\n您的登录账号为邮箱后缀之前的部分！") == JOptionPane.YES_OPTION) {
                        getView().toLogin(loginBean);
                    }
                }
            }

            @Override
            public void onFailed(String msg) {
                if (isViewAttached()) {
                    getView().showError(msg);
                }
            }
        });
    }

}
