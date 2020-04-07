package panel.admin.order;

import bean.OrderBean;
import mvp.BaseView;

import java.util.List;

/**
 * @author 赵洪苛
 * @date 2020/3/30 15:47
 * @description 订单管理视图
 */
public interface OrderManageView extends BaseView {

    void update(List<OrderBean> data);

    void applyDelete(List<OrderBean> data);

    void resetAll();

}
