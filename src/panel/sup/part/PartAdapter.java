package panel.sup.part;

import bean.PartBean;
import event.EventCenter;
import event.Events;

import javax.swing.table.AbstractTableModel;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author 赵洪苛
 * @date 2020/3/31 19:35
 * @description 零件表格适配器
 */
public class PartAdapter extends AbstractTableModel {

    private List<PartBean> partBeans;

    private String[] headers = {"选择", "代码", "名称", "库存", "价格"};

    public void setPartBeans(List<PartBean> partBeans) {
        this.partBeans = partBeans;
    }

    @Override
    public int getRowCount() {
        return partBeans == null ? 0 : partBeans.size();
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
        return true;
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        switch (columnIndex) {
            case 0:
                partBeans.get(rowIndex).setState((int) aValue);
                break;
            case 1:
                partBeans.get(rowIndex).setCode((String) aValue);
                break;
            case 2:
                partBeans.get(rowIndex).setName((String) aValue);
                break;
            case 3:
                int needCount;
                try {
                    needCount = Integer.parseInt((String) aValue);
                } catch (Exception e) {
                    EventCenter.dispatchEvent(Events.ERROR_NUMBER_FORMAT, 0, 0, aValue);
                    return;
                }
                partBeans.get(rowIndex).setCount(needCount);
                break;
            case 4:
                BigDecimal decimal;
                try {
                    decimal = new BigDecimal((String) aValue);
                } catch (Exception e) {
                    EventCenter.dispatchEvent(Events.ERROR_NUMBER_FORMAT, 0, 0, aValue);
                    return;
                }
                partBeans.get(rowIndex).setPrice(decimal);
                break;
            default:
        }
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        switch (columnIndex) {
            case 0:
                return partBeans.get(rowIndex).getState();
            case 1:
                return partBeans.get(rowIndex).getCode();
            case 2:
                return partBeans.get(rowIndex).getName();
            case 3:
                return partBeans.get(rowIndex).getCount();
            case 4:
                return partBeans.get(rowIndex).getPrice();
            default:
                return null;
        }
    }
}
