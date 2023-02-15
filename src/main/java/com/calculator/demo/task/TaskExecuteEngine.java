package com.calculator.demo.task;

import java.math.BigDecimal;

/**
 * @author fangjunjie
 * @date: 2023/2/14 20:55
 * @description: 计算任务调度器
 */
public interface TaskExecuteEngine<T extends AbstractCalculatorTask> {

    public RespResult<String> addTask(T task);

    public void processTasks();

    public RespResult<BigDecimal> getResult();

}
