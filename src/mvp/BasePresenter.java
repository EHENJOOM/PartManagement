package mvp;

/**
 * @author 赵洪苛
 * @date 2020/03/21 11:08
 * @description MVP设计模式，基础逻辑处理器，需继承此类
 */
public class BasePresenter<T extends BaseView> {

    private T view;

    /**
     * 绑定视图
     * @param view 视图实例
     */
    public void attachView(T view) {
        this.view = view;
    }

    /**
     * 解绑视图
     */
    public void detachView() {
        view = null;
    }

    /**
     * 获取已绑定的视图
     * @return 绑定的view
     */
    public T getView() {
        return this.view;
    }

    /**
     * 获取视图是否已绑定
     * @return 是否已绑定视图
     */
    public boolean isViewAttached() {
        return view != null;
    }
}
