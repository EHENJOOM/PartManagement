package login;

import bean.LoginBean;
import bean.UserBean;
import main.MainDialog;
import org.hibernate.Session;
import org.hibernate.Transaction;
import thread.ThreadPoolEnum;
import util.SessionFactoryEnum;

import javax.swing.*;
import java.util.regex.Pattern;

/**
 * @author 赵洪苛
 * @date 2020/3/29 23:08
 * @description
 */
public class InitInformationDialog extends JFrame {
    private JTextField phoneTextField;
    private JTextField addressTextField;
    private JButton okButton;
    private JRadioButton maleRadioButton;
    private JRadioButton femaleRadioButton;
    private JPanel contentPane;

    private String sex = "男";
    private Pattern pattern;

    public InitInformationDialog(UserBean userBean) {
        initView(userBean);
    }

    private void initView(UserBean userBean) {
        setSize(400, 300);
        setContentPane(contentPane);
        setTitle("完善个人信息");
        setLocationRelativeTo(null);
        getRootPane().setDefaultButton(okButton);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        ButtonGroup buttonGroup = new ButtonGroup();
        maleRadioButton.addActionListener(event -> sex = "男");
        femaleRadioButton.addActionListener(event -> sex = "女");
        maleRadioButton.setSelected(true);
        buttonGroup.add(maleRadioButton);
        buttonGroup.add(femaleRadioButton);
        okButton.addActionListener(event -> save(userBean));
        pattern = Pattern.compile("1[3-9]\\\\d{9}");
    }

    private void save(UserBean userBean) {
        String address = addressTextField.getText();
        String phone = phoneTextField.getText();

        if (address == null || address.isEmpty()) {
            showInfo("地址不能为空！");
            return;
        }
        if (phone == null || phone.isEmpty()) {
            showInfo("电话不能为空！");
            return;
        }
        if (pattern.matcher(phone).matches()) {
            showInfo("不正确的电话格式！");
            return;
        }

        ThreadPoolEnum.getInstance().execute(()-> {
            Session session = SessionFactoryEnum.getInstance().getSession();
            if (session == null) {
                showInfo("提交失败！");
                return;
            }
            Transaction transaction = session.beginTransaction();
            try {
                session.createQuery("update UserBean user set user.address=:address, user.phone=:phone, user.sex=:sex where loginBean.account=:account and loginBean.type=:type")
                        .setParameter("address", address)
                        .setParameter("phone", phone)
                        .setParameter("sex", sex)
                        .setParameter("account", userBean.getLoginBean().getAccount())
                        .setParameter("type", userBean.getLoginBean().getType())
                        .executeUpdate();
                transaction.commit();
                toMainDialog(userBean);
            } catch (Exception e) {
                transaction.rollback();
                showInfo("提交失败！");
            } finally {
                session.close();
            }
        });
    }

    private void toMainDialog(UserBean userBean) {
        SwingUtilities.invokeLater(() -> {
            this.dispose();
            new MainDialog(userBean.getLoginBean()).setVisible(true);
        });
    }

    private void showInfo(String msg) {
        JOptionPane.showMessageDialog(null, msg, "提示", JOptionPane.INFORMATION_MESSAGE);
    }
}
