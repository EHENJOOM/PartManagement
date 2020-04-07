package panel.admin.user;

import bean.UserBean;
import mvp.BaseView;

import java.util.List;

/**
 * @author 赵洪苛
 * @date 2020/3/30 15:06
 * @description 用户管理视图
 */
public interface UserManageView extends BaseView {

    void update(List<UserBean> data);

}
