package panel.admin.user;

import bean.UserBean;
import mvp.BaseCallBack;
import org.hibernate.Session;
import thread.ThreadPoolEnum;
import util.SessionFactoryEnum;

import java.util.List;

/**
 * @author 赵洪苛
 * @date 2020/3/30 15:07
 * @description 用户管理数据处理器
 */
public class UserManageModel {

    public void select(BaseCallBack<List<UserBean>> baseCallBack) {
        ThreadPoolEnum.getInstance().execute(() -> {
            Session session = SessionFactoryEnum.getInstance().getSession();
            if (session == null) {
                return;
            }
            try {
                List<UserBean> list = session.createQuery("from UserBean ").list();
                baseCallBack.onSucceed(list);
            } catch (Exception e) {
                baseCallBack.onFailed("查询失败！");
            } finally {
                session.close();
            }
        });
    }

}
