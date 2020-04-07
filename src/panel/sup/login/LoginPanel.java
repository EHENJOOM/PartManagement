package panel.sup.login;

import bean.LoginBean;
import constant.Config;
import panel.sup.order.ComboBoxEditor;
import panel.user.cart.CheckBoxEditor;
import panel.user.cart.CheckBoxRender;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author 赵洪苛
 * @date 2020/3/31 19:29
 * @description 登录账号面板
 */
public class LoginPanel extends JPanel implements LoginView {

    private JTable table;
    private LoginAdapter adapter;
    private JButton selectAllButton;
    private JTextField accountTextField;
    private JComboBox<String> typeComboBox;

    private int allState = Config.UNSELECTED;
    private List<LoginBean> data;
    private LoginPresenter presenter = new LoginPresenter();

    public LoginPanel() {
        initView();
        presenter.attachView(this);
        presenter.select();
    }

    private void initView() {
        setLayout(new BorderLayout());

        JScrollPane scrollPane = new JScrollPane();
        adapter = new LoginAdapter();
        table = new JTable(adapter);
        table.getColumnModel().getColumn(0).setCellEditor(new CheckBoxEditor());
        table.getColumnModel().getColumn(0).setCellRenderer(new CheckBoxRender());
        table.getColumnModel().getColumn(3).setCellEditor(new ComboBoxEditor(Config.IDENTITY_LOGIN));
        scrollPane.setViewportView(table);
        add(scrollPane, BorderLayout.CENTER);

        JPanel headerPanel = new JPanel(new GridLayout(1, 5, 10, 20));
        JLabel codeLabel = new JLabel("账号");
        accountTextField = new JTextField();
        JLabel typeLabel = new JLabel("账号类型");
        typeComboBox = new JComboBox<>(new String[]{null, "用户", "管理员", "超级管理员"});
        JButton filterButton = new JButton("查找");
        filterButton.addActionListener(event -> filter());
        codeLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        typeLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        headerPanel.add(codeLabel);
        headerPanel.add(accountTextField);
        headerPanel.add(typeLabel);
        headerPanel.add(typeComboBox);
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
        JButton newButton = new JButton("新建");
        newButton.addActionListener(event -> build());
        JButton deleteButton = new JButton("删除");
        deleteButton.addActionListener(event -> presenter.delete(data));
        JButton saveButton = new JButton("保存");
        saveButton.addActionListener(event -> presenter.save(data));
        box.add(Box.createHorizontalStrut(5));
        box.add(selectAllButton);
        box.add(Box.createHorizontalGlue());
        box.add(newButton);
        box.add(Box.createHorizontalStrut(10));
        box.add(deleteButton);
        box.add(Box.createHorizontalStrut(10));
        box.add(saveButton);
        box.add(Box.createHorizontalStrut(5));

        add(headerPanel, BorderLayout.NORTH);
        add(box, BorderLayout.SOUTH);
    }

    private void filter() {
        String account = accountTextField.getText();
        int type = typeComboBox.getSelectedIndex() - 1;
        List<LoginBean> list = data;

        if (account != null && !account.isEmpty()) {
            list = data.stream()
                .filter(temp -> temp.getAccount().contains(account) || temp.getAccount().equals(account))
                .collect(Collectors.toList());
        }
        if (type >= 0) {
            list = data.stream()
                .filter(temp -> temp.getType() == type)
                .collect(Collectors.toList());
        }

        if (list.size() <= 0) {
            showInfo("没有找到符合条件的记录！");
            return;
        }
        adapter.setLoginBeans(list);
        table.updateUI();
    }

    private void build() {
        LoginBean loginBean = new LoginBean();
        data.add(loginBean);
        table.updateUI();
    }

    @Override
    public void update(List<LoginBean> data) {
        SwingUtilities.invokeLater(() -> {
            this.data = data;
            adapter.setLoginBeans(data);
            table.updateUI();
        });
    }

    @Override
    public void applyDelete(List<LoginBean> data) {
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
