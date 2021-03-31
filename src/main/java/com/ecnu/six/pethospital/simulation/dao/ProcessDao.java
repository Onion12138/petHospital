package com.ecnu.six.pethospital.simulation.dao;

import com.ecnu.six.pethospital.simulation.domain.Process;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author YueChen
 * @version 1.0
 * @date 2021/3/29 10:40
 */
@Mapper
@Repository
public interface ProcessDao {
    @Select("SELECT * FROM process WHERE id = #{roleId}")
    List<Process> findProcessByRoleId(@Param("roleId") int roleId);
}
