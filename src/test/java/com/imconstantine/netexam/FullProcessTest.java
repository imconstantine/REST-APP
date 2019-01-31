package com.imconstantine.netexam;

import com.imconstantine.netexam.dto.request.ExamSetStateDtoRequest;
import com.imconstantine.netexam.dto.request.QuestionDtoRequest;
import com.imconstantine.netexam.dto.response.ExamDtoResponse;
import com.imconstantine.netexam.dto.response.ExamForSolutionDtoResponse;
import com.imconstantine.netexam.dto.response.ExamsAndTeachersDtoResponse;
import com.imconstantine.netexam.dto.response.TeacherDtoResponse;
import com.imconstantine.netexam.utils.ErrorCode;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;

/**
 * Test executed with follows settings
 * rest_http_port=8080
 * max_name_length=20
 * min_password_length=4
 * min_answers=3
 * min_questions_count_per_exam=2
 * min_time=1
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = NetExamServer.class, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class FullProcessTest extends ClientTestBase {

    @Test
    public void testFullProcess() {
        TeacherDtoResponse teacherDtoResponse = createAndRegisterAndLoginTeacher("Алексей", "Петров", null, "Алгебра",
                "Доцент", "login1", "qwerty123", ErrorCode.SUCCESS);
        String teacher1Token = getToken();
        ExamDtoResponse examDtoResponse1 = createExam(teacher1Token, "Алгебра", 1, ErrorCode.SUCCESS);
        List<QuestionDtoRequest> questions1 = new ArrayList<>();
        questions1.add(new QuestionDtoRequest("Корень из 4", 1, Arrays.asList("2", "8", "1", "16"), 1));
        questions1.add(new QuestionDtoRequest("Корень из 16", 2, Arrays.asList("2", "4", "8", "32"), 2));
        questions1.add(new QuestionDtoRequest("Корень из 25", 3, Arrays.asList("0", "1", "5", "125"), 3));
        questions1.add(new QuestionDtoRequest("Корень из 36", 4, Arrays.asList("12", "169", "1", "6"), 4));
        addQuestions(teacher1Token, examDtoResponse1.getId(), questions1, ErrorCode.SUCCESS);
        ExamSetStateDtoRequest examSetStateDtoRequest1 = new ExamSetStateDtoRequest(4, 10, true);
        makeReady(teacher1Token, examDtoResponse1.getId(), examSetStateDtoRequest1, ErrorCode.SUCCESS);

        ExamDtoResponse examDtoResponse2 = createExam(teacher1Token, "Физика", 1, ErrorCode.SUCCESS);
        List<QuestionDtoRequest> questions2 = new ArrayList<>();
        questions2.add(new QuestionDtoRequest("Третий закон Ньютона", 1, Arrays.asList("F = -F", "F != -F", "F = m*a"), 1));
        questions2.add(new QuestionDtoRequest("Ускорение свободного падения", 2, Arrays.asList("9", "9.8", "10"), 2));
        questions2.add(new QuestionDtoRequest("Обозначение момента силы", 3, Arrays.asList("N", "F", "M", "H"), 3));
        addQuestions(teacher1Token, examDtoResponse2.getId(), questions2, ErrorCode.SUCCESS);
        ExamSetStateDtoRequest examSetStateDtoRequest2 = new ExamSetStateDtoRequest(3, 11, true);
        makeReady(teacher1Token, examDtoResponse2.getId(), examSetStateDtoRequest2, ErrorCode.SUCCESS);

        createAndRegisterAndLoginStudent("Дмитрий", "Макаров", null, "МПБ-402", 1, "login3", "qwerty123", ErrorCode.SUCCESS);
        String student1Token = getToken();
        ExamForSolutionDtoResponse examForSolutionDtoResponse1 = getStudentExamQuestions(student1Token, examDtoResponse2.getId(),
                questions2, examSetStateDtoRequest2, ErrorCode.SUCCESS);
        getCurrentStudentExams(student1Token, new ExamSetStateDtoRequest[]{examSetStateDtoRequest1}, ErrorCode.SUCCESS);
        List<Integer> solutuion = Arrays.asList(examForSolutionDtoResponse1.getQuestions().get(0).getCorrect(),
                examForSolutionDtoResponse1.getQuestions().get(1).getCorrect(),
                examForSolutionDtoResponse1.getQuestions().get(2).getCorrect());
        sendSolution(teacherDtoResponse, getToken(), examDtoResponse2.getId(), questions2, examSetStateDtoRequest2,
                solutuion, ErrorCode.SUCCESS);
        getCurrentStudentExams(student1Token, new ExamSetStateDtoRequest[0], ErrorCode.SUCCESS);
        getExamWithCorrectResults(teacherDtoResponse, student1Token, examDtoResponse2, questions2, examSetStateDtoRequest2,
                examForSolutionDtoResponse1, ErrorCode.SUCCESS);

        createAndRegisterAndLoginStudent("Максим", "Шевчюк", null, "МПБ-402", 1, "login4", "qwerty123", ErrorCode.SUCCESS);
        String student2Token = getToken();
        ExamForSolutionDtoResponse examForSolutionDtoResponse2 = getStudentExamQuestions(student2Token, examDtoResponse2.getId(),
                questions2, examSetStateDtoRequest2, ErrorCode.SUCCESS);
        getRemainingStudentExams(student2Token, new ExamSetStateDtoRequest[]{examSetStateDtoRequest1}, ErrorCode.SUCCESS);
        ExamsAndTeachersDtoResponse examList = getCurrentStudentExams(student2Token, new ExamSetStateDtoRequest[]{examSetStateDtoRequest2}, ErrorCode.SUCCESS);
        assertEquals(examList.getList().get(0).getTimeInMinutes(), examForSolutionDtoResponse2.getExam().getTimeInMinutes());
        List<Integer> solutuion2 = Arrays.asList(examForSolutionDtoResponse2.getQuestions().get(0).getCorrect(),
                null,
                examForSolutionDtoResponse2.getQuestions().get(2).getCorrect());
        sendSolution(teacherDtoResponse, student2Token, examDtoResponse2.getId(), questions2, examSetStateDtoRequest2,
                solutuion2, ErrorCode.SUCCESS);
        getExamResultsWithNoAnswer(student2Token, questions2, examSetStateDtoRequest2, examForSolutionDtoResponse2, ErrorCode.SUCCESS);

        createAndRegisterAndLoginStudent("Андрей", "Шевчюк", null, "МПБ-502", 1, "login5", "qwerty123", ErrorCode.SUCCESS);
        String student3Token = getToken();
        getEmptyExamResults(student3Token, ErrorCode.SUCCESS);

        createAndRegisterAndLoginStudent("Павел", "Иванов", null, "МПБ-505", 1, "login6", "qwerty123", ErrorCode.SUCCESS);
        String student4Token = getToken();
        getStudentExamQuestions(student4Token, examDtoResponse2.getId(),questions2, examSetStateDtoRequest2, ErrorCode.SUCCESS);
        getExamResultsBeforeTimeOver(student4Token, examDtoResponse2, questions2, examSetStateDtoRequest2, ErrorCode.SUCCESS);

        createAndRegisterAndLoginStudent("Антон", "Зайцев", null, "МПБ-603", 1, "login7", "qwerty123", ErrorCode.SUCCESS);
        getExamStudents(teacher1Token, examDtoResponse2.getId(), questions2, examSetStateDtoRequest2, ErrorCode.SUCCESS);
    }

    @Test
    public void testSendSolutionWhenTimeIsOver() throws InterruptedException {
        TeacherDtoResponse teacherDtoResponse = createAndRegisterAndLoginTeacher("Алексей", "Петров", null, "Алгебра",
                "Доцент", "login1", "qwerty123", ErrorCode.SUCCESS);
        String teacher1Token = getToken();
        ExamDtoResponse examDtoResponse2 = createExam(teacher1Token, "Физика", 1, ErrorCode.SUCCESS);
        List<QuestionDtoRequest> questions2 = new ArrayList<>();
        questions2.add(new QuestionDtoRequest("Третий закон Ньютона", 1, Arrays.asList("F = -F", "F != -F", "F = m*a"), 1));
        questions2.add(new QuestionDtoRequest("Ускорение свободного падения", 2, Arrays.asList("9", "9.8", "10"), 2));
        questions2.add(new QuestionDtoRequest("Обозначение момента силы", 3, Arrays.asList("N", "F", "M", "H"), 3));
        addQuestions(teacher1Token, examDtoResponse2.getId(), questions2, ErrorCode.SUCCESS);
        ExamSetStateDtoRequest examSetStateDtoRequest2 = new ExamSetStateDtoRequest(3, 1, true);
        makeReady(teacher1Token, examDtoResponse2.getId(), examSetStateDtoRequest2, ErrorCode.SUCCESS);

        createAndRegisterAndLoginStudent("Дмитрий", "Макаров", null, "МПБ-402", 1, "login3", "qwerty123", ErrorCode.SUCCESS);
        String student1Token = getToken();
        ExamForSolutionDtoResponse examForSolutionDtoResponse1 = getStudentExamQuestions(student1Token, examDtoResponse2.getId(),
                questions2, examSetStateDtoRequest2, ErrorCode.SUCCESS);
        getStudentExamQuestions(student1Token, examDtoResponse2.getId(),
                questions2, examSetStateDtoRequest2, ErrorCode.SUCCESS);
        Thread.sleep(TimeUnit.MINUTES.toMillis(2));
        List<Integer> solutuion = Arrays.asList(examForSolutionDtoResponse1.getQuestions().get(0).getCorrect(),
                examForSolutionDtoResponse1.getQuestions().get(1).getCorrect(),
                examForSolutionDtoResponse1.getQuestions().get(2).getCorrect());
        sendSolution(teacherDtoResponse, getToken(), examDtoResponse2.getId(), questions2, examSetStateDtoRequest2,
                solutuion, ErrorCode.EXAM_TIME_IS_UP);

        getExamResultsForTimeOver(student1Token, examDtoResponse2, questions2, examSetStateDtoRequest2, examForSolutionDtoResponse1, ErrorCode.SUCCESS);
        getExamStudentsForTimeOver(teacher1Token, examDtoResponse2.getId(), questions2, examSetStateDtoRequest2, ErrorCode.SUCCESS);
    }
}
