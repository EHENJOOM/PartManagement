package login;

import bean.LoginBean;
import bean.UserBean;
import mvp.BasePresenter;

/**
 * @author 赵洪苛
 * @date 2020/3/21 23:03
 * @description 登录事务处理器
 */
public class LoginPresenter extends BasePresenter<LoginView> {

    private LoginModel model = new LoginModel();

    public void login(LoginBean loginBean) {
        model.getData(loginBean, new LoginCallBack<LoginBean>() {
            @Override
            public void onInit(UserBean userBean) {
                if (isViewAttached()) {
                    getView().toInit(userBean);
                }
            }

            @Override
            public void onSucceed(LoginBean data) {
                if (loginBean.equals(data)) {
                    if (isViewAttached()) {
                        getView().login(data);
                    }
                } else {
                    if (isViewAttached()) {
                        getView().showError("用户名或密码错误！");
                    }
                }
            }

            @Override
            public void onFailed(String msg) {
                getView().showError(msg);
            }
        });
    }

}
