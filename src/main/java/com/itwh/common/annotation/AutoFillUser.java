package com.itwh.common.annotation;


import com.itwh.common.enumeration.OperationType;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

//用于标识某个方法需要公共字段自动填充处理
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface AutoFillUser {

    //数据库操作类型: INSERT, UPDATE
    OperationType value();
}
