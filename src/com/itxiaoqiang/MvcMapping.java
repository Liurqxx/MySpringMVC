package com.itxiaoqiang;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 创建注解
 *
 * @author hello_liu
 * create by xiaoqiang on 2019/7/13
 */

@Retention(RetentionPolicy.RUNTIME)
@Target(value = ElementType.METHOD)
public @interface MvcMapping {
    public String value();
}
