package panel.sup.cart;

import bean.ShopCartBean;
import mvp.BaseView;

import java.util.List;

/**
 * @author 赵洪苛
 * @date 2020/4/1 17:27
 * @description 购物车视图器
 */
public interface ShopCartView extends BaseView {

    void update(List<ShopCartBean> data);

    void applyDelete(List<ShopCartBean> data);

    void resetAll();

}
