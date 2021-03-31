package com.ecnu.six.pethospital.oauth.form;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author LEO D PEN
 * @date 2021
 * @desc
 * @since
 */
@Data
public class AppSocialUsrForm {

    // 必传
    @NotBlank
    private String uuid;

    // 必传
    @NotBlank
    private String source;

    private String accessToken;

    private Integer expirein;

    private String nickName;

    private String avatar;

    private String location;

    private String gender;

    private String email;

}
