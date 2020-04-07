package login;

import bean.LoginBean;
import bean.UserBean;
import constant.Config;
import main.MainDialog;
import org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper;
import org.jb2011.lnf.beautyeye.ch3_button.BEButtonUI;
import register.RegisterDialog;
import thread.ThreadPoolEnum;
import util.SessionFactoryEnum;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * @author 赵洪苛
 * @date 2020//03/21 17:57
 * @description 登录窗口
 */
public class LoginDialog extends JFrame implements LoginView {

    /**
     * 使用了IDEA的创建Dialog的工具，初始化控件的代码自动插入至字节码文件中
     */
    private JPanel contentPane;
    private JComboBox comboBox;
    private JTextField accountTxtField;
    private JTextField passwordTxtField;
    private JButton okButton;
    private JLabel registerLabel;
    private JLabel forgetLabel;

    private LoginPresenter presenter;

    private int status = Config.USER_LOGIN;

    public LoginDialog() {
        initView();
        presenter = new LoginPresenter();
        // 逻辑处理器绑定视图
        presenter.attachView(this);
    }

    /**
     * 初始化视图
     */
    private void initView() {
        setContentPane(contentPane);
        setSize(480, 330);
        setLocationRelativeTo(null);
        setTitle("毕业论文课题管理系统");
        getRootPane().setDefaultButton(okButton);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        okButton.setForeground(Color.WHITE);
        okButton.setUI(new BEButtonUI().setNormalColor(BEButtonUI.NormalColor.blue));
        okButton.addActionListener(event -> {
            String account = accountTxtField.getText();
            String password = passwordTxtField.getText();

            if (account.isEmpty()) {
                showError("账号不能为空！");
                return;
            }
            if (password.isEmpty()) {
                showError("密码不能为空！");
                return;
            }

            presenter.login(new LoginBean(account, password, status));
        });

        comboBox.addItem("用户");
        comboBox.addItem("管理员");
        comboBox.addItem("超级管理员");
        comboBox.addItemListener(event -> status = comboBox.getSelectedIndex());

        registerLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                dispose();
                RegisterDialog dialog = new RegisterDialog(Config.REGISTER_DIALOG);
                dialog.setVisible(true);
            }
        });
        forgetLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                dispose();
                RegisterDialog dialog = new RegisterDialog(Config.FORGET_PASSWORD_DIALOG);
                dialog.setVisible(true);
            }
        });
    }

    @Override
    public void login(LoginBean loginBean) {
        // 退出消费者线程，使用事件分发者线程更新UI
        SwingUtilities.invokeLater(() -> {
            this.dispose();
            MainDialog mainDialog = new MainDialog(loginBean);
            mainDialog.setVisible(true);
        });
    }

    @Override
    public void toInit(UserBean userBean) {
        SwingUtilities.invokeLater(() -> {
            this.dispose();
            new InitInformationDialog(userBean).setVisible(true);
        });
    }

    public static void main(String[] args) {
        // 使用美化包进行界面美化
        try {
            BeautyEyeLNFHelper.frameBorderStyle = BeautyEyeLNFHelper.FrameBorderStyle.translucencyAppleLike;
            UIManager.put("RootPane.setupButtonVisible",false);
            BeautyEyeLNFHelper.launchBeautyEyeLNF();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 提前加载SessionFactory，防止用户等待时间过长
        ThreadPoolEnum.getInstance().execute(SessionFactoryEnum::getInstance);

        LoginDialog dialog = new LoginDialog();
        dialog.setVisible(true);
    }

}
