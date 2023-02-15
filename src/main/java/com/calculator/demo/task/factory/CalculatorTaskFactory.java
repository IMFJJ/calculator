package com.calculator.demo.task.factory;

import com.calculator.demo.content.CalculatrorContext;
import com.calculator.demo.enmus.OperationType;
import com.calculator.demo.task.AbstractCalculatorTask;

import java.math.BigDecimal;

/**
 * @Author: fangjunjie
 * @createTime: 2023年02月14日 23:52:51
 * @Description: 创建任务的工厂
 */
public class CalculatorTaskFactory {


    public static AbstractCalculatorTask createCalculatorTask(CalculatrorContext calculatrorContext, OperationType operationType, BigDecimal operands) throws Exception {
        Class<AbstractCalculatorTask> clazz = operationType.getClazzName();
        if (clazz != null) {
            AbstractCalculatorTask abstractCalculatorTask = clazz.newInstance();
            abstractCalculatorTask.setContext(calculatrorContext);
            abstractCalculatorTask.setOperands(operands);
            return abstractCalculatorTask;
        }
        return null;
    }
}
