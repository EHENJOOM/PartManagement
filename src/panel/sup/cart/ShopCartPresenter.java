package panel.sup.cart;

import bean.ShopCartBean;
import com.sun.org.apache.xerces.internal.xs.datatypes.ByteList;
import mvp.BaseCallBack;
import mvp.BasePresenter;

import java.util.List;

/**
 * @author 赵洪苛
 * @date 2020/4/1 17:28
 * @description 购物车逻辑处理器
 */
public class ShopCartPresenter extends BasePresenter<ShopCartView> {

    private ShopCartModel model = new ShopCartModel();

    public void select() {
        model.select(new BaseCallBack<List<ShopCartBean>>() {
            @Override
            public void onSucceed(List<ShopCartBean> data) {
                if (isViewAttached()) {
                    getView().update(data);
                }
            }

            @Override
            public void onFailed(String msg) {
                if (isViewAttached()) {
                    getView().showError(msg);
                }
            }
        });
    }

    public void delete(List<ShopCartBean> data) {
        model.delete(data, new BaseCallBack<List<ShopCartBean>>() {
            @Override
            public void onSucceed(List<ShopCartBean> data) {
                if (isViewAttached()) {
                    getView().showInfo("删除成功！");
                    getView().applyDelete(data);
                    getView().resetAll();
                }
            }

            @Override
            public void onFailed(String msg) {

            }
        });
    }

    public void save(List<ShopCartBean> data) {
        model.save(data, new BaseCallBack<List<ShopCartBean>>() {
            @Override
            public void onSucceed(List<ShopCartBean> data) {
                if (isViewAttached()) {
                    getView().showInfo("保存成功！");
                    getView().resetAll();
                }
            }

            @Override
            public void onFailed(String msg) {
                if (isViewAttached()) {
                    getView().showError(msg);
                }
            }
        });
    }

}
