package panel.user.parts;

import bean.LoginBean;
import bean.PartBean;
import constant.Config;
import mvp.BaseCallBack;
import mvp.BasePresenter;

import java.util.List;

/**
 * @author 赵洪苛
 * @date 2020/3/24 15:56
 * @description 查询零件处理器
 */
public class SelectPartsPresenter extends BasePresenter<SelectPartsView> {

    private SelectPartsModel model = new SelectPartsModel();

    public void select(LoginBean loginBean) {
        model.select(loginBean, new BaseCallBack<List<PartBean>>() {
            @Override
            public void onSucceed(List<PartBean> data) {
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

    public void addIntoCart(LoginBean loginBean, List<PartBean> partBeans) {
        if (!check(partBeans)) {
            return;
        }
        model.addIntoCart(loginBean, partBeans, new BaseCallBack<String>() {
            @Override
            public void onSucceed(String data) {
                if (isViewAttached()) {
                    getView().showInfo(data);
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

    public void deleteFromCart(LoginBean loginBean, List<PartBean> partBeans) {
        if (!check(partBeans)) {
            return;
        }
        model.deleteFromCart(loginBean, partBeans, new BaseCallBack<String>() {
            @Override
            public void onSucceed(String data) {
                if (isViewAttached()) {
                    getView().showInfo(data);
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

    private boolean check(List<PartBean> partBeans) {
        if (partBeans.stream().filter(temp -> temp.getState() == Config.SELECTED).count() <= 0) {
            if (isViewAttached()) {
                getView().showInfo("请先选择项目！");
            }
            return false;
        }
        return true;
    }

}
