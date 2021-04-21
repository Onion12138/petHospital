package com.ecnu.six.pethospital.guide.service;

import com.ecnu.six.pethospital.guide.dao.DepartmentDao;
import com.ecnu.six.pethospital.guide.dao.EquipmentDao;
import com.ecnu.six.pethospital.guide.dao.MedicineDao;
import com.ecnu.six.pethospital.guide.domain.Department;
import com.ecnu.six.pethospital.guide.domain.Equipment;
import com.ecnu.six.pethospital.guide.domain.Medicine;
import com.ecnu.six.pethospital.guide.domain.Role;
import com.ecnu.six.pethospital.guide.service.impl.GuideServiceImpl;
import com.ecnu.six.pethospital.guide.vo.DepartmentResponse;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

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
        Equipment equipment1 = Equipment.builder()
                .id(0)
                .name("设备1")
                .message("信息1")
                .picture("www.tu1.link")
                .posX(1)
                .posY(2)
                .departmentId(1)
                .build();
        Equipment equipment2 = Equipment.builder()
                .id(1)
                .name("设备2")
                .message("信息2")
                .picture("www.tu2.link")
                .posX(3)
                .posY(5)
                .departmentId(1)
                .build();
        Equipment equipment3 = Equipment.builder()
                .id(2)
                .name("设备3")
                .message("信息3")
                .picture("www.tu3.link")
                .posX(7)
                .posY(8)
                .departmentId(2)
                .build();
        Medicine medicine1 = Medicine.builder()
                .id(0)
                .name("药品1")
                .message("药品信息1")
                .picture("www.tu4.link")
                .build();
        Department department1 = Department.builder()
                .id(1)
                .name("部门1")
                .message("部门信息1")
                .principal(Role.builder()
                        .id(1)
                        .name("角色1")
                        .build())
                .picture("www.pic1.link")
                .isPharmacy(false)
                .pos1X(0).pos1Y(1).pos2X(3).pos2Y(4)
                .build();
        Department department2 = Department.builder()
                .id(2)
                .name("部门2")
                .message("部门信息2")
                .principal(Role.builder()
                        .id(1)
                        .name("角色1")
                        .build())
                .picture("www.pic2.link")
                .isPharmacy(true)
                .pos1X(0).pos1Y(1).pos2X(8).pos2Y(4)
                .build();
        when(departmentDao.findAll()).thenReturn(new ArrayList<>(Arrays.asList(department1, department2)));
        when(equipmentDao.findEquipmentByDepartmentId(1)).thenReturn(new ArrayList<>(Arrays.asList(equipment1, equipment2)));
        when(equipmentDao.findEquipmentByDepartmentId(2)).thenReturn(new ArrayList<>(Arrays.asList(equipment3)));
        when(medicineDao.findAll()).thenReturn(new ArrayList<>(Arrays.asList(medicine1)));
        List<DepartmentResponse> responses = guideService.getDepartments();
        assertAll(
                () -> assertEquals(2, responses.size()),
                () -> assertEquals(1, responses.get(0).getId()),
                () -> assertEquals("部门1", responses.get(0).getName()),
                () -> assertEquals("部门信息1", responses.get(0).getMessage()),
                () -> assertEquals("www.pic1.link", responses.get(0).getPicture()),
                () -> assertEquals("角色1", responses.get(0).getRoleName()),
                () -> assertEquals(0, responses.get(0).getPosition()[0]),
                () -> assertEquals(1, responses.get(0).getPosition()[1]),
                () -> assertEquals(3, responses.get(0).getPosition()[2]),
                () -> assertEquals(4, responses.get(0).getPosition()[3]),
                () -> assertEquals(2, responses.get(0).getEquipments().size()),
                () -> assertEquals("设备1", responses.get(0).getEquipments().get(0).getName()),
                () -> assertEquals("信息1", responses.get(0).getEquipments().get(0).getMessage()),
                () -> assertEquals("www.tu1.link", responses.get(0).getEquipments().get(0).getPicture()),
                () -> assertEquals(1, responses.get(0).getEquipments().get(0).getPosition()[0]),
                () -> assertEquals(2, responses.get(0).getEquipments().get(0).getPosition()[1]),
                () -> assertEquals("设备2", responses.get(0).getEquipments().get(1).getName()),
                () -> assertEquals("信息2", responses.get(0).getEquipments().get(1).getMessage()),
                () -> assertEquals("www.tu2.link", responses.get(0).getEquipments().get(1).getPicture()),
                () -> assertEquals(3, responses.get(0).getEquipments().get(1).getPosition()[0]),
                () -> assertEquals(5, responses.get(0).getEquipments().get(1).getPosition()[1]),


                () -> assertEquals(2, responses.get(1).getId()),
                () -> assertEquals("部门2", responses.get(1).getName()),
                () -> assertEquals("部门信息2", responses.get(1).getMessage()),
                () -> assertEquals("www.pic2.link", responses.get(1).getPicture()),
                () -> assertEquals("角色1", responses.get(1).getRoleName()),
                () -> assertEquals(0, responses.get(1).getPosition()[0]),
                () -> assertEquals(1, responses.get(1).getPosition()[1]),
                () -> assertEquals(8, responses.get(1).getPosition()[2]),
                () -> assertEquals(4, responses.get(1).getPosition()[3]),
                () -> assertEquals(1, responses.get(1).getEquipments().size()),
                () -> assertEquals("设备3", responses.get(1).getEquipments().get(0).getName()),
                () -> assertEquals("信息3", responses.get(1).getEquipments().get(0).getMessage()),
                () -> assertEquals("www.tu3.link", responses.get(1).getEquipments().get(0).getPicture()),
                () -> assertEquals(7, responses.get(1).getEquipments().get(0).getPosition()[0]),
                () -> assertEquals(8, responses.get(1).getEquipments().get(0).getPosition()[1]),
                () -> assertEquals("药品1", responses.get(1).getMedicines().get(0).getName()),
                () -> assertEquals("药品信息1", responses.get(1).getMedicines().get(0).getMessage()),
                () -> assertEquals("www.tu4.link", responses.get(1).getMedicines().get(0).getPicture())
        );
    }
}
