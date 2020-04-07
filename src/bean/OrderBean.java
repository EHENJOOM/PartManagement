package bean;

import java.sql.Timestamp;
import java.util.Objects;

/**
 * @author 赵洪苛
 * @date 2020/3/19 7:48
 * @description
 */
public class OrderBean {
    private Integer id;
    private Integer needCount;
    private Timestamp orderDate;
    private Timestamp paymentDate;
    private UserBean userBean;
    private PartBean partBean;
    private Integer status;
    private String extend;
    private Integer paid;
    private Timestamp payDate;

    private String suggest;
    private int state;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getNeedCount() {
        return needCount;
    }

    public void setNeedCount(Integer needCount) {
        this.needCount = needCount;
    }

    public Timestamp getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Timestamp orderDate) {
        this.orderDate = orderDate;
    }

    public Timestamp getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(Timestamp paymentDate) {
        this.paymentDate = paymentDate;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getExtend() {
        return extend;
    }

    public void setExtend(String extend) {
        this.extend = extend;
    }

    public Timestamp getPayDate() {
        return payDate;
    }

    public void setPayDate(Timestamp payDate) {
        this.payDate = payDate;
    }

    public Integer getPaid() {
        return paid;
    }

    public void setPaid(Integer paid) {
        this.paid = paid;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getSuggest() {
        return suggest;
    }

    public void setSuggest(String suggest) {
        this.suggest = suggest;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        OrderBean orderBean = (OrderBean) o;
        return Objects.equals(id, orderBean.id) &&
                Objects.equals(needCount, orderBean.needCount) &&
                Objects.equals(orderDate, orderBean.orderDate) &&
                Objects.equals(paymentDate, orderBean.paymentDate);
    }

    @Override
    public String toString() {
        return partBean.toString() + " " + userBean.toString() + " " + needCount + " " + orderDate;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, needCount, orderDate, paymentDate);
    }

    public UserBean getUserBean() {
        return userBean;
    }

    public void setUserBean(UserBean userBean) {
        this.userBean = userBean;
    }

    public PartBean getPartBean() {
        return partBean;
    }

    public void setPartBean(PartBean partBean) {
        this.partBean = partBean;
    }
}
