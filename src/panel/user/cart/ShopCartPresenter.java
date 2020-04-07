package panel.user.cart;

import bean.LoginBean;
import bean.ShopCartBean;
import constant.Config;
import mvp.BaseCallBack;
import mvp.BasePresenter;

import javax.swing.*;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

/**
 * @author 赵洪苛
 * @date 2020/3/24 22:37
 * @description 购物车逻辑处理器
 */
public class ShopCartPresenter extends BasePresenter<ShopCartView> {

    private ShopCartModel model = new ShopCartModel();

    public void update(LoginBean loginBean) {
        model.update(loginBean, new BaseCallBack<List<ShopCartBean>>() {
            @Override
            public void onSucceed(List<ShopCartBean> data) {
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

    public void delete(List<ShopCartBean> shopCartBeans) {
        if (check(shopCartBeans)) {
            return;
        }
        model.delete(shopCartBeans, new BaseCallBack<List<ShopCartBean>>() {
            @Override
            public void onSucceed(List<ShopCartBean> data) {
                if (isViewAttached()) {
                    getView().applyDelete(data);
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

    public void save(List<ShopCartBean> shopCartBeans) {
        if (check(shopCartBeans)) {
            return;
        }
        model.save(shopCartBeans, new BaseCallBack<List<ShopCartBean>>() {
            @Override
            public void onSucceed(List<ShopCartBean> data) {
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

    public void purchase(LoginBean loginBean, List<ShopCartBean> shopCartBeans) {
        if (check(shopCartBeans)) {
            return;
        }
        String day = JOptionPane.showInputDialog(null, "请按照 yyyy-MM-dd 的格式输入最晚付款日期", "输入日期", JOptionPane.PLAIN_MESSAGE);
        if (day == null || day.isEmpty()) {
            return;
        }
        Timestamp date;
        try {
            date = Timestamp.valueOf(day + " 00:00:00");
        } catch (Exception e) {
            getView().showError("日期格式不正确，请重新输入！");
            return;
        }
        model.purchase(loginBean, date, shopCartBeans, new BaseCallBack<List<ShopCartBean>>() {
            @Override
            public void onSucceed(List<ShopCartBean> data) {
                if (isViewAttached()) {
                    getView().showInfo("订单已提交，正在等待审核！");
                    getView().applyDelete(data);
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

    private boolean check(List<ShopCartBean> shopCartBeans) {
        if (shopCartBeans.stream().filter(temp -> temp.getState() == Config.SELECTED).count() <= 0) {
            if (isViewAttached()) {
                getView().showInfo("请先选择项目！");
            }
            return true;
        }
        return false;
    }

}
