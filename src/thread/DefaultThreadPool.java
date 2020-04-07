package thread;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author 赵洪苛
 * @date 2020/03/21 11:21
 * @description 默认线程池
 */
public class DefaultThreadPool<T extends Runnable> implements ThreadPool<T> {

    /**
     * 最大消费者线程数
     */
    private static int MAX_WORKER_COUNT = 10;

    /**
     * 默认消费者线程数
     */
    private static int DEFAULT_WORKER_COUNT = 3;

    /**
     * 最小消费者线程数
     */
    private static int MIN_WORKER_COUNT = 1;

    /**
     * 任务队列
     */
    private final LinkedList<T> jobs = new LinkedList<>();

    /**
     * 消费者线程队列
     */
    private List<Worker> workers = Collections.synchronizedList(new LinkedList<>());

    private int workerCount = 0;
    private AtomicInteger count = new AtomicInteger();

    public DefaultThreadPool() {
        this(DEFAULT_WORKER_COUNT);
    }

    public DefaultThreadPool(int defaultWorkerSize) {
        if (defaultWorkerSize > MAX_WORKER_COUNT) {
            throw new IllegalArgumentException("最大消费者线程数不能超过：" + MAX_WORKER_COUNT);
        } else if (defaultWorkerSize < MIN_WORKER_COUNT) {
            throw new IllegalArgumentException("最大消费者线程数不能小于：" + MIN_WORKER_COUNT);
        }

        initWorkers(defaultWorkerSize);
    }

    private void initWorkers(int num) {
        for (int i = 0; i < num; ++i) {
            Worker worker = new Worker();
            workers.add(worker);
            Thread thread = new Thread(worker,"消费者线程" + count.getAndIncrement());
            thread.setDaemon(true);
            thread.start();
            ++workerCount;
        }
    }


    /**
     * @see ThreadPool#shutdown()
     */
    @Override
    public void shutdown() {
        workers.forEach(Worker::shutdown);
    }

    /**
     * @see ThreadPool#execute(Object)
     * @param job 线程任务
     */
    @Override
    public void execute(T job) {
        if (job != null) {
            synchronized (jobs) {
                jobs.addLast(job);
                jobs.notify();
            }
        }
    }

    /**
     * @see ThreadPool#addThread(int)
     * @param num 需要添加的消费者线程数量
     */
    @Override
    public void addThread(int num) {
        synchronized (jobs) {
            if (workerCount + num > MAX_WORKER_COUNT) {
                throw new IllegalArgumentException("消费者线程数量不能大于：" + MAX_WORKER_COUNT);
            }

            initWorkers(num);
        }
    }

    /**
     * @see ThreadPool#removeThread(int)
     * @param num 需要减少的消费者线程数量
     */
    @Override
    public void removeThread(int num) {
        synchronized (jobs) {
            if (workerCount - num < MIN_WORKER_COUNT) {
                throw new IllegalArgumentException("消费者线程数量不能小于：" + MIN_WORKER_COUNT);
            }

            for (int i = 0; i < num; ++i) {
                Worker worker = workers.remove(i);
                worker.shutdown();
                --workerCount;
            }
        }
    }

    @Override
    public int getWaitSize() {
        return workerCount;
    }

    class Worker implements Runnable {

        private volatile boolean running = true;

        @Override
        public void run() {
            while (running) {
                T job;
                synchronized (jobs) {
                    while (jobs.isEmpty()) {
                        try {
                            jobs.wait();
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                            return;
                        }
                    }
                    job = jobs.removeFirst();
                }
                if (job != null) {
                    try {
                        job.run();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        public void shutdown() {
            running = false;
        }
    }
}
