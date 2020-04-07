package panel.admin.order;

import bean.OrderBean;
import constant.ConstantStatus;

import javax.swing.table.AbstractTableModel;
import java.util.List;

/**
 * @author 赵洪苛
 * @date 2020/3/30 15:55
 * @description 表格适配器
 */
public class OrderManageAdapter extends AbstractTableModel {

    private String[] headers = {"选择", "零件代码", "名称", "单价", "客户姓名", "性别", "地址", "电话", "需求量", "订单创建日期", "最晚支付日期", "支付日期", "状态", "备注", "平均欠款时间(AAT)"};

    private List<OrderBean> orderBeans;

    public void setOrderBeans(List<OrderBean> orderBeans) {
        this.orderBeans = orderBeans;
    }

    @Override
    public int getRowCount() {
        return orderBeans == null ? 0 : orderBeans.size();
    }

    @Override
    public String getColumnName(int column) {
        return headers[column];
    }

    @Override
    public int getColumnCount() {
        return headers.length;
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        switch (columnIndex) {
            case 0:
            case 13:
                return true;
            default:
                return false;
        }
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        switch (columnIndex) {
            case 0:
                orderBeans.get(rowIndex).setState((int) aValue);
                break;
            case 13:
                orderBeans.get(rowIndex).setExtend((String) aValue);
                break;
            default:
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
                return orderBeans.get(rowIndex).getUserBean().getName();
            case 5:
                return orderBeans.get(rowIndex).getUserBean().getSex();
            case 6:
                return orderBeans.get(rowIndex).getUserBean().getAddress();
            case 7:
                return orderBeans.get(rowIndex).getUserBean().getPhone();
            case 8:
                return orderBeans.get(rowIndex).getNeedCount();
            case 9:
                return (orderBeans.get(rowIndex).getOrderDate().getYear() + 1900) + "-" +
                        (orderBeans.get(rowIndex).getOrderDate().getMonth() + 1) + "-" +
                        orderBeans.get(rowIndex).getOrderDate().getDate();
            case 10:
                return (orderBeans.get(rowIndex).getPaymentDate().getYear() + 1900) + "-" +
                        (orderBeans.get(rowIndex).getPaymentDate().getMonth() + 1) + "-" +
                        orderBeans.get(rowIndex).getPaymentDate().getDate();
            case 11:
                return orderBeans.get(rowIndex).getPayDate() == null ? null :
                    (orderBeans.get(rowIndex).getPayDate().getYear() + 1900) + "-" +
                        (orderBeans.get(rowIndex).getPayDate().getMonth() + 1) + "-" +
                        orderBeans.get(rowIndex).getPayDate().getDate();
            case 12:
                return ConstantStatus.getStatus(orderBeans.get(rowIndex).getStatus());
            case 13:
                return orderBeans.get(rowIndex).getExtend();
            case 14:
                return orderBeans.get(rowIndex).getSuggest();
            default:
                return null;
        }
    }
}
