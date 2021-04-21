package com.ecnu.six.pethospital.disease.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

/**
 * @author onion
 * @date 2021/3/29 -10:28 上午
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CaseDTO {
    private Long caseId;
    @NotEmpty
    private String inspection;
    @NotEmpty
    private String name;
    @NotEmpty
    private String diagnosis;
    @NotEmpty
    private String treatment;
    private Boolean valid;
    private String createTime;
    private String inspectionVideoUri;
    private String diagnosisPhotoUri;
    private String diagnosisVideoUri;
    private String treatmentPhotoUri;
    private String treatmentVideoUri;
}
