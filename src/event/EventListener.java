package event;

/**
 * @author 赵洪苛
 * @date 2020/03/24 22:25
 * @description 事件监听器
 */
public interface EventListener {

    /**
     * 事件处理器
     * @param topic 事件主题
     * @param msgCode 消息代码
     * @param resultCode 结果代码
     * @param object 可能会用到的数据
     */
    void onEvent(String topic, int msgCode, int resultCode, Object object);

}
