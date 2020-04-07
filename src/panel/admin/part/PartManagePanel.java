package panel.admin.part;

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
 * @date 2020/3/30 11:51
 * @description 零件管理面板
 */
public class PartManagePanel extends JPanel implements PartManageView {

    private JTable table;
    private PartManageAdapter adapter;
    private List<PartBean> partBeans;
    private JButton selectAllButton;
    private int allState = Config.UNSELECTED;

    private PartManagePresenter presenter = new PartManagePresenter();

    public PartManagePanel() {
        initView();
        presenter.attachView(this);
        presenter.select();
    }

    private void initView() {
        setLayout(new BorderLayout());

        JScrollPane scrollPane = new JScrollPane();
        adapter = new PartManageAdapter();
        table = new JTable(adapter);
        table.getColumnModel().getColumn(0).setCellEditor(new CheckBoxEditor());
        table.getColumnModel().getColumn(0).setCellRenderer(new CheckBoxRender());
        scrollPane.setViewportView(table);
        add(scrollPane, BorderLayout.CENTER);

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
        JButton newButton = new JButton("新建");
        newButton.addActionListener(event -> add());
        JButton deleteButton = new JButton("删除");
        deleteButton.addActionListener(event -> presenter.delete(partBeans));
        JButton saveButton = new JButton("保存");
        saveButton.addActionListener(event -> presenter.save(partBeans));
        box.add(Box.createHorizontalStrut(5));
        box.add(selectAllButton);
        box.add(Box.createHorizontalGlue());
        box.add(newButton);
        box.add(Box.createHorizontalStrut(10));
        box.add(deleteButton);
        box.add(Box.createHorizontalStrut(10));
        box.add(saveButton);
        box.add(Box.createHorizontalStrut(5));
        add(box, BorderLayout.SOUTH);
    }

    private void add() {
        PartBean partBean = new PartBean();
        partBean.setState(Config.UNSELECTED);
        partBeans.add(partBean);
        table.updateUI();
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        presenter.detachView();
    }

    @Override
    public void update(List<PartBean> data) {
        SwingUtilities.invokeLater(() -> {
            this.partBeans = data;
            adapter.setPartBeans(data);
            table.updateUI();
        });
    }

    @Override
    public void applyDelete(List<PartBean> data) {
        SwingUtilities.invokeLater(() -> {
            List<PartBean> list = data.stream()
                    .filter(temp -> temp.getState() == Config.SELECTED)
                    .collect(Collectors.toList());
            this.partBeans.removeAll(list);
            adapter.setPartBeans(partBeans);
            table.updateUI();
        });
    }

    @Override
    public void resetAll() {
        SwingUtilities.invokeLater(() -> {
            allState = Config.UNSELECTED;
            selectAllButton.setText("全选");
            partBeans.forEach(temp -> temp.setState(Config.UNSELECTED));
            table.updateUI();
        });
    }
}
