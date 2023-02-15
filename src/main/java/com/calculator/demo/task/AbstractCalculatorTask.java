package com.calculator.demo.task;

import com.calculator.demo.content.CalculatrorContext;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @Author: fangjunjie
 * @createTime: 2023年02月14日 20:57:34
 * @Description: 抽象任务类
 */
@Data
public abstract class AbstractCalculatorTask {
    protected CalculatrorContext context;
    //前端输入的操作数
    protected BigDecimal operands;

    public AbstractCalculatorTask() {
        this.context = context;
    }

    /**
     * @author fangjunjie
     * @date: 2023/2/14 21:00
     * @description: 钩子方法, 不操作有不同的校验
     */
    public void valid() {
    }


    /**
     * @author fangjunjie
     * @date: 2023/2/15 21:03
     * @description: 真正执行的逻辑
     */
    public void execute() {
    }

    /**
     * @author fangjunjie
     * @date: 2023/2/15 21:02
     * @description: 后置通知
     */
    public void afterRetunring() {
        Integer index = context.getUndoOperationIndex();

        //未使用undo
        if (index == -1) {
            //把计算结果缓存起来
            this.context.getResultList().add(context.getLastResult());
        } else {
            //使用过了undo 加减乘除需要向前移动索引
            this.context.setUndoOperationIndex(++index);
            //覆盖之前的值
            this.context.getResultList().set(index, context.getLastResult());
        }
    }


    /**
     * @author fangjunjie
     * @date: 2023/2/14 21:00
     * @description: 钩子方法, 不同的增删改查/undo/redo的操作有不同的实现
     */
    public void process() {
        execute();
        afterRetunring();
    }


}
