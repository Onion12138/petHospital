package com.ecnu.six.pethospital.guide.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author YueChen
 * @version 1.0
 * @date 2021/3/29 8:49
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MedicineResponse {
    private String name;
    private String message;
    private String picture;
}
