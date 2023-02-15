package com.calculator.demo;

import com.calculator.demo.content.CalculatrorContext;
import com.calculator.demo.enmus.OperationType;
import com.calculator.demo.service.Calculator;
import com.calculator.demo.service.impl.CalculatorImpl;

import java.math.BigDecimal;

/**
 * @Author: fangjunjie
 * @createTime: 2023年02月14日 23:26:56
 * @Description:
 */
public class Main {
    public static void main(String[] args) {
        //设置计算结果精度,默认保留2位
        Calculator calculator = new CalculatorImpl(CalculatrorContext.getInstance().setScale(2));
        try {
            for (int i = 1; i <= 10; i++) {
                calculator.operate(OperationType.ADD, new BigDecimal(i));
            }
            System.out.println(Thread.currentThread().getName() + ":获取的结果为:" + calculator.getResult().getData());
            calculator.operate(OperationType.DIVIDE, new BigDecimal("1.3"));

//          calculator.operate(OperationType.DIVIDE, new BigDecimal("0"));//提示报错终止计算
            for (int i = 0; i <= 2; i++) {
                calculator.undo();
            }
            System.out.println(Thread.currentThread().getName() + ":获取的结果为:" + calculator.getResult().getData());
            calculator.redo();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }


    }
}
