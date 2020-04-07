package panel.sup.user;

import bean.UserBean;
import mvp.BaseCallBack;
import mvp.BasePresenter;

import java.util.List;

/**
 * @author 赵洪苛
 * @date 2020/3/31 19:31
 * @description 用户逻辑处理器
 */
public class UserPresenter extends BasePresenter<UserView> {

    private UserModel model = new UserModel();

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

    public void delete(List<UserBean> data) {
        model.delete(data, new BaseCallBack<List<UserBean>>() {
            @Override
            public void onSucceed(List<UserBean> data) {
                if (isViewAttached()) {
                    getView().showInfo("删除成功！");
                    getView().applyDelete(data);
                    getView().resetAll();
                }
            }

            @Override
            public void onFailed(String msg) {

            }
        });
    }

    public void save(List<UserBean> data) {
        model.save(data, new BaseCallBack<List<UserBean>>() {
            @Override
            public void onSucceed(List<UserBean> data) {
                if (isViewAttached()) {
                    getView().showInfo("保存成功！");
                    getView().resetAll();
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
