package bean;

import java.util.Objects;

/**
 * @author 赵洪苛
 * @date 2020/3/21 23:15
 * @description
 */
public class VerifyBean {
    private int id;
    private String account;
    private String code;
    private long timestamp;
    private int type;

    public VerifyBean() { }

    public VerifyBean(String account, String code, long timestamp, int type) {
        this.account =account;
        this.code = code;
        this.timestamp = timestamp;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
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
        VerifyBean that = (VerifyBean) o;
        return id == that.id &&
                Objects.equals(account, that.account) &&
                Objects.equals(code, that.code);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, account, code, timestamp);
    }
}
