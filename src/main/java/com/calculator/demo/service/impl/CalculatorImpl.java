package com.calculator.demo.service.impl;

import com.calculator.demo.content.CalculatrorContext;
import com.calculator.demo.enmus.OperationType;
import com.calculator.demo.service.Calculator;
import com.calculator.demo.task.AbstractCalculatorTask;
import com.calculator.demo.task.PushTaskExecuteEngine;
import com.calculator.demo.task.RespResult;
import com.calculator.demo.task.factory.CalculatorTaskFactory;

import java.math.BigDecimal;

/**
 * @Author: fangjunjie
 * @createTime: 2023年02月14日 22:57:50
 * @Description:
 */
public class CalculatorImpl implements Calculator {
    CalculatrorContext calculatrorContext;
    PushTaskExecuteEngine pushTaskExecuteEngine;

    public CalculatorImpl() {
        this.pushTaskExecuteEngine = PushTaskExecuteEngine.getInstance();
        this.calculatrorContext = CalculatrorContext.getInstance();
    }

    public CalculatorImpl(CalculatrorContext calculatrorContext) {
        this.pushTaskExecuteEngine = PushTaskExecuteEngine.getInstance();
        this.calculatrorContext = calculatrorContext;
    }

    @Override
    public void operate(OperationType operationType, BigDecimal bigDecimal) {
        //任务工厂获取任务
        try {
            AbstractCalculatorTask task = CalculatorTaskFactory.createCalculatorTask(calculatrorContext, operationType, bigDecimal);
            task.valid();
            pushTaskExecuteEngine.addTask(task);
        } catch (Exception e) {
            //关闭计算子线程
//            pushTaskExecuteEngine.shutdownNow();
            throw new RuntimeException(e.getMessage());
        }

    }

    @Override
    public void operate(OperationType operationType) {
        operate(operationType, null);
    }

    @Override
    public void undo() {
        pushTaskExecuteEngine.getResult();//主要是让前面的任务执行完,才能确定是否可以撤回
        operate(OperationType.UNDO);
    }

    @Override
    public void redo() {
        //同步数据
        pushTaskExecuteEngine.getResult();//主要是让前面的任务执行完,才能确定是否可以重做
        operate(OperationType.REDO);
    }

    @Override
    public RespResult<BigDecimal> getResult() {
        //这里任务执行完了才获取结果
        return pushTaskExecuteEngine.getResult();
    }
}
