package panel.user.parts;

import bean.PartBean;

import javax.swing.table.AbstractTableModel;
import java.util.List;

/**
 * @author 赵洪苛
 * @date 2020/3/24 15:58
 * @description 表格适配器
 */
public class SelectPartsAdapter extends AbstractTableModel {

    private List<PartBean> partBeans;

    private String[] headers = new String[]{"操作", "零件代码", "名称", "库存量", "单价"};

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
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        if (columnIndex == 0) {
            partBeans.get(rowIndex).setState((int) aValue);
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

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return columnIndex == 0;
    }

    @Override
    public String getColumnName(int columnIndex) {
        return headers[columnIndex];
    }
}
