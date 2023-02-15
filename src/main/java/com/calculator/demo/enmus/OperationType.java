package com.calculator.demo.enmus;

import com.calculator.demo.task.*;

/**
 * @author fangjunjie
 * @date: 2023/2/15 16:46
 * @description: 操作的类型枚举
 */
public enum OperationType {
    ADD("相加", AddCalculatorTask.class),
    SUBTRACTION("相减", SubtractionCalculatorTask.class),
    MULTIPLY("相乘", MultiplyCalculatorTask.class),
    DIVIDE("相除", DivideCalculatorTask.class),
    REDO("向前重做", RedoCalculatorTask.class),
    UNDO("向后回滚", UndoCalculatorTask.class);

    private String typeName;
    private Class clazzName;

    OperationType(String typeName, Class<? extends AbstractCalculatorTask> clazzName) {
        this.typeName = typeName;
        this.clazzName = clazzName;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public Class getClazzName() {
        return clazzName;
    }

    public void setClazzName(Class clazzName) {
        this.clazzName = clazzName;
    }
}
