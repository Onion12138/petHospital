package com.ecnu.six.pethospital.guide.dao;

import com.ecnu.six.pethospital.guide.domain.Department;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * @author YueChen
 * @version 1.0
 * @date 2021/3/29 9:04
 */
@Mapper
@Repository
public interface DepartmentDao {
    @Select("SELECT * FROM department")
    @Results({
            @Result(property = "principal", column = "principal_id", one = @One(select = "com.ecnu.six.pethospital.exam.dao.RoleDao.findRoleById"))
    })
    List<Department> findAll();
}
