package com.ecnu.six.pethospital.oauth.VO;

import com.ecnu.six.pethospital.oauth.entity.Adm;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author LEO D PEN
 * @date 2021
 * @desc
 * @since
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdminLogVO {

    private Integer socialUsrId;

    private Adm adm;

    private String token;

}
