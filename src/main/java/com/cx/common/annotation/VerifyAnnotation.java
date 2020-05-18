package com.cx.common.annotation;

import com.cx.common.entity.Function;
import com.cx.common.entity.Module;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 审核接口
 * @author Administrator
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface VerifyAnnotation {
    /**
     * 是否需要审核 默认需要
     */
    boolean isVerify() default true;
    /**
     * 模块描述
     */
    Module module();
    /**
     * 功能描述
     */
    Function function();
    /**
     * 功能 0添加  1修改  2删除   3批量删除  4启用
     */
    int flag() default 0;

}
