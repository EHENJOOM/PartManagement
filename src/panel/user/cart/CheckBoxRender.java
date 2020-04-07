package panel.user.cart;

import constant.Config;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;

/**
 * @author 赵洪苛
 * @date 2020/3/25 11:54
 * @description 复选框渲染器
 */
public class CheckBoxRender extends JCheckBox implements TableCellRenderer {
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        if (Config.UNSELECTED == (int) value) {
            setSelected(false);
        } else {
            setSelected(true);
        }
        return this;
    }
}
