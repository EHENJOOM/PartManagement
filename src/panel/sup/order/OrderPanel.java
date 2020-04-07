package panel.sup.order;

import bean.OrderBean;
import constant.Config;
import event.EventCenter;
import event.EventListener;
import event.Events;
import panel.user.cart.CheckBoxEditor;
import panel.user.cart.CheckBoxRender;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author 赵洪苛
 * @date 2020/4/1 17:35
 * @description 订单面板
 */
public class OrderPanel extends JPanel implements OrderView, EventListener {

    private JTable table;
    private OrderAdapter adapter;
    private JButton selectAllButton;
    private JTextField codeTextField;
    private JTextField partNameTextField;
    private JTextField nameTextField;

    private int allState = Config.UNSELECTED;
    private List<OrderBean> data;
    private OrderPresenter presenter = new OrderPresenter();

    private String[] topics = {Events.ERROR_DATE_FORMAT, Events.ERROR_NUMBER_FORMAT};

    public OrderPanel() {
        initView();
        presenter.attachView(this);
        presenter.select();
        EventCenter.registerEventListener(this, topics);
    }

    private void initView() {
        setLayout(new BorderLayout());

        JScrollPane scrollPane = new JScrollPane();
        adapter = new OrderAdapter();
        table = new JTable(adapter);
        table.getColumnModel().getColumn(0).setCellEditor(new CheckBoxEditor());
        table.getColumnModel().getColumn(0).setCellRenderer(new CheckBoxRender());
        table.getColumnModel().getColumn(12).setCellEditor(new ComboBoxEditor(Config.ORDER_STATUS));
        scrollPane.setViewportView(table);
        add(scrollPane, BorderLayout.CENTER);

        JPanel headerPanel = new JPanel(new GridLayout(1, 7, 10, 20));
        JLabel codeLabel = new JLabel("零件代码");
        codeTextField = new JTextField();
        JLabel partNameLabel = new JLabel("零件名称");
        partNameTextField = new JTextField();
        JLabel nameLabel = new JLabel("用户姓名");
        nameTextField = new JTextField();
        JButton filterButton = new JButton("查找");
        filterButton.addActionListener(event -> filter());
        codeLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        partNameLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        headerPanel.add(codeLabel);
        headerPanel.add(codeTextField);
        headerPanel.add(partNameLabel);
        headerPanel.add(partNameTextField);
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
        List<OrderBean> list = data;
        String code = codeTextField.getText();
        String partName = partNameTextField.getText();
        String name = nameTextField.getText();

        if (code != null && !code.isEmpty()) {
            list = list.stream()
                .filter(temp -> temp.getPartBean().getCode().contains(code) || temp.getPartBean().getCode().equals(code))
                .collect(Collectors.toList());
        }
        if (partName != null && !partName.isEmpty()) {
            list = list.stream()
                .filter(temp -> temp.getPartBean().getName().contains(partName) || temp.getPartBean().getName().equals(partName))
                .collect(Collectors.toList());
        }
        if (name != null && !name.isEmpty()) {
            list = list.stream()
                .filter(temp -> temp.getUserBean().getName().contains(name) || temp.getUserBean().getName().equals(name))
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
    public void update(List<OrderBean> data) {
        SwingUtilities.invokeLater(() -> {
            this.data = data;
            adapter.setOrderBeans(data);
            table.updateUI();
        });
    }

    @Override
    public void applyDelete(List<OrderBean> data) {
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

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        presenter.detachView();
        EventCenter.unregisterEventListener(this, topics);
    }

    @Override
    public void onEvent(String topic, int msgCode, int resultCode, Object object) {
        if (Events.ERROR_NUMBER_FORMAT.equals(topic)) {
            showError(object + " 不是正确的数字格式！");
        } else if (Events.ERROR_DATE_FORMAT.equals(topic)) {
            showError(object + "不是正确的日期格式！\n请按照 yyyy-MM-dd 的格式输入！");
        }
    }
}
