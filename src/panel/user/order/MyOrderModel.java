package panel.user.order;

import bean.LoginBean;
import bean.OrderBean;
import bean.UserBean;
import constant.Config;
import mvp.BaseCallBack;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.Transaction;
import thread.ThreadPoolEnum;
import util.SessionFactoryEnum;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author 赵洪苛
 * @date 2020/3/25 18:04
 * @description 订单数据处理器
 */
public class MyOrderModel {

    public void select(LoginBean loginBean, BaseCallBack<List<OrderBean>> baseCallBack) {
        ThreadPoolEnum.getInstance().execute(() -> {
            Session session = SessionFactoryEnum.getInstance().getSession();
            if (session == null) {
                return;
            }
            try {
                UserBean userBean = (UserBean) session.createQuery("from UserBean user where user.loginBean=:loginBean")
                    .setParameter("loginBean", loginBean)
                    .uniqueResult();
                List<OrderBean> list = session.createQuery("from OrderBean orderBean where orderBean.userBean=:userBean")
                    .setParameter("userBean", userBean)
                    .list();
                list.forEach(temp -> {
                    Hibernate.initialize(temp.getUserBean());
                    Hibernate.initialize(temp.getPartBean());
                });
                baseCallBack.onSucceed(list);
            } catch (Exception e) {
                e.printStackTrace();
                baseCallBack.onFailed("查询失败！");
            }
        });
    }

    public void delete(List<OrderBean> orderBeans, BaseCallBack<List<OrderBean>> baseCallBack) {
        ThreadPoolEnum.getInstance().execute(() -> {
            Session session = SessionFactoryEnum.getInstance().getSession();
            if (session == null) {
                return;
            }
            Transaction transaction  = session.beginTransaction();
            try {
                orderBeans.stream()
                    .filter(temp -> temp.getState() == Config.SELECTED)
                    .forEach(session::delete);
                transaction.commit();
                baseCallBack.onSucceed(orderBeans.stream().filter(temp -> temp.getState() == Config.SELECTED).collect(Collectors.toList()));
            } catch (Exception e) {
                transaction.rollback();
                baseCallBack.onFailed("删除失败，请稍后重试！");
            } finally {
                session.close();
            }
        });
    }

    public void pay(List<OrderBean> orderBeans, BaseCallBack<List<OrderBean>> baseCallBack) {
        ThreadPoolEnum.getInstance().execute(() -> {
            Session session = SessionFactoryEnum.getInstance().getSession();
            if (session == null) {
                return;
            }
            Transaction transaction = session.beginTransaction();
            try {
                orderBeans.stream()
                    .filter(temp -> temp.getStatus() == Config.ORDER_STATUS_UNPAID)
                    .filter(temp -> temp.getState() == Config.SELECTED)
                    .forEach(temp -> {
                        temp.setStatus(Config.ORDER_STATUS_PAID);
                        temp.setPaid(Config.ORDER_STATUS_PAID);
                        session.update(temp);
                    });
                transaction.commit();
                baseCallBack.onSucceed(orderBeans);
            } catch (Exception e) {
                transaction.rollback();
                baseCallBack.onFailed("支付失败！");
            } finally {
                session.close();
            }
        });
    }

}
