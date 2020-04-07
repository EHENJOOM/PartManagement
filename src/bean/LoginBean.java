package bean;

import java.util.Objects;

/**
 * @author 赵洪苛
 * @date 2020/3/19 7:48
 * @description
 */
public class LoginBean {
    private Integer id;
    private String account;
    private String password;
    private Integer type;
    private int state;

    public LoginBean() {

    }

    public LoginBean(String account, String password, int type) {
        this.account = account;
        this.password = password;
        this.type = type;
    }

    public LoginBean(String account, String password) {
        this.account = account;
        this.password = password;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        LoginBean loginBean = (LoginBean) o;
        return Objects.equals(account, loginBean.account) &&
                Objects.equals(password, loginBean.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, account, password, type);
    }

    @Override
    public String toString() {
        return account + " " + password;
    }
}
