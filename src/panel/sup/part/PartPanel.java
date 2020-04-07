package panel.sup.part;

import bean.PartBean;
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
 * @date 2020/3/31 19:33
 * @description 零件管理面板
 */
public class PartPanel extends JPanel implements PartView, EventListener {

    private JTable table;
    private PartAdapter adapter;
    private JButton selectAllButton;
    private JTextField codeTextField;
    private JTextField partNameTextField;

    private int allState = Config.UNSELECTED;
    private List<PartBean> data;
    private PartPresenter presenter = new PartPresenter();

    public PartPanel() {
        initView();
        presenter.attachView(this);
        presenter.select();
        EventCenter.registerEventListener(this, Events.ERROR_NUMBER_FORMAT);
    }

    private void initView() {
        setLayout(new BorderLayout());

        JScrollPane scrollPane = new JScrollPane();
        adapter = new PartAdapter();
        table = new JTable(adapter);
        table.getColumnModel().getColumn(0).setCellEditor(new CheckBoxEditor());
        table.getColumnModel().getColumn(0).setCellRenderer(new CheckBoxRender());
        scrollPane.setViewportView(table);
        add(scrollPane, BorderLayout.CENTER);

        JPanel headerPanel = new JPanel(new GridLayout(1, 7, 10, 20));
        JLabel codeLabel = new JLabel("零件代码");
        codeTextField = new JTextField();
        JLabel partNameLabel = new JLabel("零件名称");
        partNameTextField = new JTextField();
        JButton filterButton = new JButton("查找");
        filterButton.addActionListener(event -> filter());
        codeLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        partNameLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        headerPanel.add(codeLabel);
        headerPanel.add(codeTextField);
        headerPanel.add(partNameLabel);
        headerPanel.add(partNameTextField);
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
        box.add(deleteButton);
        box.add(Box.createHorizontalStrut(10));
        box.add(saveButton);
        box.add(Box.createHorizontalStrut(5));

        add(headerPanel, BorderLayout.NORTH);
        add(box, BorderLayout.SOUTH);
    }

    private void filter() {
        List<PartBean> list = data;
        String code = codeTextField.getText();
        String partName = partNameTextField.getText();

        if (code != null && !code.isEmpty()) {
            list = list.stream()
                .filter(temp -> temp.getCode().contains(code) || temp.getCode().equals(code))
                .collect(Collectors.toList());
        }
        if (partName != null && !partName.isEmpty()) {
            list = list.stream()
                .filter(temp -> temp.getName().contains(partName) || temp.getName().equals(partName))
                .collect(Collectors.toList());
        }

        if (list.size() <= 0) {
            showInfo("没有找到符合条件的记录！");
            return;
        }
        adapter.setPartBeans(list);
        table.updateUI();
    }

    private void build() {
        PartBean partBean = new PartBean();
        data.add(partBean);
        table.updateUI();
    }

    @Override
    public void update(List<PartBean> data) {
        SwingUtilities.invokeLater(() -> {
            this.data = data;
            adapter.setPartBeans(data);
            table.updateUI();
        });
    }

    @Override
    public void applyDelete(List<PartBean> data) {
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
        EventCenter.unregisterEventListener(this, Events.ERROR_NUMBER_FORMAT);
    }

    @Override
    public void onEvent(String topic, int msgCode, int resultCode, Object object) {
        if (Events.ERROR_NUMBER_FORMAT.equals(topic)) {
            showError(object + " 不是正确的数字格式！");
        }
    }
}
