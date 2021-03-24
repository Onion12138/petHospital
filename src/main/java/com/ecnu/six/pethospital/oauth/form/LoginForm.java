package com.ecnu.six.pethospital.oauth.form;

import cn.mpy634.annotation.BetterBuilder;
import cn.mpy634.enums.BuilderType;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

/**
 * @author LEO D PEN
 * @date 2021
 * @desc
 * @since
 */
@BetterBuilder(BUILDER_TYPE = BuilderType.NO_BUILDER)
public class LoginForm {

    @NotBlank
    @Length(min = 11, max = 11)
    private String stuId;

    @NotBlank
    @Length(min = 8, max = 50)
    private String pwd;

    /**
     * 待绑定的social usrId
     * 默认没有，不传
     */
    private String socialUsrId;
}
