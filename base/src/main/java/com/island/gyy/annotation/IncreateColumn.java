package com.island.gyy.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created with Android Studio.
 * User: Admin
 * Date: 2016/9/8
 * Time: 9:25
 * Describe:
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface IncreateColumn {
}
