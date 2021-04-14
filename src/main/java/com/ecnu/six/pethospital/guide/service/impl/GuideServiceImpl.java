package com.ecnu.six.pethospital.guide.service.impl;

import com.ecnu.six.pethospital.guide.dao.DepartmentDao;
import com.ecnu.six.pethospital.guide.dao.EquipmentDao;
import com.ecnu.six.pethospital.guide.dao.MedicineDao;
import com.ecnu.six.pethospital.guide.domain.Department;
import com.ecnu.six.pethospital.guide.domain.Equipment;
import com.ecnu.six.pethospital.guide.domain.Medicine;
import com.ecnu.six.pethospital.guide.service.GuideService;
import com.ecnu.six.pethospital.guide.vo.DepartmentResponse;
import com.ecnu.six.pethospital.guide.vo.EquipmentResponse;
import com.ecnu.six.pethospital.guide.vo.MedicineResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

/**
 * @author YueChen
 * @version 1.0
 * @date 2021/3/29 8:57
 */
@Service
@Slf4j
public class GuideServiceImpl implements GuideService {
    @Autowired
    private DepartmentDao departmentDao;

    @Autowired
    private EquipmentDao equipmentDao;

    @Autowired
    private MedicineDao medicineDao;

    @Override
    public List<DepartmentResponse> getDepartments() {
        List<Department> departments = departmentDao.findAll();
        List<DepartmentResponse> departmentResponses = new LinkedList<>();
        for(Department department : departments) {
            List<Equipment> equipments = equipmentDao.findEquipmentByDepartmentId(department.getId());
            List<EquipmentResponse> equipmentResponses = new LinkedList<>();
            Integer[] departmentPosition = new Integer[4];
            departmentPosition[0] = department.getPos1X();
            departmentPosition[1] = department.getPos1Y();
            departmentPosition[2] = department.getPos2X();
            departmentPosition[3] = department.getPos2Y();
            for(Equipment equipment : equipments) {
                Integer[] pos = new Integer[2];
                pos[0] = equipment.getPosX();
                pos[1] = equipment.getPosY();
                equipmentResponses.add(EquipmentResponse.builder()
                        .name(equipment.getName())
                        .message(equipment.getMessage())
                        .position(pos)
                        .picture(equipment.getPicture())
                        .build());
            }
            if(department.getIsPharmacy()) {
                List<Medicine> medicines = medicineDao.findAll();
                List<MedicineResponse> medicineResponses = new LinkedList<>();
                for(Medicine medicine : medicines) {
                    medicineResponses.add(MedicineResponse.builder()
                            .name(medicine.getName())
                            .message(medicine.getMessage())
                            .picture(medicine.getPicture())
                            .build());
                }
                departmentResponses.add(DepartmentResponse.builder()
                        .id(department.getId())
                        .name(department.getName())
                        .message(department.getMessage())
                        .roleName(department.getPrincipal().getName())
                        .picture(department.getPicture())
                        .position(departmentPosition)
                        .equipments(equipmentResponses)
                        .medicines(medicineResponses)
                        .build());
            }
            else {
                departmentResponses.add(DepartmentResponse.builder()
                        .id(department.getId())
                        .name(department.getName())
                        .message(department.getMessage())
                        .roleName(department.getPrincipal().getName())
                        .picture(department.getPicture())
                        .position(departmentPosition)
                        .equipments(equipmentResponses)
                        .build());
            }
        }
        return departmentResponses;
    }
}
