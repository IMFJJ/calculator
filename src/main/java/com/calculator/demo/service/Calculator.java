package com.calculator.demo.service;

import com.calculator.demo.enmus.OperationType;
import com.calculator.demo.task.RespResult;

import java.math.BigDecimal;

/**
 * @Author: fangjunjie
 * @createTime: 2023年02月14日 17:41:50
 * @Description: 这个是计算器对外暴露的接口
 */
public interface Calculator {

    /**
     * @param
     * @param operationType 操作类型  bigDecimal 操作数
     * @author fangjunjie
     * @date: 2023/2/14 22:46
     * @description: 计算器操作接口
     */
    public void operate(OperationType operationType, BigDecimal bigDecimal);

    /**
     * @author fangjunjie
     * @date: 2023/2/15 0:38
     * @description: redo和undo不需要操作数
     */
    public void operate(OperationType operationType);


    public void undo();

    public void redo();

    /**
     * @author fangjunjie
     * @date: 2023/2/15 0:44
     * @description: 获取计算结果, 如果没计算完则阻塞等待
     */
    public RespResult<BigDecimal> getResult();

}