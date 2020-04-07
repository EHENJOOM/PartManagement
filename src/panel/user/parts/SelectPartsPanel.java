package panel.user.parts;

import bean.LoginBean;
import bean.PartBean;
import constant.Config;
import panel.user.cart.CheckBoxEditor;
import panel.user.cart.CheckBoxRender;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author 赵洪苛
 * @date 2020/3/24 15:54
 * @description 查询零件信息
 */
public class SelectPartsPanel extends JPanel implements SelectPartsView {

    private SelectPartsAdapter adapter;
    private JTable table;

    private LoginBean loginBean;
    private SelectPartsPresenter presenter = new SelectPartsPresenter();
    private List<PartBean> partBeans;

    private JTextField codeTextField;
    private JTextField nameTextField;
    private JButton selectAllButton;
    private int allState = Config.UNSELECTED;

    public SelectPartsPanel(LoginBean loginBean) {
        initView();
        presenter.attachView(this);
        presenter.select(loginBean);
        this.loginBean = loginBean;
    }

    private void initView() {
        setLayout(new BorderLayout());

        JScrollPane scrollPane = new JScrollPane();
        adapter = new SelectPartsAdapter();
        table = new JTable(adapter);
        table.getColumnModel().getColumn(0).setCellEditor(new CheckBoxEditor());
        table.getColumnModel().getColumn(0).setCellRenderer(new CheckBoxRender());
        scrollPane.setViewportView(table);
        add(scrollPane, BorderLayout.CENTER);

        JPanel headerPanel = new JPanel(new GridLayout(1, 5, 10, 20));
        JLabel codeLabel = new JLabel("代码");
        codeTextField = new JTextField();
        JLabel nameLabel = new JLabel("名称");
        nameTextField = new JTextField();
        JButton filterButton = new JButton("查找");
        codeLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        nameLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        filterButton.addActionListener(event -> filter());
        headerPanel.add(codeLabel);
        headerPanel.add(codeTextField);
        headerPanel.add(nameLabel);
        headerPanel.add(nameTextField);
        headerPanel.add(filterButton);

        Box box = Box.createHorizontalBox();
        selectAllButton = new JButton("全选");
        selectAllButton.addActionListener(event -> {
            if (allState == Config.UNSELECTED) {
                partBeans.forEach(temp -> temp.setState(Config.SELECTED));
                selectAllButton.setText("取消选中");
                allState = Config.SELECTED;
            } else {
                partBeans.forEach(temp -> temp.setState(Config.UNSELECTED));
                selectAllButton.setText("全选");
                allState = Config.UNSELECTED;
            }
            table.updateUI();
        });
        JButton deleteButton = new JButton("从购物车中移除");
        deleteButton.addActionListener(event -> presenter.deleteFromCart(loginBean, partBeans));
        JButton saveButton = new JButton("加入购物车");
        saveButton.addActionListener(event -> presenter.addIntoCart(loginBean, partBeans));
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
        String code = codeTextField.getText();
        String name = nameTextField.getText();
        List<PartBean> list = partBeans;

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
        adapter.setPartBeans(list);
        table.updateUI();
    }

    @Override
    public void update(List<PartBean> partBeans) {
        SwingUtilities.invokeLater(() -> {
            this.partBeans = partBeans;
            adapter.setPartBeans(partBeans);
            table.updateUI();
        });
    }

    @Override
    public void resetAll() {
        SwingUtilities.invokeLater(() -> {
            partBeans.forEach(temp -> temp.setState(Config.UNSELECTED));
            table.updateUI();
            allState = Config.UNSELECTED;
            selectAllButton.setText("全选");
        });
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        presenter.detachView();
    }
}
