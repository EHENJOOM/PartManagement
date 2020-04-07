package panel.sup.order;

import bean.OrderBean;
import mvp.BaseView;

import java.util.List;

/**
 * @author 赵洪苛
 * @date 2020/4/1 17:36
 * @description 订单视图器
 */
public interface OrderView extends BaseView {

    void update(List<OrderBean> data);

    void applyDelete(List<OrderBean> data);

    void resetAll();
}
