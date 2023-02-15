package com.calculator.demo.task;

import java.math.BigDecimal;

/**
 * @Author: fangjunjie
 * @createTime: 2023年02月14日 20:57:34
 * @Description: 相乘任务
 */
public class MultiplyCalculatorTask extends AbstractCalculatorTask {
    public MultiplyCalculatorTask() {
        super();
    }

    /**
     * @author fangjunjie
     * @date: 2023/2/14 21:00
     * @description: 钩子方法, 不同的增删改查/undo/redo的操作有不同的实现
     */
    public void execute() {
        Integer scale = this.context.getScale();
        BigDecimal lastResult = this.context.getLastResult();
        BigDecimal res = lastResult.multiply(this.operands).setScale(scale, BigDecimal.ROUND_HALF_UP);
        System.out.println(Thread.currentThread().getName() + ":已经执行" + lastResult + "*" + this.operands + "=" + res);
        this.context.setLastResult(res);
    }
}
