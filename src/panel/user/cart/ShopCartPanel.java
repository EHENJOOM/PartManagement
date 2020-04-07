package panel.user.cart;

import bean.LoginBean;
import bean.ShopCartBean;
import constant.Config;
import event.EventCenter;
import event.EventListener;
import event.Events;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author 赵洪苛
 * @date 2020/3/24 22:35
 * @description 购物车面板
 */
public class ShopCartPanel extends JPanel implements ShopCartView, EventListener {

    private JTable table;
    private LoginBean loginBean;
    private ShopCartAdapter adapter;
    private List<ShopCartBean> shopCartBeans;
    private int allState = Config.UNSELECTED;
    private JTextField codeTextField;
    private JTextField nameTextField;
    private JButton selectAllButton;
    private ShopCartPresenter presenter = new ShopCartPresenter();

    public ShopCartPanel(LoginBean loginBean) {
        initView();
        presenter.attachView(this);
        presenter.update(loginBean);
        this.loginBean = loginBean;
        EventCenter.registerEventListener(this, Events.CANNOT_CAST_STRING_TO_INT);
    }

    private void initView() {
        setLayout(new BorderLayout());

        JScrollPane scrollPane = new JScrollPane();
        adapter = new ShopCartAdapter();
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
                shopCartBeans.forEach(temp -> temp.setState(Config.SELECTED));
                selectAllButton.setText("取消选中");
                allState = Config.SELECTED;
            } else {
                shopCartBeans.forEach(temp -> temp.setState(Config.UNSELECTED));
                selectAllButton.setText("全选");
                allState = Config.UNSELECTED;
            }
            table.updateUI();
        });
        JButton deleteButton = new JButton("移除");
        deleteButton.addActionListener(event -> presenter.delete(shopCartBeans));
        JButton saveButton = new JButton("保存");
        saveButton.addActionListener(event -> presenter.save(shopCartBeans));
        JButton purchaseButton = new JButton("提交合同");
        purchaseButton.addActionListener(event -> presenter.purchase(loginBean, shopCartBeans));
        box.add(Box.createHorizontalStrut(5));
        box.add(selectAllButton);
        box.add(Box.createHorizontalGlue());
        box.add(deleteButton);
        box.add(Box.createHorizontalStrut(10));
        box.add(saveButton);
        box.add(Box.createHorizontalStrut(10));
        box.add(purchaseButton);
        box.add(Box.createHorizontalStrut(5));

        add(headerPanel, BorderLayout.NORTH);
        add(box, BorderLayout.SOUTH);
    }

    private void filter() {
        String code = codeTextField.getText();
        String name = nameTextField.getText();
        List<ShopCartBean> list = shopCartBeans;

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
        adapter.setShopCartBeans(list);
        table.updateUI();
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        presenter.detachView();
        EventCenter.unregisterEventListener(this, Events.CANNOT_CAST_STRING_TO_INT);
    }

    @Override
    public void update(List<ShopCartBean> data) {
        SwingUtilities.invokeLater(() -> {
            this.shopCartBeans = data;
            adapter.setShopCartBeans(data);
            table.updateUI();
        });
    }

    @Override
    public void applyDelete(List<ShopCartBean> data) {
        SwingUtilities.invokeLater(() -> {
            shopCartBeans.removeAll(data);
            adapter.setShopCartBeans(shopCartBeans);
            table.updateUI();
        });
    }

    @Override
    public void resetAll() {
        SwingUtilities.invokeLater(() -> {
            shopCartBeans.forEach(temp -> temp.setState(Config.UNSELECTED));
            table.updateUI();
            allState = Config.UNSELECTED;
            selectAllButton.setText("全选");
        });
    }

    @Override
    public void onEvent(String topic, int msgCode, int resultCode, Object object) {
        switch (topic) {
            case Events.CANNOT_CAST_STRING_TO_INT:
                this.showInfo("请输入正确的数字！");
                break;
            default:
        }
    }
}
