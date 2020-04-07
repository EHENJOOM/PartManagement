package bean;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * @author 赵洪苛
 * @date 2020/3/19 7:48
 * @description
 */
public class PartBean {
    private Integer id;

    public void setId(int id) {
        this.id = id;
    }

    private String code;
    private String name;
    private Integer count;
    private BigDecimal price;

    private int state;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PartBean partBean = (PartBean) o;
        return Objects.equals(id, partBean.id) &&
                Objects.equals(code, partBean.code) &&
                Objects.equals(name, partBean.name) &&
                Objects.equals(count, partBean.count);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, code, name, count);
    }

    @Override
    public String toString() {
        return code + " " + name + " " + count;
    }
}
