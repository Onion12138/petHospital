package com.ecnu.six.pethospital.oauth.VO;

import com.ecnu.six.pethospital.oauth.entity.LocalUser;
import lombok.Data;

/**
 * @author LEO D PEN
 * @date 2021
 * @desc
 * @since
 */
@Data
public class LogVO {

    private LocalUser user;

    private Integer socialUsrId;

    private String token;

}
