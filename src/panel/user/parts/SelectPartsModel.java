package panel.user.parts;

import bean.LoginBean;
import bean.PartBean;
import bean.ShopCartBean;
import constant.Config;
import mvp.BaseCallBack;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import thread.ThreadPoolEnum;
import util.SessionFactoryEnum;

import java.util.List;

/**
 * @author 赵洪苛
 * @date 2020/3/24 15:57
 * @description 查询零件数据访问
 */
public class SelectPartsModel {

    public void select(LoginBean loginBean, BaseCallBack<List<PartBean>> baseCallBack) {
        Session session = SessionFactoryEnum.getInstance().getSession();
        if (session == null) {
            return;
        }
        try {
            Query<PartBean> query = session.createQuery("from PartBean");
            List<PartBean> list = query.list();
            if (list.size() == 0) {
                baseCallBack.onFailed("未查到任何数据！");
                return;
            }
            baseCallBack.onSucceed(list);
        } catch (Exception e) {
            baseCallBack.onFailed("数据获取失败！");
        }
    }

    public void addIntoCart(LoginBean loginBean, List<PartBean> partBeans, BaseCallBack<String> baseCallBack) {
        ThreadPoolEnum.getInstance().execute(() -> {
            Session session = SessionFactoryEnum.getInstance().getSession();
            if (session == null) {
                return;
            }
            Transaction transaction = session.beginTransaction();
            try {
                partBeans.stream()
                    .filter(temp -> temp.getState() == Config.SELECTED)
                    .forEach(temp -> {
                        long count = (Long) session.createQuery("select count(*) from ShopCartBean shop where shop.loginBean=:loginBean and shop.partBean=:partBean")
                            .setParameter("loginBean", loginBean)
                            .setParameter("partBean", temp)
                            .uniqueResult();
                        if (count <= 0) {
                            ShopCartBean shopCartBean = new ShopCartBean();
                            shopCartBean.setNeedCount(1);
                            shopCartBean.setPartBean(temp);
                            shopCartBean.setLoginBean(loginBean);
                            session.save(shopCartBean);
                        }
                    });
                transaction.commit();
                baseCallBack.onSucceed("添加成功！");
            } catch (Exception e) {
                transaction.rollback();
                baseCallBack.onFailed("添加失败");
            } finally {
                session.close();
            }
        });
    }

    public void deleteFromCart(LoginBean loginBean, List<PartBean> partBeans, BaseCallBack<String> baseCallBack) {
        ThreadPoolEnum.getInstance().execute(() -> {
            Session session = SessionFactoryEnum.getInstance().getSession();
            if (session == null) {
                return;
            }
            Transaction transaction = session.beginTransaction();
            try {
                partBeans.stream()
                    .filter(temp -> temp.getState() == Config.SELECTED)
                    .forEach(temp -> session.createQuery("delete from ShopCartBean shop where shop.loginBean=:loginBean and shop.partBean=:partBean")
                        .setParameter("loginBean", loginBean)
                        .setParameter("partBean", temp)
                        .executeUpdate());
                transaction.commit();
                baseCallBack.onSucceed("删除成功！");
            } catch (Exception e) {
                transaction.rollback();
                baseCallBack.onFailed("删除失败，请稍后重试！");
            } finally {
                session.close();
            }
        });
    }

}
