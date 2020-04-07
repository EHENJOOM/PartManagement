package event;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @author 赵洪苛
 * @date 2020/03/24 22:28
 * @description 事件中心，用于注册、注销、分发事件
 */
public class EventCenter {

    /**
     * 已注册的事件主题和时间监听器
     */
    private static final Map<String, Object> listenerMap = new HashMap<>();

    /**
     * 监听器锁
     */
    private static final Object lock = new Object();

    /**
     * 对象池
     */
    private static final DefaultObjectPool pool = new DefaultObjectPool(5);

    /**
     * 注册事件监听器
     * 事件接收方需实现{@link EventListener}接口
     * @param listener 事件监听器
     * @param topic 事件主题
     */
    public static void registerEventListener(EventListener listener, String topic) {
        registerEventListener(listener, new String[]{topic});
    }

    /**
     * 注册事件监听器
     * @param listener 事件监听器
     * @param topics 事件主题
     */
    public static void registerEventListener(EventListener listener, String[] topics) {
        if (null == listener || null == topics) {
            return;
        }

        synchronized (lock) {
            for (String topic : topics) {
                if (topic.isEmpty()) {
                    continue;
                }

                Object object = listenerMap.get(topic);
                if (null == object) {
                    listenerMap.put(topic, listener);
                } else if (object instanceof EventListener) {
                    EventListener old = (EventListener) object;
                    if (listener == old) {
                        continue;
                    }
                    List<EventListener> list = new LinkedList<>();
                    list.add(old);
                    list.add(listener);
                    listenerMap.put(topic, list);
                } else if (object instanceof List) {
                    List<EventListener> old = (List<EventListener>) object;
                    if (old.indexOf(listener) >= 0) {
                        continue;
                    }
                    old.add(listener);
                }
            }
        }
    }

    /**
     * 注销事件监听器
     * @param listener 事件监听器
     * @param topic 事件主题
     */
    public static void unregisterEventListener(EventListener listener, String topic) {
        unregisterEventListener(listener, new String[]{topic});
    }

    /**
     * 注销事件监听器
     * @param listener 事件监听器
     * @param topics 事件主题
     */
    public static void unregisterEventListener(EventListener listener, String[] topics) {
        if (null == listener || null == topics) {
            return;
        }

        synchronized (lock) {
            for (String topic : topics) {
                if (topic.isEmpty()) {
                    continue;
                }

                Object object = listenerMap.get(topic);
                if (null == object) {
                    continue;
                } else if (object instanceof EventListener) {
                    if (object == listener) {
                        listenerMap.remove(topic);
                    }
                } else if (object instanceof List) {
                    List<EventListener> list = (List<EventListener>) object;
                    list.remove(listener);
                }
            }
        }
    }

    /**
     * 分发事件
     * 当在一个窗口中想要通知另一个窗口某事件时，调用此方法
     * @param topic 事件主题
     * @param msgCode 消息码
     * @param resultCode 结果码
     * @param object 可能会用到的数据
     */
    public static void dispatchEvent(String topic, int msgCode, int resultCode, Object object) {
        if (!topic.isEmpty()) {
            PoolObject event = pool.get();
            event.setTopic(topic);
            event.setMsgCode(msgCode);
            event.setResultCode(resultCode);
            event.setObject(object);
            dispatchEvent(event);
        }
    }

    /**
     * 分发事件
     * @param event 事件对象
     */
    public static void dispatchEvent(PoolObject event) {
        if (listenerMap.size() == 0) {
            return;
        }

        if (null != event && !event.getTopic().isEmpty()) {
            String topic = event.getTopic();
            EventListener listener;
            List<EventListener> list;

            synchronized (lock) {
                Object object = listenerMap.get(topic);
                if (null == object) {
                    return;
                }
                if (object instanceof EventListener) {
                    listener = (EventListener) object;
                    listener.onEvent(topic, event.getMsgCode(), event.getResultCode(), event.getObject());
                } else if (object instanceof List) {
                    list = (LinkedList<EventListener>) object;
                    list.forEach(l -> l.onEvent(topic, event.getMsgCode(), event.getResultCode(), event.getObject()));
                }

                pool.returnObject(event);
            }
        }
    }

}
