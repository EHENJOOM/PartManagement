package panel.user.cart;

import bean.ShopCartBean;
import constant.Config;
import event.EventCenter;
import event.Events;

import javax.swing.table.AbstractTableModel;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author 赵洪苛
 * @date 2020/3/24 22:38
 * @description 表格适配器
 */
public class ShopCartAdapter extends AbstractTableModel {

    private List<ShopCartBean> shopCartBeans;

    private String[] headers = new String[]{"操作", "零件代码", "名字", "数量"};

    public void setShopCartBeans(List<ShopCartBean> shopCartBeans) {
        this.shopCartBeans = shopCartBeans;
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
    public String getColumnName(int column) {
        return headers[column];
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        switch (columnIndex) {
            case 0:
                shopCartBeans.get(rowIndex).setState((int) aValue);
                break;
            case 3:
                Pattern pattern = Pattern.compile("^[+]{0,1}(\\d+)$");
                Matcher matcher = pattern.matcher((String) aValue);
                if (!matcher.matches()) {
                    EventCenter.dispatchEvent(Events.CANNOT_CAST_STRING_TO_INT, 0, 0, null);
                    return;
                }
                shopCartBeans.get(rowIndex).setNeedCount(Integer.parseInt((String) aValue));
                break;
            default:
        }
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return columnIndex == 3 || columnIndex == 0;
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
                return shopCartBeans.get(rowIndex).getNeedCount();
            default:
                return null;
        }
    }
}
