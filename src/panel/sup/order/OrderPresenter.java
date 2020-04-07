package panel.sup.order;

import bean.OrderBean;
import mvp.BaseCallBack;
import mvp.BasePresenter;

import java.util.List;

/**
 * @author 赵洪苛
 * @date 2020/4/1 17:37
 * @description 订单逻辑处理器
 */
public class OrderPresenter extends BasePresenter<OrderView> {

    private OrderModel model = new OrderModel();

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

    public void save(List<OrderBean> data) {
        model.save(data, new BaseCallBack<List<OrderBean>>() {
            @Override
            public void onSucceed(List<OrderBean> data) {
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
