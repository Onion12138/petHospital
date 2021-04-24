package com.ecnu.six.pethospital.disease.dao;

import com.ecnu.six.pethospital.disease.domain.Caze;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author onion
 * @date 2021/3/22 -9:40 上午
 */
public interface CaseDao extends JpaRepository<Caze, Long> {
    Page<Caze> findAllByName(String name, Pageable pageable);
    Page<Caze> findAllByNameLike(String name, Pageable pageable);
}
