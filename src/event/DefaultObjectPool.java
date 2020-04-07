package event;

/**
 * @author 赵洪苛
 * @date 2020/03/24 22:23
 * @description 默认的对象池实现
 */
public class DefaultObjectPool extends AbstractObjectPool<PoolObject> {

    public DefaultObjectPool(int capacity) {
        super(capacity);
    }

    @Override
    protected PoolObject[] createObjectPool(int capacity) {
        return new PoolObject[capacity];
    }

    @Override
    protected PoolObject createNewObject() {
        return new PoolObject();
    }
}
