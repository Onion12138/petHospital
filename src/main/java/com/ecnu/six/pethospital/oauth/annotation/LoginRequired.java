package com.ecnu.six.pethospital.oauth.annotation;


import com.ecnu.six.pethospital.oauth.enums.Role;

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

    /**
     * 0 是 普通用户 ；1是 管理员
     * 管理员含括普通用户
     * @return
     */
    Role role() default Role.USER;

}
