package panel.sup.order;

import bean.OrderBean;
import constant.Config;
import mvp.BaseCallBack;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.Transaction;
import thread.ThreadPoolEnum;
import util.SessionFactoryEnum;

import java.util.List;

/**
 * @author 赵洪苛
 * @date 2020/4/1 17:37
 * @description 订单数据处理器
 */
public class OrderModel {

    public void select(BaseCallBack<List<OrderBean>> baseCallBack) {
        ThreadPoolEnum.getInstance().execute(() -> {
            Session session = SessionFactoryEnum.getInstance().getSession();
            if (session == null) {
                return;
            }
            try {
                List<OrderBean> list = session.createQuery("from OrderBean ").list();
                list.forEach(temp -> {
                    Hibernate.initialize(temp.getPartBean());
                    Hibernate.initialize(temp.getUserBean());
                });
                baseCallBack.onSucceed(list);
            } catch (Exception e) {
                baseCallBack.onFailed("查询失败！");
            } finally {
                session.close();
            }
        });
    }

    public void delete(List<OrderBean> data, BaseCallBack<List<OrderBean>> baseCallBack) {
        ThreadPoolEnum.getInstance().execute(() -> {
            Session session = SessionFactoryEnum.getInstance().getSession();
            if (session == null) {
                return;
            }
            Transaction transaction = session.beginTransaction();
            try {
                data.stream()
                    .filter(temp -> temp.getState() == Config.SELECTED)
                    .forEach(session::delete);
                transaction.commit();
            } catch (Exception e) {
                transaction.rollback();
                baseCallBack.onFailed("删除失败！");
            } finally {
                session.close();
            }
        });
    }

    public void save(List<OrderBean> data, BaseCallBack<List<OrderBean>> baseCallBack) {
        ThreadPoolEnum.getInstance().execute(() -> {
            Session session = SessionFactoryEnum.getInstance().getSession();
            if (session == null) {
                return;
            }
            Transaction transaction = session.beginTransaction();
            try {
                data.stream()
                    .filter(temp -> temp.getState() == Config.SELECTED)
                    .forEach(session::saveOrUpdate);
                transaction.commit();
                baseCallBack.onSucceed(data);
            } catch (Exception e) {
                transaction.rollback();
                baseCallBack.onFailed("保存失败！");
            } finally {
                session.close();
            }
        });
    }

}
