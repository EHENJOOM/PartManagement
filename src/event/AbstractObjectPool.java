package event;

/**
 * @author 赵洪苛
 * @date 2020/03/24 22:10
 * @description 对象池抽象类
 */
public abstract class AbstractObjectPool<T extends BasePoolObject> {

    /**
     * 存储对象的容器
     */
    private T[] container;

    /**
     * 容器对象锁
     */
    private final Object lock = new Object();

    /**
     * 对象池中可用的对象数
     */
    private int length;

    /**
     * 构造函数，只对对象池进行初始化
     * @param capacity 对象池容量
     */
    public AbstractObjectPool(int capacity) {
        container = createObjectPool(capacity);
    }

    /**
     * 创建对象池
     * @param capacity 对象池容量
     * @return 对象池
     */
    protected abstract T[] createObjectPool(int capacity);

    /**
     * 创建一个新的对象
     * @return 新的对象
     */
    protected abstract T createNewObject();

    /**
     * 从对象池中获取空闲的对象，若对象池中没有空闲对象，则自己创建一个
     * @return 对象
     */
    public final T get() {
        T object = findFreeObject();
        if (object == null) {
            object = createNewObject();
        } else {
            object.reset();
        }
        return object;
    }

    /**
     * 把对象放回对象池中，当对象池中已经放满了对象，则停止放入
     * @param object 用完的对象
     */
    public final void returnObject(T object) {
        synchronized (lock) {
            int size = container.length;
            if (length < size) {
                container[length] = object;
                ++length;
            }
        }
    }

    /**
     * 寻找对象池中空闲状态的对象，用于用户使用
     * @return 空闲对象
     */
    private T findFreeObject() {
        T object = null;
        synchronized (lock) {
            if (length > 0) {
                --length;
                object = container[length];
                container[length] = null;
            }
        }
        return object;
    }
}
