package com.ecnu.six.pethospital.guide.dao;

import com.ecnu.six.pethospital.guide.domain.Equipment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author YueChen
 * @version 1.0
 * @date 2021/3/29 9:22
 */
@Mapper
@Repository
public interface EquipmentDao {
    @Select("SELECT * FROM equipment WHERE department_id = #{departmentId}")
    List<Equipment> findEquipmentByDepartmentId(@Param("departmentId") int departmentId);
}
