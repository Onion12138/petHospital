package com.ecnu.six.pethospital.simulation.dao;

import com.ecnu.six.pethospital.simulation.domain.Step;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author YueChen
 * @version 1.0
 * @date 2021/3/29 10:54
 */
@Mapper
@Repository
public interface StepDao {
    @Select("Select * FROM step WHERE process_id = #{processId} ORDER BY p_order")
    List<Step> findStepsByProcessId(@Param("processId") int processId);
}
