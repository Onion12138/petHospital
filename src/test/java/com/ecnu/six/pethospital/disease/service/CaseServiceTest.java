package com.ecnu.six.pethospital.disease.service;

import com.ecnu.six.pethospital.disease.dao.CaseDao;
import com.ecnu.six.pethospital.disease.service.impl.CaseServiceImpl;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author onion
 * @date 2021/4/14 -1:40 下午
 */
@SpringBootTest
public class CaseServiceTest {
    @Mock
    private CaseDao caseDao;

    @InjectMocks
    private CaseServiceImpl caseService;

//    @Test
//    @DisplayName("添加正确的考试")
//    void shouldAddCorrectExam() {
//        ExamRequest examRequest = ExamRequest.builder()
//                .paperId(1)
//                .examName("期末考试")
//                .creatorId(1)
//                .startTime("2021-05-12 10:00:00").build();
//        ArgumentCaptor<Exam> examCaptor = ArgumentCaptor.forClass(Exam.class);
//        caseService.addExam(examRequest);
//        verify(examDao, times(1))
//                .addExam(examCaptor.capture());
//        Exam exam = examCaptor.getValue();
//        assertAll(
//                () -> assertEquals(1, exam.getPaperId()),
//                () -> assertEquals(1, exam.getCreatorId()),
//                () -> assertEquals("期末考试", exam.getExamName()),
//                () -> assertEquals(LocalDateTime.of(2021, 5, 12, 10, 0, 0), exam.getStartTime())
//        );
//    }
}
