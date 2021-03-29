package com.ecnu.six.pethospital.guide.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author YueChen
 * @version 1.0
 * @date 2021/3/29 8:43
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EquipmentResponse {
    private String name;
    private String message;
    private Integer[] position = new Integer[2];
    private String picture;
}
