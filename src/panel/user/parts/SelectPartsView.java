package panel.user.parts;

import bean.PartBean;
import mvp.BaseView;

import java.util.List;

/**
 * @author 赵洪苛
 * @date 2020/3/24 15:56
 * @description 查询零件视图接口
 */
public interface SelectPartsView extends BaseView {

    void update(List<PartBean> partBeans);

    void resetAll();

}
