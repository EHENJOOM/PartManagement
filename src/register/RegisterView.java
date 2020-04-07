package register;

import bean.LoginBean;
import mvp.BaseView;

/**
 * @author 赵洪苛
 * @date 2020/03/24 20:22
 * @description 注册或忘记密码视图器
 */
public interface RegisterView extends BaseView {

    /**
     * 显示确认信息窗口
     * @param msg 提示信息
     * @return 返回值为{@code YES_OPTION}则代表用户按下了确认键
     */
    int showConfirm(String msg);

    /**
     * 显示提示信息
     * @param msg 提示信息
     */
    void showMessage(String msg);

    /**
     * 转至调用presenter的注册接口
     * @param loginBean 账户信息
     */
    void toRegister(LoginBean loginBean);

    /**
     * 转至presenter的忘记密码接口
     * @param loginBean 账户信息
     */
    void toUpdatePassword(LoginBean loginBean);

    /**
     * 转至登录界面
     * @param loginBean 账户信息
     */
    void toLogin(LoginBean loginBean);

}
