package panel.user.cart;

import constant.Config;

import javax.swing.*;
import javax.swing.table.TableCellEditor;
import java.awt.*;

/**
 * @author 赵洪苛
 * @date 2020/3/25 11:54
 * @description 复选框编辑器
 */
public class CheckBoxEditor extends AbstractCellEditor implements TableCellEditor {

    private JCheckBox checkBox;
    private int state = Config.SELECTED;

    public CheckBoxEditor() {
        checkBox = new JCheckBox();
        checkBox.addChangeListener(event -> {
            if (checkBox.isSelected()) {
                state = Config.UNSELECTED;
            } else {
                state = Config.SELECTED;
            }

            fireEditingStopped();
        });
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        state = (int) value;
        return checkBox;
    }

    @Override
    public Object getCellEditorValue() {
        return state;
    }
}
