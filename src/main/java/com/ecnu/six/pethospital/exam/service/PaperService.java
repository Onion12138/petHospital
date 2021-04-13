package com.ecnu.six.pethospital.exam.service;

import com.ecnu.six.pethospital.exam.dto.PaperRequest;
import com.ecnu.six.pethospital.exam.vo.PaperResponse;

import java.util.List;

/**
 * @author HavenTong
 * @date 2021/3/25 下午3:47
 */
public interface PaperService {

    boolean addPaper(PaperRequest paperRequest);

    boolean updatePaper(PaperRequest paperRequest);

    boolean deletePaper(PaperRequest paperRequest);

    PaperResponse findPaperById(PaperRequest paperRequest);

    List<PaperResponse> findAll();
}
