package panel.sup.part;

import bean.PartBean;
import mvp.BaseCallBack;
import mvp.BasePresenter;

import java.util.List;

/**
 * @author 赵洪苛
 * @date 2020/3/31 19:34
 * @description 零件管理逻辑视图
 */
public class PartPresenter extends BasePresenter<PartView> {

    private PartModel model = new PartModel();

    public void select() {
        model.select(new BaseCallBack<List<PartBean>>() {
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

    public void delete(List<PartBean> data) {
        model.delete(data, new BaseCallBack<List<PartBean>>() {
            @Override
            public void onSucceed(List<PartBean> data) {
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

    public void save(List<PartBean> data) {
        model.save(data, new BaseCallBack<List<PartBean>>() {
            @Override
            public void onSucceed(List<PartBean> data) {
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
