package panel.admin.user;

import bean.UserBean;
import mvp.BaseCallBack;
import mvp.BasePresenter;

import java.util.List;

/**
 * @author 赵洪苛
 * @date 2020/3/30 15:06
 * @description 用户管理逻辑处理器
 */
public class UserManagePresenter extends BasePresenter<UserManageView> {

    private UserManageModel model = new UserManageModel();

    public void select() {
        model.select(new BaseCallBack<List<UserBean>>() {
            @Override
            public void onSucceed(List<UserBean> data) {
                if (isViewAttached()) {
                    getView().update(data);
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
