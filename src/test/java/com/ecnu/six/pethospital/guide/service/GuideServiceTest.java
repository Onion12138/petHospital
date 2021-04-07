package com.ecnu.six.pethospital.guide.service;

import com.ecnu.six.pethospital.guide.dao.DepartmentDao;
import com.ecnu.six.pethospital.guide.dao.EquipmentDao;
import com.ecnu.six.pethospital.guide.dao.MedicineDao;
import com.ecnu.six.pethospital.guide.service.impl.GuideServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author YueChen
 * @version 1.0
 * @date 2021/3/31 14:52
 */
@SpringBootTest
@Slf4j
public class GuideServiceTest {
    @Mock
    private DepartmentDao departmentDao;

    @Mock
    private EquipmentDao equipmentDao;

    @Mock
    private MedicineDao medicineDao;

    @InjectMocks
    private GuideServiceImpl guideService;

    @Test
    @DisplayName("正确返回各科室信息")
    void shouldGetAllDepartments() {

    }
}
