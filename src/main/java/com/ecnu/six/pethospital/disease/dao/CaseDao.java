package com.ecnu.six.pethospital.disease.dao;

import com.ecnu.six.pethospital.disease.domain.Caze;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author onion
 * @date 2021/3/22 -9:40 上午
 */
public interface CaseDao extends JpaRepository<Caze, Long> {
    List<Caze> findAllByName(String name);
}
