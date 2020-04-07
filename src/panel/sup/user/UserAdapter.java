package panel.sup.user;

import bean.UserBean;

import javax.swing.table.AbstractTableModel;
import java.util.List;

/**
 * @author 赵洪苛
 * @date 2020/3/31 19:35
 * @description 用户信息表格适配器
 */
public class UserAdapter extends AbstractTableModel {

    private List<UserBean> userBeans;

    private String[] headers = {"操作", "登录账号", "用户代码", "姓名", "性别", "地址", "电话"};

    public void setUserBeans(List<UserBean> userBeans) {
        this.userBeans = userBeans;
    }

    @Override
    public String getColumnName(int column) {
        return headers[column];
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return columnIndex != 1;
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        switch (columnIndex) {
            case 0:
                userBeans.get(rowIndex).setState((int) aValue);
                break;
            case 2:
                userBeans.get(rowIndex).setCode((String) aValue);
                break;
            case 3:
                userBeans.get(rowIndex).setName((String) aValue);
                break;
            case 4:
                userBeans.get(rowIndex).setSex((String) aValue);
                break;
            case 5:
                userBeans.get(rowIndex).setAddress((String) aValue);
                break;
            case 6:
                userBeans.get(rowIndex).setPhone((String) aValue);
                break;
            default:
        }
    }

    @Override
    public int getRowCount() {
        return userBeans == null ? 0 : userBeans.size();
    }

    @Override
    public int getColumnCount() {
        return headers.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        switch (columnIndex) {
            case 0:
                return userBeans.get(rowIndex).getState();
            case 1:
                return userBeans.get(rowIndex).getLoginBean().getAccount();
            case 2:
                return userBeans.get(rowIndex).getCode();
            case 3:
                return userBeans.get(rowIndex).getName();
            case 4:
                return userBeans.get(rowIndex).getSex();
            case 5:
                return userBeans.get(rowIndex).getAddress();
            case 6:
                return userBeans.get(rowIndex).getPhone();
            default:
                return null;
        }
    }
}
