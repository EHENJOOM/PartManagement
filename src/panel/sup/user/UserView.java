package panel.sup.user;

import bean.UserBean;
import mvp.BaseView;

import java.util.List;

/**
 * @author 赵洪苛
 * @date 2020/3/31 19:31
 * @description 用户面板视图
 */
public interface UserView extends BaseView {

    void update(List<UserBean> data);

    void applyDelete(List<UserBean> data);

    void resetAll();

}
