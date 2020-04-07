package constant;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 赵洪苛
 * @date 2020/3/29 12:03
 * @description 订单状态
 */
public class ConstantStatus {

    private static Map<Integer, String> orderMap = new HashMap<>();

    private static Map<String, Integer> map = new HashMap<>();

    static {
        orderMap.put(Config.ORDER_STATUS_AUDIT, Config.ORDER_STATUS[0]);
        orderMap.put(Config.ORDER_STATUS_REFUSE_AUDIT, Config.ORDER_STATUS[1]);
        orderMap.put(Config.ORDER_STATUS_UNPAID, Config.ORDER_STATUS[2]);
        orderMap.put(Config.ORDER_STATUS_PAID, Config.ORDER_STATUS[3]);
        orderMap.put(Config.ORDER_STATUS_DELIVERY, Config.ORDER_STATUS[4]);
        orderMap.put(Config.ORDER_STATUS_TRANSPORTING, Config.ORDER_STATUS[5]);
        orderMap.put(Config.ORDER_STATUS_COMPLETED, Config.ORDER_STATUS[6]);

        orderMap.put(Config.USER_LOGIN, Config.IDENTITY_LOGIN[0]);
        orderMap.put(Config.ADMIN_LOGIN, Config.IDENTITY_LOGIN[1]);
        orderMap.put(Config.SUPER_ADMIN_LOGIN, Config.IDENTITY_LOGIN[2]);

        map.put(Config.IDENTITY_LOGIN[0], Config.USER_LOGIN);
        map.put(Config.IDENTITY_LOGIN[1], Config.ADMIN_LOGIN);
        map.put(Config.IDENTITY_LOGIN[2], Config.SUPER_ADMIN_LOGIN);

        map.put(Config.ORDER_STATUS[0], Config.ORDER_STATUS_AUDIT);
        map.put(Config.ORDER_STATUS[1], Config.ORDER_STATUS_REFUSE_AUDIT);
        map.put(Config.ORDER_STATUS[2], Config.ORDER_STATUS_UNPAID);
        map.put(Config.ORDER_STATUS[3], Config.ORDER_STATUS_PAID);
        map.put(Config.ORDER_STATUS[4], Config.ORDER_STATUS_DELIVERY);
        map.put(Config.ORDER_STATUS[5], Config.ORDER_STATUS_TRANSPORTING);
        map.put(Config.ORDER_STATUS[0], Config.ORDER_STATUS_COMPLETED);
    }

    public static String getStatus(int status) {
        return orderMap.get(status);
    }

    public static int getStatusByString(String str) {
        return map.get(str);
    }

}
