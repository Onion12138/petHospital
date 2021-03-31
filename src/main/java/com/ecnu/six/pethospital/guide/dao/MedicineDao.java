package com.ecnu.six.pethospital.guide.dao;

import com.ecnu.six.pethospital.guide.domain.Medicine;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author YueChen
 * @version 1.0
 * @date 2021/3/29 9:30
 */
@Mapper
@Repository
public interface MedicineDao {

    @Select("SELECT * FROM medicine")
    List<Medicine> findAll();
}
