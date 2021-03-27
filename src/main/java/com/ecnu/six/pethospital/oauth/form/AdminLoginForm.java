package com.ecnu.six.pethospital.oauth.form;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

/**
 * @author LEO D PEN
 * @date 2021
 * @desc
 * @since
 */
@Data
public class AdminLoginForm {

    @NotBlank
    private String admName; // 唯一的，由我们来插入好了，暂不提供注册接口

    @NotBlank
    private String pwd;
}
