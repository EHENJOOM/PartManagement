package panel.user.order;

import bean.OrderBean;
import constant.ConstantStatus;

import javax.swing.table.AbstractTableModel;
import java.util.List;

/**
 * @author 赵洪苛
 * @date 2020/3/25 18:04
 * @description 订单表格适配器
 */
public class MyOrderAdapter extends AbstractTableModel {

    private List<OrderBean> orderBeans;

    private String[] headers = {"选择", "零件代码", "名称", "单价", "购买数量", "用户代码", "姓名", "订单创建时间", "状态", "备注"};

    public void setOrderBeans(List<OrderBean> orderBeans) {
        this.orderBeans = orderBeans;
    }

    @Override
    public int getRowCount() {
        return orderBeans == null ? 0 : orderBeans.size();
    }

    @Override
    public int getColumnCount() {
        return headers.length;
    }

    @Override
    public String getColumnName(int column) {
        return headers[column];
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return columnIndex == 0;
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        if (columnIndex == 0) {
            orderBeans.get(rowIndex).setState((int) aValue);
        }
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        switch (columnIndex) {
            case 0:
                return orderBeans.get(rowIndex).getState();
            case 1:
                return orderBeans.get(rowIndex).getPartBean().getCode();
            case 2:
                return orderBeans.get(rowIndex).getPartBean().getName();
            case 3:
                return orderBeans.get(rowIndex).getPartBean().getPrice();
            case 4:
                return orderBeans.get(rowIndex).getNeedCount();
            case 5:
                return orderBeans.get(rowIndex).getUserBean().getCode();
            case 6:
                return orderBeans.get(rowIndex).getUserBean().getName();
            case 7:
                return (orderBeans.get(rowIndex).getOrderDate().getYear() + 1900) + "-" +
                        (orderBeans.get(rowIndex).getOrderDate().getMonth() + 1) + "-" +
                        orderBeans.get(rowIndex).getOrderDate().getDate();
            case 8:
                return ConstantStatus.getStatus(orderBeans.get(rowIndex).getStatus());
            case 9:
                return orderBeans.get(rowIndex).getExtend();
            default:
                return null;
        }
    }
}
