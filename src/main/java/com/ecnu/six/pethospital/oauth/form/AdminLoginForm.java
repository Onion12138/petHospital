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
    private String admName; // 还是stuid

    @NotBlank
    private String pwd;
}
