package com.calculator.demo.task;

import java.math.BigDecimal;

/**
 * @Author: fangjunjie
 * @createTime: 2023年02月14日 20:57:34
 * @Description: 计算器任务
 */
public class UndoCalculatorTask extends AbstractCalculatorTask {
    public UndoCalculatorTask() {
        super();
    }

    public void valid() {
        //判断是否可以undo
        if (context.getResultList().size() > 1 && context.getUndoOperationIndex() == 0) {
            throw new RuntimeException(Thread.currentThread().getName() + ":不能再undo操作,到底了!");
        }
    }

    public void afterRetunring() {

    }

    /**
     * @author fangjunjie
     * @date: 2023/2/14 21:00
     * @description: 钩子方法, 不同的增删改查/undo/redo的操作有不同的实现
     */
    public void execute() {
        Integer index = this.context.getUndoOperationIndex();
        Integer size = context.getResultList().size();
        if (index == -1) {
            index = size - 1;                //初始化undo下标
        }
        if (size == 1) {//操作一次就撤回
            this.context.setLastResult(BigDecimal.ZERO);
            return;
        }
        this.context.setUndoOperationIndex(--index);

        this.context.setLastResult(this.context.getResultList().get(index));
        System.out.println(Thread.currentThread().getName() + ":已经执行撤回操作,结果为:" + this.context.getLastResult());

    }
}
