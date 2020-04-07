package panel.admin.order;

import bean.OrderBean;
import mvp.BaseCallBack;
import mvp.BasePresenter;

import java.util.List;

/**
 * @author 赵洪苛
 * @date 2020/3/30 15:48
 * @description 订单逻辑处理器
 */
public class OrderManagePresenter extends BasePresenter<OrderManageView> {

    private OrderManageModel model = new OrderManageModel();

    public void select() {
        model.select(new BaseCallBack<List<OrderBean>>() {
            @Override
            public void onSucceed(List<OrderBean> data) {
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

    public void delete(List<OrderBean> data) {
        model.delete(data, new BaseCallBack<List<OrderBean>>() {
            @Override
            public void onSucceed(List<OrderBean> data) {
                if (isViewAttached()) {
                    getView().applyDelete(data);
                    getView().resetAll();
                    getView().showInfo("删除成功！");
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

    public void audit(int operation, List<OrderBean> data) {
        model.audit(operation, data, new BaseCallBack<List<OrderBean>>() {
            @Override
            public void onSucceed(List<OrderBean> data) {
                if (isViewAttached()) {
                    getView().resetAll();
                    getView().showInfo("操作成功！");
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

    public void save(List<OrderBean> data) {
        model.save(data, new BaseCallBack<List<OrderBean>>() {
            @Override
            public void onSucceed(List<OrderBean> data) {
                if (isViewAttached()) {
                    getView().showInfo("保存成功！");
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

    public void deliver(List<OrderBean> data) {
        model.delivery(data, new BaseCallBack<List<OrderBean>>() {
            @Override
            public void onSucceed(List<OrderBean> data) {
                if (isViewAttached()) {
                    getView().showInfo("发货成功！");
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
