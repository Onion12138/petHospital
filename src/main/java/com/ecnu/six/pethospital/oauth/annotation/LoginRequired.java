package com.ecnu.six.pethospital.oauth.annotation;


import java.lang.annotation.*;

/**
 * @author LEO D PEN
 * @date 2021
 * @desc
 * @since
 */
@Target({ ElementType.METHOD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LoginRequired {

    int role() default 0;

}
