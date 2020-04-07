package panel.sup.login;

import bean.LoginBean;
import mvp.BaseView;

import java.util.List;

/**
 * @author 赵洪苛
 * @date 2020/3/31 19:29
 * @description 登录面板视图
 */
public interface LoginView extends BaseView {

    void update(List<LoginBean> data);

    void applyDelete(List<LoginBean> data);

    void resetAll();

}
