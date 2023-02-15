package com.calculator.demo.task;


/**
 * @Author: fangjunjie
 * @createTime: 2023年02月14日 20:57:34
 * @Description: 重做任务
 */
public class RedoCalculatorTask extends AbstractCalculatorTask {
    public RedoCalculatorTask() {
        super();
    }

    public void valid() {
        Integer index = this.context.getUndoOperationIndex();
        Integer size = this.context.getResultList().size();
        if (index == -1) {
            throw new RuntimeException(Thread.currentThread().getName() + ":还未执行undo撤回操作,不能执行redo操作");
        }
        if (index == (size - 1)) {
            throw new RuntimeException(Thread.currentThread().getName() + ":不能再redo操作了,不然超过撤回数了!");
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
        this.context.setUndoOperationIndex(++index);
        this.context.setLastResult(this.context.getResultList().get(index));
        System.out.println(Thread.currentThread().getName() + ":已经执行重做操作,结果为:" + this.context.getLastResult());

    }
}
