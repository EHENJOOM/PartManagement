package panel.user.cart;

import bean.LoginBean;
import bean.OrderBean;
import bean.ShopCartBean;
import bean.UserBean;
import constant.Config;
import mvp.BaseCallBack;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.Transaction;
import thread.ThreadPoolEnum;
import util.SessionFactoryEnum;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author 赵洪苛
 * @date 2020/3/24 22:37
 * @description 购物车数据处理器
 */
public class ShopCartModel {

    public void update(LoginBean loginBean, BaseCallBack<List<ShopCartBean>> baseCallBack) {
        ThreadPoolEnum.getInstance().execute(() -> {
            Session session = SessionFactoryEnum.getInstance().getSession();
            if (session == null) {
                return;
            }
            try {
                List<ShopCartBean> list = session.createQuery("from ShopCartBean shop where shop.loginBean=:loginBean")
                    .setParameter("loginBean", loginBean)
                    .list();
                list.forEach(temp -> Hibernate.initialize(temp.getPartBean()));
                baseCallBack.onSucceed(list);
            } catch (Exception e) {
                baseCallBack.onFailed("数据获取失败，请稍后重试！");
            } finally {
                session.close();
            }
        });
    }

    public void delete(List<ShopCartBean> shopCartBeans, BaseCallBack<List<ShopCartBean>> baseCallBack) {
        ThreadPoolEnum.getInstance().execute(() -> {
            Session session = SessionFactoryEnum.getInstance().getSession();
            if (session == null) {
                return;
            }
            Transaction transaction = session.beginTransaction();
            try {
                shopCartBeans.stream()
                    .filter(temp -> temp.getState() == Config.SELECTED)
                    .forEach(session::delete);
                transaction.commit();
                baseCallBack.onSucceed(shopCartBeans.stream().filter(temp -> temp.getState() == Config.SELECTED).collect(Collectors.toList()));
            } catch (Exception e) {
                transaction.rollback();
                baseCallBack.onFailed("删除失败，请稍后重试！");
            } finally {
                session.close();
            }
        });
    }

    public void save(List<ShopCartBean> shopCartBeans, BaseCallBack<List<ShopCartBean>> baseCallBack) {
        ThreadPoolEnum.getInstance().execute(() -> {
            Session session = SessionFactoryEnum.getInstance().getSession();
            if (session == null) {
                return;
            }
            Transaction transaction = session.beginTransaction();
            try {
                shopCartBeans.stream()
                    .filter(temp -> temp.getState() == Config.SELECTED)
                    .forEach(session::update);
                transaction.commit();
                baseCallBack.onSucceed(shopCartBeans);
            } catch (Exception e) {
                transaction.rollback();
                baseCallBack.onFailed("保存失败，请稍后重试！");
            } finally {
                session.close();
            }
        });
    }

    public void purchase(LoginBean loginBean, Timestamp date, List<ShopCartBean> shopCartBeans, BaseCallBack<List<ShopCartBean>> baseCallBack) {
        ThreadPoolEnum.getInstance().execute(() -> {
            Session session = SessionFactoryEnum.getInstance().getSession();
            if (session == null) {
                return;
            }
            Transaction transaction = session.beginTransaction();
            try {
                UserBean userBean = (UserBean) session.createQuery("from UserBean user where user.loginBean=:loginBean")
                    .setParameter("loginBean", loginBean)
                    .uniqueResult();
                shopCartBeans.stream()
                    .filter(temp -> temp.getState() == Config.SELECTED)
                    .forEach(temp -> {
                        OrderBean orderBean = new OrderBean();
                        orderBean.setPartBean(temp.getPartBean());
                        orderBean.setOrderDate(new Timestamp(System.currentTimeMillis()));
                        orderBean.setUserBean(userBean);
                        orderBean.setStatus(Config.ORDER_STATUS_AUDIT);
                        orderBean.setPaid(Config.ORDER_STATUS_UNPAID);
                        orderBean.setPaymentDate(date);
                        orderBean.setNeedCount(temp.getNeedCount());
                        session.save(orderBean);
                        session.delete(temp);
                    });
                transaction.commit();
                baseCallBack.onSucceed(shopCartBeans);
            } catch (Exception e) {
                transaction.rollback();
                baseCallBack.onFailed("订单提交失败！");
            } finally {
                session.close();
            }
        });
    }

}
