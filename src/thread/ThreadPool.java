package thread;

/**
 * @author 赵洪苛
 * @date 2020/03/21 11:17
 * @description 线程池方法接口
 */
public interface ThreadPool<T> {

    /**
     * 关闭所有消费者线程
     */
    void shutdown();

    /**
     * 添加并执行任务
     * @param job 线程任务
     */
    void execute(T job);

    /**
     * 添加消费者线程
     * @param num 需要添加的消费者线程数量
     */
    void addThread(int num);

    /**
     * 减少消费者线程
     * @param num 需要减少的消费者线程数量
     */
    void removeThread(int num);

    /**
     * 获取正在等待执行的任务的数量
     * @return 等待执行的任务数量
     */
    int getWaitSize();

}
