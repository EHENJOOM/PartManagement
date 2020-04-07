package event;

/**
 * @author 赵洪苛
 * @date 2020/03/24 22:06
 * @description 对象池中的对象必须实现此接口
 */
public interface BasePoolObject {

    /**
     * 重置对象的状态
     */
    void reset();
}
