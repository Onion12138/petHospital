package com.ecnu.six.pethospital.simulation.dao;

import com.ecnu.six.pethospital.simulation.domain.Process;
import org.apache.ibatis.annotations.*;
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
    @Select("SELECT * FROM process WHERE role_id = #{roleId}")
    List<Process> findProcessByRoleId(@Param("roleId") int roleId);

    @Select("SELECT * FROM process")
    List<Process> findAllProcess();

    @Delete("DELETE FROM process WHERE id = #{processId}")
    void deleteProcessById(@Param("processId") int processId);

    @Insert("INSERT INTO process(`name`, `role_id`)" +
            "VALUES (#{name}, #{roleId})")
    int addProcess(Process process);

    @Update("UPDATE process SET `name` = #{name}, `role_id` = #{roleId} " +
            "WHERE id = #{id}")
    void updateProcess(Process process);
}
