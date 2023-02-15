package com.calculator.demo.content;

import lombok.Data;

import java.math.BigDecimal;
import java.util.ArrayList;

/**
 * @Author: fangjunjie
 * @createTime: 2023年02月14日 21:25:29
 * @Description: 计算器的上下文, 保存当前操作符、操作数之类的
 */
@Data
public class CalculatrorContext {
    //最新结果值
    protected BigDecimal lastResult;

    //undo操作的索引
    protected Integer undoOperationIndex;

    //计算结果的精度
    private int scale;

    //每次运算任务的结果记录,例如 1、3、6
    ArrayList<BigDecimal> resultList;

    private volatile static CalculatrorContext instance;

    public CalculatrorContext() {
        //初始化上下文
        this.resultList = new ArrayList<>();
        this.undoOperationIndex = -1;
        this.lastResult = BigDecimal.ZERO;
        this.scale = 2;
    }

    public static CalculatrorContext getInstance() {
        //检查是否要阻塞
        if (instance == null) {
            synchronized (CalculatrorContext.class) {
                //检查是否要重新创建实例
                if (instance == null) {
                    instance = new CalculatrorContext();
                    //指令重排序的问题
                }
            }
        }
        return instance;
    }

    public CalculatrorContext setScale(int scale) {
        this.scale = scale;
        return this;
    }
}
