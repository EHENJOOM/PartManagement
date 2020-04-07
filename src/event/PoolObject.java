package event;

/**
 * @author 赵洪苛
 * @date 2020/03/24 22:07
 * @description 对象池中的对象
 */
public class PoolObject implements BasePoolObject {

    private String topic;
    private int msgCode;
    private int resultCode;
    private Object object;

    public PoolObject() {}

    public PoolObject(String topic, int msgCode, int resultCode, Object object) {
        this.topic = topic;
        this.msgCode = msgCode;
        this.resultCode = resultCode;
        this.object = object;
    }

    @Override
    public void reset() {
        topic = null;
        msgCode = 0;
        resultCode = 0;
        object = null;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public void setResultCode(int resultCode) {
        this.resultCode = resultCode;
    }

    public void setObject(Object object) {
        this.object = object;
    }

    public void setMsgCode(int msgCode) {
        this.msgCode = msgCode;
    }

    public String getTopic() {
        return topic;
    }

    public Object getObject() {
        return object;
    }

    public int getResultCode() {
        return resultCode;
    }

    public int getMsgCode() {
        return msgCode;
    }
}
