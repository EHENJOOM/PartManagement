package bean;

/**
 * @author 赵洪苛
 * @date 2020/3/24 23:22
 * @description
 */
public class ShopCartBean {
    private int id;
    private Integer needCount;
    private LoginBean loginBean;
    private PartBean partBean;

    private UserBean userBean;
    private int state;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LoginBean getLoginBean() {
        return loginBean;
    }

    public PartBean getPartBean() {
        return partBean;
    }

    public void setPartBean(PartBean partBean) {
        this.partBean = partBean;
    }

    public void setLoginBean(LoginBean loginBean) {
        this.loginBean = loginBean;
    }

    public Integer getNeedCount() {
        return needCount;
    }

    public void setNeedCount(Integer needCount) {
        this.needCount = needCount;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public UserBean getUserBean() {
        return userBean;
    }

    public void setUserBean(UserBean userBean) {
        this.userBean = userBean;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ShopCartBean)) return false;

        ShopCartBean that = (ShopCartBean) o;

        if (getId() != that.getId()) return false;
        if (getState() != that.getState()) return false;
        if (!getLoginBean().equals(that.getLoginBean())) return false;
        return getPartBean().equals(that.getPartBean());
    }

    @Override
    public int hashCode() {
        int result = getId();
        result = 31 * result + getLoginBean().hashCode();
        result = 31 * result + getPartBean().hashCode();
        result = 31 * result + getState();
        return result;
    }
}
