package com.calculator.demo.task;


import com.calculator.demo.content.CalculatrorContext;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.concurrent.*;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Author: fangjunjie
 * @createTime: 2023年02月14日 20:50:58
 * @Description:添加任务和执行任务引擎 (生成者消费者模型)
 */
public class PushTaskExecuteEngine implements TaskExecuteEngine<AbstractCalculatorTask> {

    Lock lock = new ReentrantLock();

    LinkedList<AbstractCalculatorTask> tasks;

    //消费者等待唤醒
    Condition consumerCondition;

    //获取者等待唤醒

    Condition resultCondition;
    //定时去消费队列的任务
    private final ScheduledExecutorService processingExecutor = new ScheduledThreadPoolExecutor(1);
    private final ExecutorService executorService;

    public PushTaskExecuteEngine() {
        this(10L);
    }

    public PushTaskExecuteEngine(long processInterval) {
        tasks = new LinkedList<>();
        consumerCondition = lock.newCondition();
        resultCondition = lock.newCondition();
        processingExecutor.scheduleWithFixedDelay(new ProcessRunnable(), processInterval, processInterval, TimeUnit.MILLISECONDS);
        executorService = Executors.newSingleThreadExecutor();
    }

    private class ProcessRunnable implements Runnable {

        @Override
        public void run() {
            try {
                //处理任务的逻辑
                processTasks();

            } catch (Throwable e) {
                e.printStackTrace();
            }
        }
    }


    //生产者:由主线程添加任务
    @Override
    public RespResult<String> addTask(AbstractCalculatorTask task) {
        lock.lock();
        try {
            tasks.add(task);
            System.out.println(Thread.currentThread().getName() + ":添加任务");
            if (!tasks.isEmpty()) {
                consumerCondition.signal();
            }
        } finally {
            lock.unlock();
        }
        return RespResult.ok();
    }

    //消费者:由子线程定时执行
    @Override
    public void processTasks() {
        lock.lock();

        try {
            if (tasks.isEmpty()) {
                resultCondition.signal();
                consumerCondition.await();
            }
            AbstractCalculatorTask task = tasks.remove();
            task.process();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            lock.unlock();
        }
    }

    //获取结果:由主线程获取
    @Override
    public RespResult<BigDecimal> getResult() {
        lock.lock();
        try {
            if (!tasks.isEmpty()) {
                resultCondition.await();
            }
            BigDecimal lastResult = CalculatrorContext.getInstance().getLastResult();
            return RespResult.ok(lastResult);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            lock.unlock();

        }

    }
    private volatile static PushTaskExecuteEngine instance;

    public static PushTaskExecuteEngine getInstance() {
        //检查是否要阻塞
        if (instance == null) {
            synchronized (PushTaskExecuteEngine.class) {
                //检查是否要重新创建实例
                if (instance == null) {
                    instance = new PushTaskExecuteEngine();
                    //指令重排序的问题
                }
            }
        }
        return instance;
    }
}
