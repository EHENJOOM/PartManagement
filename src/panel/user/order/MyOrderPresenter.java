package panel.user.order;

import bean.LoginBean;
import bean.OrderBean;
import constant.Config;
import mvp.BaseCallBack;
import mvp.BasePresenter;

import javax.swing.*;
import java.util.List;

/**
 * @author 赵洪苛
 * @date 2020/3/25 18:03
 * @description 订单逻辑处理器
 */
public class MyOrderPresenter extends BasePresenter<MyOrderView> {

    private MyOrderModel model = new MyOrderModel();

    public void select(LoginBean loginBean) {
        model.select(loginBean, new BaseCallBack<List<OrderBean>>() {
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

    public void delete(List<OrderBean> orderBeans) {
        if (!check(orderBeans)) {
            return;
        }
        model.delete(orderBeans, new BaseCallBack<List<OrderBean>>() {
            @Override
            public void onSucceed(List<OrderBean> data) {
                if (isViewAttached()) {
                    getView().applyDelete(data);
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

    public void pay(List<OrderBean> orderBeans) {
        if (!check(orderBeans)) {
            return;
        }
        double totalPrice = orderBeans.stream()
                .filter(temp -> temp.getStatus() == Config.ORDER_STATUS_UNPAID && temp.getState() == Config.SELECTED)
                .mapToDouble(temp -> temp.getNeedCount() * temp.getPartBean().getPrice().doubleValue())
                .sum();
        if (isViewAttached()) {
            if (JOptionPane.YES_OPTION == JOptionPane.showConfirmDialog(null, "您将总计支付￥ " + totalPrice + " 元(不含未通过审核的订单)，是否现在支付？", "提示", JOptionPane.YES_NO_OPTION)) {
                model.pay(orderBeans, new BaseCallBack<List<OrderBean>>() {
                    @Override
                    public void onSucceed(List<OrderBean> data) {
                        getView().showInfo("支付成功！");
                        getView().update(data);
                    }

                    @Override
                    public void onFailed(String msg) {
                        getView().showError(msg);
                    }
                });
            }
        }
    }

    private boolean check(List<OrderBean> orderBeans) {
        if (orderBeans.stream().filter(temp -> temp.getState() == Config.SELECTED).count() <= 0) {
            if (isViewAttached()) {
                getView().showInfo("请先选择项目！");
            }
            return false;
        }
        return true;
    }

}
