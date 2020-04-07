package thread;

/**
 * @author 赵洪苛
 * @date 2020/03/21 11:31
 * @description 单例模式，封装线程池常用方法
 */
public enum ThreadPoolEnum {

    /**
     * 单例对象
     */
    INSTANCE;

    /**
     * 线程池实例对象
     */
    private ThreadPool<Runnable> pool = new DefaultThreadPool<>();

    public static ThreadPoolEnum getInstance() {
        return INSTANCE;
    }

    /**
     * @see ThreadPool#execute(Object)
     * @param runnable 要处理的事务
     */
    public void execute(Runnable runnable) {
        pool.execute(runnable);
    }

    /**
     * @see ThreadPool#shutdown()
     */
    public void shutdown() {
        pool.shutdown();
    }

    /**
     * @see ThreadPool#addThread(int)
     * @param num 增加的消费者线程数量
     */
    public void addThread(int num) {
        pool.addThread(num);
    }

    /**
     * @see ThreadPool#removeThread(int)
     * @param num 减少的消费者线程数量
     */
    public void removeThread(int num) {
        pool.removeThread(num);
    }

    /**
     * @see ThreadPool#getWaitSize()
     * @return 当前正坐在等待执行的任务数量
     */
    public int getWaitSize() {
        return pool.getWaitSize();
    }
}
