package panel.admin.part;

import bean.PartBean;
import mvp.BaseView;

import java.util.List;

/**
 * @author 赵洪苛
 * @date 2020/3/30 11:52
 * @description 零件管理视图
 */
public interface PartManageView extends BaseView {

    void update(List<PartBean> data);

    void applyDelete(List<PartBean> data);

    void resetAll();

}
