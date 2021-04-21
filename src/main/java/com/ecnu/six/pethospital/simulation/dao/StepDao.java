package com.ecnu.six.pethospital.simulation.dao;

import com.ecnu.six.pethospital.simulation.domain.Step;
import org.apache.ibatis.annotations.*;
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

    @Insert("INSERT INTO step(`name`, `message`, `picture`, `video`, `process_id`, `p_order`)" +
            "VALUES (#{name}, #{message}, #{picture}, #{video}, #{processId}, #{pOrder})")
    void addStep(Step step);

    @Update("UPDATE step SET `name` = #{name}, `message` = #{message}, `picture` = #{picture}, `video` = #{video} " +
            "WHERE id = #{id}")
    void updateStep(Step step);

}
