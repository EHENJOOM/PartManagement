package panel.admin.part;

import bean.PartBean;

import javax.swing.table.AbstractTableModel;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author 赵洪苛
 * @date 2020/3/30 11:54
 * @description 表格适配器
 */
public class PartManageAdapter extends AbstractTableModel {

    private String[] headers = {"选择", "代码", "名称", "库存", "价格"};

    private List<PartBean> partBeans;

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
                partBeans.get(rowIndex).setCount(Integer.parseInt((String) aValue));
                break;
            case 4:
                partBeans.get(rowIndex).setPrice(new BigDecimal((String) aValue));
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
