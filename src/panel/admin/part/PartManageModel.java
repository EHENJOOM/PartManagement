package panel.admin.part;

import bean.PartBean;
import constant.Config;
import mvp.BaseCallBack;
import org.hibernate.Session;
import thread.ThreadPoolEnum;
import util.SessionFactoryEnum;

import java.util.List;

/**
 * @author 赵洪苛
 * @date 2020/3/30 11:53
 * @description 零件管理数据处理器
 */
public class PartManageModel {

    public void select(BaseCallBack<List<PartBean>> baseCallBack) {
        ThreadPoolEnum.getInstance().execute(() -> {
            Session session = SessionFactoryEnum.getInstance().getSession();
            if (session == null) {
                return;
            }
            try {
                List list = session.createQuery("from PartBean ").list();
                baseCallBack.onSucceed(list);
            } catch (Exception e) {
                baseCallBack.onFailed("查询失败！");
            } finally {
                session.close();
            }
        });
    }

    public void delete(List<PartBean> partBeans, BaseCallBack<List<PartBean>> baseCallBack) {
        ThreadPoolEnum.getInstance().execute(() -> {
            Session session = SessionFactoryEnum.getInstance().getSession();
            if (session == null) {
                return;
            }
            try {
                partBeans.stream()
                    .filter(temp -> temp.getState() == Config.SELECTED)
                    .forEach(session::delete);
                session.beginTransaction().commit();
                baseCallBack.onSucceed(partBeans);
            } catch (Exception e) {
                baseCallBack.onFailed("删除失败！");
            } finally {
                session.close();
            }
        });
    }

    public void save(List<PartBean> partBeans, BaseCallBack<List<PartBean>> baseCallBack) {
        ThreadPoolEnum.getInstance().execute(() -> {
            Session session = SessionFactoryEnum.getInstance().getSession();
            if (session == null) {
                return;
            }
            try {
                partBeans.stream()
                    .filter(temp -> temp.getState() == Config.SELECTED)
                    .forEach(session::saveOrUpdate);
                session.beginTransaction().commit();
                baseCallBack.onSucceed(partBeans);
            } catch (Exception e) {
                baseCallBack.onFailed("保存失败！");
            } finally {
                session.close();
            }
        });
    }

}
