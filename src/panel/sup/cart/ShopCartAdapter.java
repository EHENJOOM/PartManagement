package panel.sup.cart;

import bean.ShopCartBean;
import event.EventCenter;
import event.Events;

import javax.swing.table.AbstractTableModel;
import java.util.List;

/**
 * @author 赵洪苛
 * @date 2020/4/1 17:35
 * @description 购物车表格适配器
 */
public class ShopCartAdapter extends AbstractTableModel {

    private List<ShopCartBean> shopCartBeans;

    private String[] headers = {"操作", "零件代码", "名称", "单价", "订购数量", "用户代码", "姓名", "性别", "地址", "电话"};

    public void setShopCartBeans(List<ShopCartBean> shopCartBeans) {
        this.shopCartBeans = shopCartBeans;
    }

    @Override
    public String getColumnName(int column) {
        return headers[column];
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        switch (columnIndex) {
            case 0:
            case 4:
                return true;
            default:
                return false;
        }
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        switch (columnIndex) {
            case 0:
                shopCartBeans.get(rowIndex).setState((int) aValue);
                break;
            case 4:
                int needCount;
                try {
                    needCount = Integer.parseInt((String) aValue);
                } catch (Exception e) {
                    EventCenter.dispatchEvent(Events.ERROR_NUMBER_FORMAT, 0, 0, aValue);
                    return;
                }
                shopCartBeans.get(rowIndex).setNeedCount(needCount);
                break;
            default:
        }
    }

    @Override
    public int getRowCount() {
        return shopCartBeans == null ? 0 : shopCartBeans.size();
    }

    @Override
    public int getColumnCount() {
        return headers.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        switch (columnIndex) {
            case 0:
                return shopCartBeans.get(rowIndex).getState();
            case 1:
                return shopCartBeans.get(rowIndex).getPartBean().getCode();
            case 2:
                return shopCartBeans.get(rowIndex).getPartBean().getName();
            case 3:
                return shopCartBeans.get(rowIndex).getPartBean().getPrice();
            case 4:
                return shopCartBeans.get(rowIndex).getNeedCount();
            case 5:
                return shopCartBeans.get(rowIndex).getUserBean().getCode();
            case 6:
                return shopCartBeans.get(rowIndex).getUserBean().getName();
            case 7:
                return shopCartBeans.get(rowIndex).getUserBean().getSex();
            case 8:
                return shopCartBeans.get(rowIndex).getUserBean().getAddress();
            case 9:
                return shopCartBeans.get(rowIndex).getUserBean().getPhone();
            default:
                return null;
        }
    }
}
