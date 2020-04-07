package panel.admin.user;

import bean.UserBean;

import javax.swing.table.AbstractTableModel;
import java.util.List;

/**
 * @author 赵洪苛
 * @date 2020/3/30 15:17
 * @description 表格适配器
 */
public class UserManageAdapter extends AbstractTableModel {

    private String[] headers = {"客户代码", "姓名", "性别", "地址", "电话"};

    private List<UserBean> userBeans;

    public void setUserBeans(List<UserBean> userBeans) {
        this.userBeans = userBeans;
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
    public String getColumnName(int column) {
        return headers[column];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        switch (columnIndex) {
            case 0:
                return userBeans.get(rowIndex).getCode();
            case 1:
                return userBeans.get(rowIndex).getName();
            case 2:
                return userBeans.get(rowIndex).getSex();
            case 3:
                return userBeans.get(rowIndex).getAddress();
            case 4:
                return userBeans.get(rowIndex).getPhone();
            default:
                return null;
        }
    }
}
