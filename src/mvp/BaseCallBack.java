package mvp;

/**
 * @author 赵洪苛
 * @date 2020/03/21 11:13
 * @description 默认消息回调，Model获取数据后根据此回调将数据返回至Presenter，进行系列操作后返回视图更新界面UI。可根据需要继承此接口。
 */
public interface BaseCallBack<T> {

    /**
     * 事务处理成功回调
     * @param data 处理成功的返回数据
     */
    void onSucceed(T data);

    /**
     * 事务处理失败回调
     * @param msg 处理失败的原因
     */
    void onFailed(String msg);

}
