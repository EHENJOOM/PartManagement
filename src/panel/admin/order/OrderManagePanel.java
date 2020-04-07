package panel.admin.order;

import bean.OrderBean;
import constant.Config;
import panel.user.cart.CheckBoxEditor;
import panel.user.cart.CheckBoxRender;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * @author 赵洪苛
 * @date 2020/3/30 15:46
 * @description 订单管理面板
 */
public class OrderManagePanel extends JPanel implements OrderManageView {

    private JTable table;
    private OrderManageAdapter adapter;

    private JButton selectAllButton;
    private int allState = Config.UNSELECTED;
    private List<OrderBean> orderBeans;
    private OrderManagePresenter presenter = new OrderManagePresenter();

    public OrderManagePanel() {
        initView();
        presenter.attachView(this);
        presenter.select();
    }

    private void initView() {
        setLayout(new BorderLayout());

        JScrollPane scrollPane = new JScrollPane();
        adapter = new OrderManageAdapter();
        table = new JTable(adapter);
        table.getColumnModel().getColumn(0).setCellEditor(new CheckBoxEditor());
        table.getColumnModel().getColumn(0).setCellRenderer(new CheckBoxRender());
        scrollPane.setViewportView(table);
        add(scrollPane, BorderLayout.CENTER);

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

        JButton deleteButton = new JButton("删除");
        deleteButton.addActionListener(event -> presenter.delete(orderBeans));
        JButton saveButton = new JButton("保存备注");
        saveButton.addActionListener(event -> presenter.save(orderBeans));
        JButton acceptButton = new JButton("审核通过");
        acceptButton.addActionListener(event -> presenter.audit(Config.ORDER_STATUS_UNPAID, orderBeans));
        JButton refuseButton = new JButton("拒绝订单");
        refuseButton.addActionListener(event -> presenter.audit(Config.ORDER_STATUS_REFUSE_AUDIT, orderBeans));
        JButton deliveryButton = new JButton("发货");
        deliveryButton.addActionListener(event -> presenter.deliver(orderBeans));

        box.add(Box.createHorizontalStrut(5));
        box.add(selectAllButton);
        box.add(Box.createHorizontalGlue());
        box.add(deleteButton);
        box.add(Box.createHorizontalStrut(10));
        box.add(saveButton);
        box.add(Box.createHorizontalStrut(10));
        box.add(acceptButton);
        box.add(Box.createHorizontalStrut(10));
        box.add(refuseButton);
        box.add(Box.createHorizontalStrut(10));
        box.add(deliveryButton);
        box.add(Box.createHorizontalStrut(5));

        add(box, BorderLayout.SOUTH);
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        presenter.detachView();
    }

    @Override
    public void update(List<OrderBean> data) {
        SwingUtilities.invokeLater(() -> {
            this.orderBeans = data;
            adapter.setOrderBeans(data);
            table.updateUI();
        });
    }

    @Override
    public void applyDelete(List<OrderBean> data) {
        SwingUtilities.invokeLater(() -> {
            orderBeans.removeAll(data);
            table.updateUI();
        });
    }

    @Override
    public void resetAll() {
        SwingUtilities.invokeLater(() -> {
            orderBeans.forEach(temp -> temp.setState(Config.UNSELECTED));
            table.updateUI();
        });
    }
}
