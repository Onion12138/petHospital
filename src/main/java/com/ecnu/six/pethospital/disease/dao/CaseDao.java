package com.ecnu.six.pethospital.disease.dao;

import com.ecnu.six.pethospital.disease.domain.Case;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author onion
 * @date 2021/3/22 -9:40 上午
 */
public interface CaseDao extends JpaRepository<Case, Long> {
}
