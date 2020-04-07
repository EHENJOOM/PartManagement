package panel.admin.user;

import bean.UserBean;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * @author 赵洪苛
 * @date 2020/3/30 15:05
 * @description 用户管理面板
 */
public class UserManagePanel extends JPanel implements UserManageView {

    private JTable table;
    private UserManageAdapter adapter;
    private UserManagePresenter presenter = new UserManagePresenter();

    public UserManagePanel() {
        initView();
        presenter.attachView(this);
        presenter.select();
    }

    private void initView() {
        setLayout(new BorderLayout());

        JScrollPane scrollPane = new JScrollPane();
        adapter = new UserManageAdapter();
        table = new JTable(adapter);
        scrollPane.setViewportView(table);
        add(scrollPane, BorderLayout.CENTER);
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        presenter.detachView();
    }

    @Override
    public void update(List<UserBean> data) {
        SwingUtilities.invokeLater(() -> {
            adapter.setUserBeans(data);
            table.updateUI();
        });
    }
}
