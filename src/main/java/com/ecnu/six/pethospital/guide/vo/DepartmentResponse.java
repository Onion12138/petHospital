package com.ecnu.six.pethospital.guide.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * @author YueChen
 * @version 1.0
 * @date 2021/3/29 8:33
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DepartmentResponse {
    private Integer id;
    private String name;
    private String message;
    private String roleName;
    private String picture;
    private Integer[] position = new Integer[4];

    private List<EquipmentResponse> equipments;
    private List<MedicineResponse> medicines;
}
