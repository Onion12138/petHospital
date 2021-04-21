package com.ecnu.six.pethospital.exam.service;

import com.ecnu.six.pethospital.exam.domain.ExamScore;
import com.ecnu.six.pethospital.exam.dto.ExamRequest;
import com.ecnu.six.pethospital.exam.dto.ExamScoreRequest;
import com.ecnu.six.pethospital.exam.vo.ExamResponse;

import java.util.List;

/**
 * @author HavenTong
 * @date 2021/3/25 下午9:10
 */
public interface ExamService {

    boolean addExam(ExamRequest examRequest);

    List<ExamResponse> findAvailableExams(ExamRequest examRequest);

    boolean saveScore(ExamScoreRequest examScoreRequest);
}
