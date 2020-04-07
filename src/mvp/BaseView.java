package mvp;

import javax.swing.*;

/**
 * @author 赵洪苛
 * @date 2020/03/21 11:07
 * @description MVP设计模式，基础视图，窗口需实现此接口
 */
public interface BaseView {

    /**
     * 显示错误窗口
     * @param msg 错误信息
     */
    default void showError(String msg) {
        SwingUtilities.invokeLater(() -> JOptionPane.showMessageDialog(null, msg, "警告", JOptionPane.ERROR_MESSAGE));
    }

    /**
     * 显示提示信息窗口
     * @param info 提示信息
     */
    default void showInfo(String info) {
        SwingUtilities.invokeLater(() -> JOptionPane.showMessageDialog(null, info, "提示", JOptionPane.INFORMATION_MESSAGE));
    }

}
