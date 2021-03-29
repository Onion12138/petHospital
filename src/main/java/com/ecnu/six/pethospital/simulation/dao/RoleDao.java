package com.ecnu.six.pethospital.simulation.dao;

import com.ecnu.six.pethospital.simulation.domain.Role;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author YueChen
 * @version 1.0
 * @date 2021/3/29 10:24
 */

@Mapper
@Repository
public interface RoleDao {
    @Select("SELECT * FORM role")
    List<Role> findAll();
}
