package event;

/**
 * @author 赵洪苛
 * @date 2020/03/24 13:59
 * @description 事件主题集合
 */
public class Events {

    public static final String CANNOT_CAST_STRING_TO_INT = "cannot_cast_string_to_int";

    public static final String ERROR_DATE_FORMAT = "error_date_format";

    public static final String ERROR_NUMBER_FORMAT = "error_number_format";

    public static final String ADD_INTO_SHOP_CART = "add_into_shop_cart";

    public static final String DELETE_FROM_SHOP_CART = "delete_from_shop_cart";

    /**
     * 管理员修改登录账号信息
     */
    public static final String ADMIN_CHANGE_LOGIN = "admin_change_login";

    /**
     * 管理员修改教师信息
     */
    public static final String ADMIN_CHANGE_TEACHER = "admin_change_teacher";

    /**
     * 管理员修改学生信息
     */
    public static final String ADMIN_CHANGE_STUDENT = "admin_change_student";
}
