package com.microsoft.order.annotation;

import java.lang.annotation.*;

/**
 * @
 * @Author:lvxiaoke
 * @Date: 2018/12/6
 * @
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface ReadOnlyConnectionAnnotation {
}
