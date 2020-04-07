package panel.sup.user;

import bean.ShopCartBean;
import bean.UserBean;
import constant.Config;
import panel.sup.order.ComboBoxEditor;
import panel.user.cart.CheckBoxEditor;
import panel.user.cart.CheckBoxRender;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author 赵洪苛
 * @date 2020/3/31 19:31
 * @description 用户面板
 */
public class UserPanel extends JPanel implements UserView {

    private JTable table;
    private UserAdapter adapter;
    private JButton selectAllButton;
    private JTextField codeTextField;
    private JTextField nameTextField;

    private int allState = Config.UNSELECTED;
    private List<UserBean> data;
    private UserPresenter presenter = new UserPresenter();

    public UserPanel() {
        initView();
        presenter.attachView(this);
        presenter.select();
    }

    private void initView() {
        setLayout(new BorderLayout());

        JScrollPane scrollPane = new JScrollPane();
        adapter = new UserAdapter();
        table = new JTable(adapter);
        table.getColumnModel().getColumn(0).setCellEditor(new CheckBoxEditor());
        table.getColumnModel().getColumn(0).setCellRenderer(new CheckBoxRender());
        table.getColumnModel().getColumn(4).setCellEditor(new ComboBoxEditor(Config.SEX));
        scrollPane.setViewportView(table);
        add(scrollPane, BorderLayout.CENTER);

        JPanel headerPanel = new JPanel(new GridLayout(1, 7, 10, 20));
        JLabel codeLabel = new JLabel("用户代码");
        codeTextField = new JTextField();
        JLabel nameLabel = new JLabel("用户姓名");
        nameTextField = new JTextField();
        JButton filterButton = new JButton("查找");
        filterButton.addActionListener(event -> filter());
        codeLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        nameLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        headerPanel.add(codeLabel);
        headerPanel.add(codeTextField);
        headerPanel.add(nameLabel);
        headerPanel.add(nameTextField);
        headerPanel.add(filterButton);

        Box box = Box.createHorizontalBox();
        selectAllButton = new JButton("全选");
        selectAllButton.addActionListener(event -> {
            if (allState == Config.UNSELECTED) {
                selectAllButton.setText("取消全选");
                allState = Config.SELECTED;
                data.forEach(temp -> temp.setState(Config.SELECTED));
            } else {
                selectAllButton.setText("全选");
                allState = Config.UNSELECTED;
                data.forEach(temp -> temp.setState(Config.UNSELECTED));
            }
            table.updateUI();
        });
        JButton deleteButton = new JButton("删除");
        deleteButton.addActionListener(event -> presenter.delete(data));
        JButton saveButton = new JButton("保存");
        saveButton.addActionListener(event -> presenter.save(data));
        box.add(Box.createHorizontalStrut(5));
        box.add(selectAllButton);
        box.add(Box.createHorizontalGlue());
        box.add(deleteButton);
        box.add(Box.createHorizontalStrut(10));
        box.add(saveButton);
        box.add(Box.createHorizontalStrut(5));

        add(headerPanel, BorderLayout.NORTH);
        add(box, BorderLayout.SOUTH);
    }

    private void filter() {
        List<UserBean> list = data;
        String code = codeTextField.getText();
        String name = nameTextField.getText();

        if (code != null && !code.isEmpty()) {
            list = list.stream()
                .filter(temp -> temp.getCode().contains(code) || temp.getCode().equals(code))
                .collect(Collectors.toList());
        }
        if (name != null && !name.isEmpty()) {
            list = list.stream()
                .filter(temp -> temp.getName().contains(name) || temp.getName().equals(name))
                .collect(Collectors.toList());
        }

        if (list.size() <= 0) {
            showInfo("没有找到符合条件的记录！");
            return;
        }
        adapter.setUserBeans(list);
        table.updateUI();
    }

    @Override
    public void update(List<UserBean> data) {
        SwingUtilities.invokeLater(() -> {
            this.data = data;
            adapter.setUserBeans(data);
            table.updateUI();
        });
    }

    @Override
    public void applyDelete(List<UserBean> data) {
        SwingUtilities.invokeLater(() -> {
            this.data.removeAll(data.stream().filter(temp -> temp.getState() == Config.SELECTED).collect(Collectors.toList()));
            table.updateUI();
        });
    }

    @Override
    public void resetAll() {
        SwingUtilities.invokeLater(() -> {
            data.forEach(temp -> temp.setState(Config.UNSELECTED));
            table.updateUI();
        });
    }
}
