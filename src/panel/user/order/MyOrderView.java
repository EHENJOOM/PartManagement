package panel.user.order;

import bean.OrderBean;
import mvp.BaseView;

import java.util.List;

/**
 * @author 赵洪苛
 * @date 2020/3/25 18:03
 * @description 订单面板视图
 */
public interface MyOrderView extends BaseView {

    void update(List<OrderBean> orderBeans);

    void applyDelete(List<OrderBean> orderBeans);

}
