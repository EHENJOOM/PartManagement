package panel.sup.cart;

import bean.ShopCartBean;
import bean.UserBean;
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
 * @date 2020/4/1 17:28
 * @description 购物车数据处理器
 */
public class ShopCartModel {

    public void select(BaseCallBack<List<ShopCartBean>> baseCallBack) {
        ThreadPoolEnum.getInstance().execute(() -> {
            Session session = SessionFactoryEnum.getInstance().getSession();
            if (session == null) {
                return;
            }
            try {
                List<ShopCartBean> list = session.createQuery("from ShopCartBean ").list();
                list.forEach(temp -> {
                    Hibernate.initialize(temp.getPartBean());
                    temp.setUserBean((UserBean) session.createQuery("from UserBean user where loginBean.id=:id ").setParameter("id", temp.getLoginBean().getId()).uniqueResult());
                });
                baseCallBack.onSucceed(list);
            } catch (Exception e) {
                baseCallBack.onFailed("查询失败！");
            } finally {
                session.close();
            }
        });
    }

    public void delete(List<ShopCartBean> data, BaseCallBack<List<ShopCartBean>> baseCallBack) {
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

    public void save(List<ShopCartBean> data, BaseCallBack<List<ShopCartBean>> baseCallBack) {
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
