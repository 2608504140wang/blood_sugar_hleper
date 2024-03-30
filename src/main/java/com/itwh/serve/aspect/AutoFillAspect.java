package com.itwh.serve.aspect;

import com.itwh.common.annotation.AutoFillUser;
import com.itwh.common.constant.AutoFillConstant;
import com.itwh.common.context.BaseContext;
import com.itwh.common.enumeration.OperationType;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.time.LocalDateTime;

@Aspect
@Component
@Slf4j
public class AutoFillAspect {

    @Pointcut("execution(* com.itwh.serve.mapper.*.*(..)) && @annotation(com.itwh.common.annotation.AutoFillUser)")
    public void autoFillPt(){}

    //通知方法
    @Before("autoFillPt()")
    public void autoFill(JoinPoint joinPoint){
        log.info("公共字段自动填充...");
        //获取当前被拦截方法对数据库的操作类型
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        AutoFillUser autoFillUser = signature.getMethod().getAnnotation(AutoFillUser.class);
        OperationType operationType = autoFillUser.value();

        //获取当前被拦截方法的参数(实体对象)
        Object[] args = joinPoint.getArgs();
        if (args == null || args.length == 0){
            return;
        }
        //插入删除方法，一般参数都是一个实体对象
        Object entity = args[0];

        //准备赋值的数据
        LocalDateTime now = LocalDateTime.now();
        Long currentId = BaseContext.getCurrentId();

        //根据当前不同的操作类型，为对应的属性根据反射来赋值
        if (operationType == OperationType.INSERT){
            try {
                //拿到赋值的set方法
                Method setCreateTime = entity.getClass().getDeclaredMethod(AutoFillConstant.SET_CREATE_TIME, LocalDateTime.class);
                //Method setCreateUser = entity.getClass().getDeclaredMethod(AutoFillConstant.SET_CREATE_USER, Long.class);
                Method setUpdateTime = entity.getClass().getDeclaredMethod(AutoFillConstant.SET_UPDATE_TIME, LocalDateTime.class);
                //Method setUpdateUser = entity.getClass().getDeclaredMethod(AutoFillConstant.SET_UPDATE_USER, Long.class);

                //通过反射为对象属性赋值
                setCreateTime.invoke(entity, now);
                //setCreateUser.invoke(entity, currentId);
                setUpdateTime.invoke(entity, now);
                //setUpdateUser.invoke(entity, currentId);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (operationType == OperationType.UPDATE){
            try {
                //拿到赋值的set方法
                Method setUpdateTime = entity.getClass().getDeclaredMethod(AutoFillConstant.SET_UPDATE_TIME, LocalDateTime.class);
                Method setUpdateUser = entity.getClass().getDeclaredMethod(AutoFillConstant.SET_UPDATE_USER, Long.class);

                //通过反射为对象属性赋值
                setUpdateTime.invoke(entity, now);
                setUpdateUser.invoke(entity, currentId);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }
}
