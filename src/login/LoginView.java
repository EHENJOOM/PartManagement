package login;

import bean.LoginBean;
import bean.UserBean;
import mvp.BaseView;

/**
 * @author 赵洪苛
 * @date 2019/11/21 18:05
 * @description 登录视图
 */
public interface LoginView extends BaseView {

    /**
     * 登录成功，跳转至主界面
     * @param loginBean 用户参数
     */
    void login(LoginBean loginBean);

    void toInit(UserBean userBean);
}
