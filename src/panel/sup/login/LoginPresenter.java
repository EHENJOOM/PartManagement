package panel.sup.login;

import bean.LoginBean;
import mvp.BaseCallBack;
import mvp.BasePresenter;

import java.util.List;

/**
 * @author 赵洪苛
 * @date 2020/3/31 19:30
 * @description 登录逻辑处理器
 */
public class LoginPresenter extends BasePresenter<LoginView> {

    private LoginModel model = new LoginModel();

    public void select() {
        model.select(new BaseCallBack<List<LoginBean>>() {
            @Override
            public void onSucceed(List<LoginBean> data) {
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

    public void delete(List<LoginBean> data) {
        model.delete(data, new BaseCallBack<List<LoginBean>>() {
            @Override
            public void onSucceed(List<LoginBean> data) {
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

    public void save(List<LoginBean> data) {
        model.save(data, new BaseCallBack<List<LoginBean>>() {
            @Override
            public void onSucceed(List<LoginBean> data) {
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
