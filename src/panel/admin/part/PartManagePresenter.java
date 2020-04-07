package panel.admin.part;

import bean.PartBean;
import mvp.BaseCallBack;
import mvp.BasePresenter;

import java.util.List;

/**
 * @author 赵洪苛
 * @date 2020/3/30 11:52
 * @description 零件管理逻辑处理器
 */
public class PartManagePresenter extends BasePresenter<PartManageView> {

    private PartManageModel model = new PartManageModel();

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

    public void delete(List<PartBean> partBeans) {
        model.delete(partBeans, new BaseCallBack<List<PartBean>>() {
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
                getView().showError(msg);
            }
        });
    }

    public void save(List<PartBean> partBeans) {
        model.save(partBeans, new BaseCallBack<List<PartBean>>() {
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
