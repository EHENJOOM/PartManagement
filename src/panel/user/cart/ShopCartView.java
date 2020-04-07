package panel.user.cart;

import bean.ShopCartBean;
import mvp.BaseView;

import java.util.List;

/**
 * @author 赵洪苛
 * @date 2020/3/24 22:36
 * @description 购物车视图
 */
public interface ShopCartView extends BaseView {

    void update(List<ShopCartBean> data);

    void applyDelete(List<ShopCartBean> data);

    void resetAll();

}
