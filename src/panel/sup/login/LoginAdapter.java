package panel.sup.login;

import bean.LoginBean;
import constant.ConstantStatus;

import javax.swing.table.AbstractTableModel;
import java.util.List;

/**
 * @author 赵洪苛
 * @date 2020/3/31 19:34
 * @description 登录表格适配器
 */
public class LoginAdapter extends AbstractTableModel {

    private List<LoginBean> loginBeans;

    private String[] headers = {"操作", "账号", "密码", "账号类型"};

    public void setLoginBeans(List<LoginBean> loginBeans) {
        this.loginBeans = loginBeans;
    }

    @Override
    public String getColumnName(int column) {
        return headers[column];
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return true;
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        switch (columnIndex) {
            case 0:
                loginBeans.get(rowIndex).setState((int) aValue);
                break;
            case 1:
                loginBeans.get(rowIndex).setAccount((String) aValue);
                break;
            case 2:
                loginBeans.get(rowIndex).setPassword((String) aValue);
                break;
            case 3:
                loginBeans.get(rowIndex).setType(ConstantStatus.getStatusByString((String) aValue));
                break;
            default:
        }
    }

    @Override
    public int getRowCount() {
        return loginBeans == null ? 0 : loginBeans.size();
    }

    @Override
    public int getColumnCount() {
        return headers.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        switch (columnIndex) {
            case 0:
                return loginBeans.get(rowIndex).getState();
            case 1:
                return loginBeans.get(rowIndex).getAccount();
            case 2:
                return loginBeans.get(rowIndex).getPassword();
            case 3:
                if (loginBeans.get(rowIndex).getType() == null) {
                    return null;
                }
                return ConstantStatus.getStatus(loginBeans.get(rowIndex).getType());
            default:
                return null;
        }
    }
}
