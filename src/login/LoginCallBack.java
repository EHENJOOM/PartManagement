package login;

import bean.UserBean;
import mvp.BaseCallBack;

/**
 * @author 赵洪苛
 * @date 2020/3/29 23:35
 * @description 登录回调
 */
public interface LoginCallBack<T> extends BaseCallBack<T> {

    void onInit(UserBean userBean);

}
