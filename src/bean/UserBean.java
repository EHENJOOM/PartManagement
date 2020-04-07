package bean;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Objects;

/**
 * @author 赵洪苛
 * @date 2020/3/19 7:48
 * @description
 */
public class UserBean {
    private Integer id;
    private String name;
    private String code;
    private Timestamp payableTime;
    private LoginBean loginBean;
    private String sex;
    private String address;
    private String phone;

    private int state;

    public Integer getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Timestamp getPayableTime() {
        return payableTime;
    }

    public void setPayableTime(Timestamp payableTime) {
        this.payableTime = payableTime;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        UserBean userBean = (UserBean) o;
        return Objects.equals(id, userBean.id) &&
                Objects.equals(name, userBean.name) &&
                Objects.equals(code, userBean.code) &&
                Objects.equals(payableTime, userBean.payableTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, payableTime);
    }

    @Override
    public String toString() {
        return loginBean.toString() + " " + name + " " + payableTime;
    }

    public LoginBean getLoginBean() {
        return loginBean;
    }

    public void setLoginBean(LoginBean loginByLogin) {
        this.loginBean = loginByLogin;
    }
}
