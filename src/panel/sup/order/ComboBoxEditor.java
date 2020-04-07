package panel.sup.order;

import javax.swing.*;
import javax.swing.table.TableCellEditor;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.MouseEvent;
import java.util.EventObject;

/**
 * @author 赵洪苛
 * @date 2020/4/7 9:47
 * @description 下拉框编辑器
 */
public class ComboBoxEditor extends AbstractCellEditor implements TableCellEditor {

    private String str;

    private JComboBox<String> comboBox;

    private static final int CLICK_COUNT_TO_START = 1;

    public ComboBoxEditor(String[] items) {
        comboBox = new JComboBox<>();
        for (String item : items) {
            comboBox.addItem(item);
        }

        comboBox.addItemListener(event -> {
            if (event.getStateChange() == ItemEvent.SELECTED) {
                str =(String) comboBox.getSelectedItem();
                fireEditingStopped();
            }
        });
    }

    @Override
    public boolean isCellEditable(EventObject eventObject) {
        if (eventObject instanceof MouseEvent) {
            return ((MouseEvent)eventObject).getClickCount() >= CLICK_COUNT_TO_START;
        }
        return false;
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        str = (String) value;
        comboBox.setSelectedItem(str);
        return comboBox;
    }

    @Override
    public Object getCellEditorValue() {
        return str;
    }
}
