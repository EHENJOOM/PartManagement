package register;

import bean.LoginBean;
import constant.Config;
import login.LoginDialog;

import javax.swing.*;

/**
 * @author 赵洪苛
 * @date 2020/03/24 20:18
 * @description 注册或忘记密码窗口
 */
public class RegisterDialog extends JFrame implements RegisterView {
    private JTextField accountTextField;
    private JTextField verifyCodeTextField;
    private JButton sendVerifyButton;
    private JTextField passwordTextField;
    private JButton okButton;
    private JLabel titleLabel;
    private JPanel contentPanel;
    private JTextField nameTextField;
    private JComboBox emailComboBox;
    private JButton backButton;
    private JRadioButton userRadio;
    private JRadioButton adminRadio;
    private JLabel authorLabel;
    private JLabel nameLabel;

    private RegisterPresenter presenter = new RegisterPresenter();

    private int status = Config.USER_LOGIN;

    public RegisterDialog(int type) {
        setContentPane(contentPanel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        initView(type);
        pack();
        setLocationRelativeTo(null);
        presenter.attachView(this);
    }

    private void initView(int type) {
        emailComboBox.addItem(Config.BUCT_MAIL_SUFFIX);
        emailComboBox.addItem(Config.QQ_MAIL_SUFFIX);
        emailComboBox.addItem(Config.WANGYI_AIL_SUFFIX);
        if (type == Config.REGISTER_DIALOG) {
            setTitle("注册");
            titleLabel.setText("注册");
            userRadio.setVisible(true);
            adminRadio.setVisible(true);
            userRadio.setSelected(true);
        } else if (type == Config.FORGET_PASSWORD_DIALOG) {
            setTitle("忘记密码");
            titleLabel.setText("忘记密码");
            userRadio.setVisible(false);
            adminRadio.setVisible(false);
            nameLabel.setVisible(false);
            nameTextField.setVisible(false);
            authorLabel.setVisible(false);
        }

        ButtonGroup buttonGroup = new ButtonGroup();
        userRadio.addActionListener(event -> status = Config.USER_LOGIN);
        adminRadio.addActionListener(event -> status = Config.ADMIN_LOGIN);
        buttonGroup.add(userRadio);
        buttonGroup.add(adminRadio);

        sendVerifyButton.addActionListener(event -> {
            String account = accountTextField.getText();
            if (account.isEmpty()) {
                showMessage("账号不能为空！");
                return;
            }
            presenter.sendVerifyCode(type, account, (String) emailComboBox.getSelectedItem());
        });
        backButton.addActionListener(event -> {
            dispose();
            LoginDialog dialog = new LoginDialog();
            dialog.setVisible(true);
        });
        okButton.addActionListener(event -> {
            String account = accountTextField.getText();
            String verify = verifyCodeTextField.getText();
            String password = passwordTextField.getText();
            String name = nameTextField.getText();

            if (account.isEmpty()) {
                showMessage("账号不能为空！");
                return;
            }
            if (verify.isEmpty()) {
                showMessage("验证码不能为空！");
                return;
            }
            if (password.isEmpty()) {
                showMessage("密码不能为空！");
                return;
            }
            if (type == Config.REGISTER_DIALOG) {
                if (name.isEmpty()) {
                    showMessage("姓名不能为空！");
                    return;
                }
            }

            presenter.verify(type, verify, new LoginBean(account, password, status));
        });
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        presenter.detachView();
    }

    @Override
    public int showConfirm(String msg) {
        return JOptionPane.showConfirmDialog(this, msg, "提示", JOptionPane.YES_NO_OPTION);
    }

    @Override
    public void showMessage(String msg) {
        SwingUtilities.invokeLater(() -> JOptionPane.showMessageDialog(this, msg, "提示", JOptionPane.INFORMATION_MESSAGE));
    }

    @Override
    public void toRegister(LoginBean loginBean) {
        SwingUtilities.invokeLater(() -> presenter.register(nameTextField.getText(), loginBean));
    }

    @Override
    public void toUpdatePassword(LoginBean loginBean) {
        SwingUtilities.invokeLater(() -> presenter.updatePassword(loginBean));
    }

    @Override
    public void toLogin(LoginBean loginBean) {
        SwingUtilities.invokeLater(() -> {
            this.dispose();
            LoginDialog loginDialog = new LoginDialog();
            loginDialog.setVisible(true);
        });
    }

}
