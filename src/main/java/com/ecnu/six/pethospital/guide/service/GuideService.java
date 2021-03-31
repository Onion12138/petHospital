package com.ecnu.six.pethospital.guide.service;

import com.ecnu.six.pethospital.guide.vo.DepartmentResponse;

import java.util.ArrayList;
import java.util.List;

/**
 * @author YueChen
 * @version 1.0
 * @date 2021/3/29 8:55
 */
public interface GuideService {
    List<DepartmentResponse> getDepartments();
}
