package com.ecnu.six.pethospital.disease.dao;

import com.ecnu.six.pethospital.disease.domain.Disease;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author onion
 * @date 2021/3/22 -8:09 上午
 */
public interface DiseaseDao extends JpaRepository<Disease, Long> {
}
