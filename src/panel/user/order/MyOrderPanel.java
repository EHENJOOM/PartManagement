package panel.user.order;

import bean.LoginBean;
import bean.OrderBean;
import constant.Config;
import panel.user.cart.CheckBoxEditor;
import panel.user.cart.CheckBoxRender;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author 赵洪苛
 * @date 2020/3/25 18:02
 * @description 我的订单面板
 */
public class MyOrderPanel extends JPanel implements MyOrderView {

    private JTable table;
    private MyOrderAdapter adapter;
    private List<OrderBean> orderBeans;
    private int allState = Config.UNSELECTED;
    private JTextField codeTextField;
    private JTextField nameTextField;
    private JButton selectAllButton;

    private MyOrderPresenter presenter = new MyOrderPresenter();

    public MyOrderPanel(LoginBean loginBean) {
        initView();
        presenter.attachView(this);
        presenter.select(loginBean);
    }

    private void initView() {
        setLayout(new BorderLayout());

        JScrollPane scrollPane = new JScrollPane();
        adapter = new MyOrderAdapter();
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
                orderBeans.forEach(temp -> temp.setState(Config.SELECTED));
                selectAllButton.setText("取消选中");
                allState = Config.SELECTED;
            } else {
                orderBeans.forEach(temp -> temp.setState(Config.UNSELECTED));
                selectAllButton.setText("全选");
                allState = Config.UNSELECTED;
            }
            table.updateUI();
        });
        JButton deleteButton = new JButton("删除记录");
        deleteButton.addActionListener(event -> presenter.delete(orderBeans));
        JButton payButton = new JButton("支付");
        payButton.addActionListener(event -> presenter.pay(orderBeans));
        box.add(Box.createHorizontalStrut(5));
        box.add(selectAllButton);
        box.add(Box.createHorizontalGlue());
        box.add(deleteButton);
        box.add(Box.createHorizontalStrut(10));
        box.add(payButton);
        box.add(Box.createHorizontalStrut(5));
        add(headerPanel,BorderLayout.NORTH);
        add(box, BorderLayout.SOUTH);
    }

    private void filter() {
        String code = codeTextField.getText();
        String name = nameTextField.getText();
        List<OrderBean> list = orderBeans;

        if (code != null && !code.isEmpty()) {
            list = list.stream()
                    .filter(temp -> temp.getPartBean().getCode().contains(code) || temp.getPartBean().getCode().equals(code))
                    .collect(Collectors.toList());
        }
        if (name != null && !name.isEmpty()) {
            list = list.stream()
                    .filter(temp -> temp.getPartBean().getName().contains(name) || temp.getPartBean().getName().equals(name))
                    .collect(Collectors.toList());
        }

        if (list.size() <= 0) {
            showInfo("没有找到符合条件的记录！");
            return;
        }
        adapter.setOrderBeans(list);
        table.updateUI();
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        presenter.detachView();
    }

    @Override
    public void update(List<OrderBean> orderBeans) {
        SwingUtilities.invokeLater(() -> {
            this.orderBeans = orderBeans;
            adapter.setOrderBeans(orderBeans);
            table.updateUI();
        });
    }

    @Override
    public void applyDelete(List<OrderBean> orderBeans) {
        SwingUtilities.invokeLater(() -> {
            this.orderBeans.removeAll(orderBeans);
            table.updateUI();
        });
    }
}
