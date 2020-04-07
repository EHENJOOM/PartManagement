package panel.sup.login;

import bean.LoginBean;
import constant.Config;
import mvp.BaseCallBack;
import org.hibernate.Session;
import org.hibernate.Transaction;
import thread.ThreadPoolEnum;
import util.SessionFactoryEnum;

import java.util.List;

/**
 * @author 赵洪苛
 * @date 2020/3/31 19:30
 * @description 登录数据处理器
 */
public class LoginModel {

    public void select(BaseCallBack<List<LoginBean>> baseCallBack) {
        ThreadPoolEnum.getInstance().execute(() -> {
            Session session = SessionFactoryEnum.getInstance().getSession();
            if (session == null) {
                return;
            }
            try {
                List list = session.createQuery("from LoginBean ").list();
                baseCallBack.onSucceed(list);
            } catch (Exception e) {
                baseCallBack.onFailed("查询失败！");
            } finally {
                session.close();
            }
        });
    }

    public void delete(List<LoginBean> data, BaseCallBack<List<LoginBean>> baseCallBack) {
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

    public void save(List<LoginBean> data, BaseCallBack<List<LoginBean>> baseCallBack) {
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
            }
        });
    }

}
