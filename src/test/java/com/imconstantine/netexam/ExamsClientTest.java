package com.imconstantine.netexam;

import com.imconstantine.netexam.dto.request.ExamSetStateDtoRequest;
import com.imconstantine.netexam.dto.request.QuestionDtoRequest;
import com.imconstantine.netexam.dto.response.*;
import com.imconstantine.netexam.utils.ErrorCode;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

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
public class ExamsClientTest extends ClientTestBase {

    @Test
    public void testCreateExam() {
        createAndRegisterAndLoginTeacher("Алексей", "Петров", null, "Алгебра", "Доцент", "log1i122na", "qwerty123", ErrorCode.SUCCESS);
        createExam(getToken(), "Математический анализ", 1, ErrorCode.SUCCESS);
    }

    @Test
    public void testCreateExamAndCopy() {
        createAndRegisterAndLoginTeacher("Алексей", "Петров", null, "Алгебра", "Доцент", "log1i122na", "qwerty123", ErrorCode.SUCCESS);
        ExamDtoResponse examDtoResponse = createExam(getToken(), "Математический анализ", 1, ErrorCode.SUCCESS);
        copyExam(getToken(), "Математический анализ", 2, examDtoResponse.getId(), ErrorCode.SUCCESS);
        copyExam(getToken(), "Математический анализ", 3, examDtoResponse.getId(), ErrorCode.SUCCESS);
    }

    @Test
    public void testCreateExamAndCopyWithSameParameters() {
        createAndRegisterAndLoginTeacher("Алексей", "Петров", null, "Алгебра", "Доцент", "log1i122na", "qwerty123", ErrorCode.SUCCESS);
        ExamDtoResponse examDtoResponse = createExam(getToken(), "Математический анализ", 1, ErrorCode.SUCCESS);
        copyExam(getToken(), "Математический анализ", 1, examDtoResponse.getId(), ErrorCode.EXAM_ALREADY_EXISTS);
    }

    @Test
    public void testCreateExamWithSameNameAndSemester() {
        createAndRegisterAndLoginTeacher("Алексей", "Петров", null, "Алгебра", "Доцент", "log1i122na", "qwerty123", ErrorCode.SUCCESS);
        ExamDtoResponse examDtoResponse = createExam(getToken(), "Математический анализ", 1, ErrorCode.SUCCESS);
        copyExam(getToken(), "Математический анализ", 1, examDtoResponse.getId(), ErrorCode.EXAM_ALREADY_EXISTS);
    }

    @Test
    public void testCreateExamWithSameSemesterAndAnotherName() {
        createAndRegisterAndLoginTeacher("Алексей", "Петров", null, "Алгебра", "Доцент", "log1i122na", "qwerty123", ErrorCode.SUCCESS);
        ExamDtoResponse examDtoResponse = createExam(getToken(), "Математический анализ", 1, ErrorCode.SUCCESS);
        copyExam(getToken(), "Математический анализ упрощ.", 1, examDtoResponse.getId(), ErrorCode.SUCCESS);
    }

    @Test
    public void testCreateExamAndCopyByAnotherTeacher() {
        createAndRegisterAndLoginTeacher("Алексей", "Петров", null, "Алгебра", "Доцент", "log1i122na", "qwerty123", ErrorCode.SUCCESS);
        ExamDtoResponse examDtoResponse = createExam(getToken(), "Математический анализ", 1, ErrorCode.SUCCESS);
        createAndRegisterAndLoginTeacher("Николай", "Некрасов", null, "Алгебра", "Доцент", "logina", "qwerty123", ErrorCode.SUCCESS);
        copyExam(getToken(), "Математический анализ упрощ.", 1, examDtoResponse.getId(), ErrorCode.ACCESS_IS_DENIED);
    }

    @Test
    public void testCreateSeveralExams() {
        createAndRegisterAndLoginTeacher("Алексей", "Петров", null, "Алгебра", "Доцент", "log1i122na", "qwerty123", ErrorCode.SUCCESS);
        for (int i = 0; i < 10; i++) {
            createExam(getToken(), "Математический анализ", i + 1, ErrorCode.SUCCESS);
        }
    }

    @Test
    public void testCreateExamWithNullName() {
        createAndRegisterAndLoginTeacher("Алексей", "Петров", null, "Алгебра", "Доцент", "log1i122na", "qwerty123", ErrorCode.SUCCESS);
        createExam(getToken(), null, 1, ErrorCode.EXAM_NAME_IS_NULL);
    }

    @Test
    public void testCreateExamWithWrongSemester() {
        createAndRegisterAndLoginTeacher("Алексей", "Петров", null, "Алгебра", "Доцент", "log1i122na", "qwerty123", ErrorCode.SUCCESS);
        createExam(getToken(), "Математический анализ", 0, ErrorCode.SEMESTER_BAD_VALUE);
        createExam(getToken(), "Математический анализ", 13, ErrorCode.SEMESTER_BAD_VALUE);
    }

    @Test
    public void testCreateExamByStudent() {
        createAndRegisterAndLoginStudent("Алексей", "Петров", null, "МПБ-505", 1, "log1i122na", "qwerty123", ErrorCode.SUCCESS);
        createExam(getToken(), "Математический анализ", 1, ErrorCode.USER_IS_NOT_TEACHER);
    }

    @Test
    public void testCreateExamByUnknownUser() {
        createExam("1234", "Математический анализ", 2, ErrorCode.TOKEN_DOES_NOT_EXIST);
    }

    @Test
    public void testCreateExamByNullUserToken() {
        createExam(null, "Математический анализ", 2, ErrorCode.TOKEN_DOES_NOT_EXIST);
    }

    @Test
    public void testCreateExamsInThreads() {
        int threadsCount = 3;
        ThreadTester[] threads = new ThreadTester[threadsCount];
        for (int i = 0; i < threadsCount; i++) {
            final int k = i;
            threads[i] = new ThreadTester(() -> {
                createAndRegisterAndLoginTeacher("Иван", "Иванов", "Иванович", "физика", "доцент", "login" + k, "password", ErrorCode.SUCCESS);
                for (int i1 = 0; i1 < 4; i1++) {
                    createExam(getToken(), "Физика", i1 * 3 + k + 1, ErrorCode.SUCCESS);
                }
            });
        }
        for (int i = 0; i < threadsCount; i++) {
            threads[i].start();
            try {
                threads[i].test();
            } catch (Exception e) {
                fail();
            }
        }
    }

    @Test
    public void testEditingExam() {
        createAndRegisterAndLoginTeacher("Алексей", "Петров", null, "Алгебра", "Доцент", "log1i122na", "qwerty123", ErrorCode.SUCCESS);
        ExamDtoResponse examDtoResponse1 = createExam(getToken(), "Математический анализ", 1, ErrorCode.SUCCESS);
        editExam(getToken(), "Алгбера", 2, examDtoResponse1.getId(), ErrorCode.SUCCESS);
    }

    @Test
    public void testEditingExamName() {
        createAndRegisterAndLoginTeacher("Алексей", "Петров", null, "Алгебра", "Доцент", "log1i122na", "qwerty123", ErrorCode.SUCCESS);
        ExamDtoResponse examDtoResponse1 = createExam(getToken(), "Математический анализ", 1, ErrorCode.SUCCESS);
        editExam(getToken(), null, 2, examDtoResponse1.getId(), ErrorCode.SUCCESS);
    }

    @Test
    public void testEditingExamSemester() {
        createAndRegisterAndLoginTeacher("Алексей", "Петров", null, "Алгебра", "Доцент", "log1i122na", "qwerty123", ErrorCode.SUCCESS);
        ExamDtoResponse examDtoResponse1 = createExam(getToken(), "Математический анализ", 1, ErrorCode.SUCCESS);
        editExam(getToken(), "Алгбера", null, examDtoResponse1.getId(), ErrorCode.SUCCESS);
    }

    @Test
    public void testEditingExamByAnotherTeacher() {
        createAndRegisterAndLoginTeacher("Алексей", "Петров", null, "Алгебра", "Доцент", "log1i122na", "qwerty123", ErrorCode.SUCCESS);
        ExamDtoResponse examDtoResponse1 = createExam(getToken(), "Математический анализ", 1, ErrorCode.SUCCESS);
        createAndRegisterAndLoginTeacher("Петр", "Иванов", null, "Алгебра", "Доцент", "log1naa", "qwerty123", ErrorCode.SUCCESS);
        editExam(getToken(), "Алгбера", 2, examDtoResponse1.getId(), ErrorCode.ACCESS_IS_DENIED);
    }

    @Test
    public void testAddQuestionsToExam() {
        createAndRegisterAndLoginTeacher("Алексей", "Петров", null, "Алгебра", "Доцент", "log1i122na", "qwerty123", ErrorCode.SUCCESS);
        ExamDtoResponse examDtoResponse = createExam(getToken(), "Математический анализ", 1, ErrorCode.SUCCESS);
        List<QuestionDtoRequest> questions = new ArrayList<>();
        questions.add(new QuestionDtoRequest("Корень из 4", 1, Arrays.asList("2", "8", "1"), 2));
        addQuestions(getToken(), examDtoResponse.getId(), questions, ErrorCode.SUCCESS);
    }

    @Test
    public void testAddQuestionsToExamWithoutNumber() {
        createAndRegisterAndLoginTeacher("Алексей", "Петров", null, "Алгебра", "Доцент", "log1i122na", "qwerty123", ErrorCode.SUCCESS);
        ExamDtoResponse examDtoResponse = createExam(getToken(), "Математический анализ", 1, ErrorCode.SUCCESS);
        List<QuestionDtoRequest> questions = new ArrayList<>();
        questions.add(new QuestionDtoRequest("Корень из 4", null, Arrays.asList("2", "8", "1"), 2));
        addQuestions(getToken(), examDtoResponse.getId(), questions, ErrorCode.SUCCESS);
    }

    @Test
    public void testMakeExamReadyWithAllQuestionZeroNumbers() {
        createAndRegisterAndLoginTeacher("Алексей", "Петров", null, "Алгебра", "Доцент", "log1i122na", "qwerty123", ErrorCode.SUCCESS);
        ExamDtoResponse examDtoResponse = createExam(getToken(), "Математический анализ", 1, ErrorCode.SUCCESS);
        List<QuestionDtoRequest> questions = new ArrayList<>();
        questions.add(new QuestionDtoRequest("Корень из 4", null, Arrays.asList("2", "8", "1"), 2));
        questions.add(new QuestionDtoRequest("Корень из 4", null, Arrays.asList("2", "8", "1"), 2));
        questions.add(new QuestionDtoRequest("Корень из 4", null, Arrays.asList("2", "8", "1"), 2));
        addQuestions(getToken(), examDtoResponse.getId(), questions, ErrorCode.SUCCESS);
        ExamSetStateDtoRequest examSetStateDtoRequest = new ExamSetStateDtoRequest(3, 10, true);
        makeReady(getToken(), examDtoResponse.getId(), examSetStateDtoRequest, ErrorCode.SUCCESS);
    }

    @Test
    public void testMakeExamReadyWithNotValidQuestionNumbers() {
        createAndRegisterAndLoginTeacher("Алексей", "Петров", null, "Алгебра", "Доцент", "log1i122na", "qwerty123", ErrorCode.SUCCESS);
        ExamDtoResponse examDtoResponse = createExam(getToken(), "Математический анализ", 1, ErrorCode.SUCCESS);
        List<QuestionDtoRequest> questions = new ArrayList<>();
        questions.add(new QuestionDtoRequest("Корень из 4", null, Arrays.asList("2", "8", "1"), 2));
        questions.add(new QuestionDtoRequest("Корень из 4", null, Arrays.asList("2", "8", "1"), 2));
        questions.add(new QuestionDtoRequest("Корень из 4", 2, Arrays.asList("2", "8", "1"), 2));
        addQuestions(getToken(), examDtoResponse.getId(), questions, ErrorCode.SUCCESS);
        ExamSetStateDtoRequest examSetStateDtoRequest = new ExamSetStateDtoRequest(3, 10, true);
        makeReady(getToken(), examDtoResponse.getId(), examSetStateDtoRequest, ErrorCode.QUESTIONS_NUMBERS_NOT_VALID);
    }

    @Test
    public void testAddSeveralQuestionsToExam() {
        createAndRegisterAndLoginTeacher("Алексей", "Петров", null, "Алгебра", "Доцент", "log1i122na", "qwerty123", ErrorCode.SUCCESS);
        ExamDtoResponse examDtoResponse = createExam(getToken(), "Математический анализ", 1, ErrorCode.SUCCESS);
        List<QuestionDtoRequest> questions = new ArrayList<>();
        questions.add(new QuestionDtoRequest("Корень из 4", 1, Arrays.asList("2", "8", "1", "16"), 1));
        questions.add(new QuestionDtoRequest("Корень из 16", 2, Arrays.asList("2", "4", "8", "32"), 2));
        questions.add(new QuestionDtoRequest("Корень из 25", 3, Arrays.asList("0", "1", "5", "125"), 3));
        questions.add(new QuestionDtoRequest("Корень из 36", 4, Arrays.asList("12", "169", "1", "6"), 4));
        addQuestions(getToken(), examDtoResponse.getId(), questions, ErrorCode.SUCCESS);
    }

    @Test
    public void testAddQuestionToUnexistentExam() {
        createAndRegisterAndLoginTeacher("Алексей", "Петров", null, "Алгебра", "Доцент", "log1i122na", "qwerty123", ErrorCode.SUCCESS);
        createExam(getToken(), "Математический анализ", 1, ErrorCode.SUCCESS);
        List<QuestionDtoRequest> questions = new ArrayList<>();
        questions.add(new QuestionDtoRequest("Корень из 4", null, Arrays.asList("2", "8", "1"), 1));
        addQuestions(getToken(), 214905, questions, ErrorCode.ACCESS_IS_DENIED);
    }

    @Test
    public void testAddQuestionToExamByStudent() {
        createAndRegisterAndLoginTeacher("Алексей", "Петров", null, "Алгебра", "Доцент", "log1i122na", "qwerty123", ErrorCode.SUCCESS);
        ExamDtoResponse examDtoResponse = createExam(getToken(), "Математический анализ", 1, ErrorCode.SUCCESS);
        createAndRegisterAndLoginStudent("Алексей", "Петров", null, "МПБ-505", 1, "studentloginna", "qwerty123", ErrorCode.SUCCESS);
        List<QuestionDtoRequest> questions = new ArrayList<>();
        questions.add(new QuestionDtoRequest("Корень из 4", null, Arrays.asList("2", "8", "1"), 2));
        addQuestions(getToken(), examDtoResponse.getId(), questions, ErrorCode.USER_IS_NOT_TEACHER);
    }

    @Test
    public void testGetUnreadyExamByTeacher() {
        createAndRegisterAndLoginTeacher("Алексей", "Петров", null, "Алгебра", "Доцент", "log1i122na", "qwerty123", ErrorCode.SUCCESS);
        ExamDtoResponse examDtoResponse = createExam(getToken(), "Математический анализ", 1, ErrorCode.SUCCESS);
        List<QuestionDtoRequest> questions = new ArrayList<>();
        questions.add(new QuestionDtoRequest("Корень из 4", 1, Arrays.asList("2", "8", "1", "16"), 1));
        questions.add(new QuestionDtoRequest("Корень из 16", 2, Arrays.asList("2", "4", "8", "32"), 2));
        questions.add(new QuestionDtoRequest("Корень из 25", 3, Arrays.asList("0", "1", "5", "125"), 3));
        questions.add(new QuestionDtoRequest("Корень из 36", 4, Arrays.asList("12", "169", "1", "6"), 4));
        addQuestions(getToken(), examDtoResponse.getId(), questions, ErrorCode.SUCCESS);
        getTeacherExamQuestions(getToken(), examDtoResponse.getId(), questions, ErrorCode.SUCCESS);
        getTeacherExamParam(getToken(), examDtoResponse.getId(), null, null, ErrorCode.SUCCESS);
    }

    @Test
    public void testGetReadyExamByTeacher() {
        createAndRegisterAndLoginTeacher("Алексей", "Петров", null, "Алгебра", "Доцент", "log1i122na", "qwerty123", ErrorCode.SUCCESS);
        ExamDtoResponse examDtoResponse = createExam(getToken(), "Математический анализ", 1, ErrorCode.SUCCESS);
        List<QuestionDtoRequest> questions = new ArrayList<>();
        questions.add(new QuestionDtoRequest("Корень из 4", 1, Arrays.asList("2", "8", "1", "16"), 1));
        questions.add(new QuestionDtoRequest("Корень из 16", 2, Arrays.asList("2", "4", "8", "32"), 2));
        questions.add(new QuestionDtoRequest("Корень из 25", 3, Arrays.asList("0", "1", "5", "125"), 3));
        questions.add(new QuestionDtoRequest("Корень из 36", 4, Arrays.asList("12", "169", "1", "6"), 4));
        addQuestions(getToken(), examDtoResponse.getId(), questions, ErrorCode.SUCCESS);
        ExamSetStateDtoRequest examSetStateDtoRequest = new ExamSetStateDtoRequest(2, 10, true);
        makeReady(getToken(), examDtoResponse.getId(), examSetStateDtoRequest, ErrorCode.SUCCESS);
        getTeacherExamQuestions(getToken(), examDtoResponse.getId(), questions, ErrorCode.SUCCESS);
        getTeacherExamParam(getToken(), examDtoResponse.getId(), 2, 10, ErrorCode.SUCCESS);
    }

    @Test
    public void testGetReadyExamByAnotherTeacher() {
        createAndRegisterAndLoginTeacher("Алексей", "Петров", null, "Алгебра", "Доцент", "log1i122na", "qwerty123", ErrorCode.SUCCESS);
        ExamDtoResponse examDtoResponse = createExam(getToken(), "Математический анализ", 1, ErrorCode.SUCCESS);
        List<QuestionDtoRequest> questions = new ArrayList<>();
        questions.add(new QuestionDtoRequest("Корень из 4", 1, Arrays.asList("2", "8", "1", "16"), 1));
        questions.add(new QuestionDtoRequest("Корень из 16", 2, Arrays.asList("2", "4", "8", "32"), 2));
        questions.add(new QuestionDtoRequest("Корень из 25", 3, Arrays.asList("0", "1", "5", "125"), 3));
        questions.add(new QuestionDtoRequest("Корень из 36", 4, Arrays.asList("12", "169", "1", "6"), 4));
        addQuestions(getToken(), examDtoResponse.getId(), questions, ErrorCode.SUCCESS);
        ExamSetStateDtoRequest examSetStateDtoRequest = new ExamSetStateDtoRequest(2, 10, true);
        makeReady(getToken(), examDtoResponse.getId(), examSetStateDtoRequest, ErrorCode.SUCCESS);
        getTeacherExamQuestions(getToken(), examDtoResponse.getId(), questions, ErrorCode.SUCCESS);
        getTeacherExamParam(getToken(), examDtoResponse.getId(), 2, 10, ErrorCode.SUCCESS);
        createAndRegisterAndLoginTeacher("Алексей", "Петров", null, "Алгебра", "Доцент", "log1i1223na", "qwerty1234", ErrorCode.SUCCESS);
        getTeacherExamQuestions(getToken(), examDtoResponse.getId(), questions, ErrorCode.ACCESS_IS_DENIED);
    }

    @Test
    public void testGetUnexistentExam() {
        createAndRegisterAndLoginTeacher("Алексей", "Петров", null, "Алгебра", "Доцент", "log1i1223na", "qwerty1234", ErrorCode.SUCCESS);
        getTeacherExamQuestions(getToken(), 327185, null, ErrorCode.ACCESS_IS_DENIED);
    }

    @Test
    public void testMakeExamReadyWithNullShowDetails() {
        createAndRegisterAndLoginTeacher("Алексей", "Петров", null, "Алгебра", "Доцент", "log1i122na", "qwerty123", ErrorCode.SUCCESS);
        ExamDtoResponse examDtoResponse = createExam(getToken(), "Математический анализ", 1, ErrorCode.SUCCESS);
        List<QuestionDtoRequest> questions = new ArrayList<>();
        questions.add(new QuestionDtoRequest("Корень из 4", 1, Arrays.asList("2", "8", "1", "16"), 1));
        questions.add(new QuestionDtoRequest("Корень из 16", 2, Arrays.asList("2", "4", "8", "32"), 2));
        questions.add(new QuestionDtoRequest("Корень из 25", 3, Arrays.asList("0", "1", "5", "125"), 3));
        questions.add(new QuestionDtoRequest("Корень из 36", 4, Arrays.asList("12", "169", "1", "6"), 4));
        addQuestions(getToken(), examDtoResponse.getId(), questions, ErrorCode.SUCCESS);
        ExamSetStateDtoRequest examSetStateDtoRequest = new ExamSetStateDtoRequest(2, 10, null);
        makeReady(getToken(), examDtoResponse.getId(), examSetStateDtoRequest, ErrorCode.EXAM_DETAILS_IS_NULL);
    }

    @Test
    public void testMakeExamReady() {
        createAndRegisterAndLoginTeacher("Алексей", "Петров", null, "Алгебра", "Доцент", "log1i122na", "qwerty123", ErrorCode.SUCCESS);
        ExamDtoResponse examDtoResponse = createExam(getToken(), "Математический анализ", 1, ErrorCode.SUCCESS);
        List<QuestionDtoRequest> questions = new ArrayList<>();
        questions.add(new QuestionDtoRequest("Корень из 4", 1, Arrays.asList("2", "8", "1", "16"), 1));
        questions.add(new QuestionDtoRequest("Корень из 16", 2, Arrays.asList("2", "4", "8", "32"), 2));
        questions.add(new QuestionDtoRequest("Корень из 25", 3, Arrays.asList("0", "1", "5", "125"), 3));
        questions.add(new QuestionDtoRequest("Корень из 36", 4, Arrays.asList("12", "169", "1", "6"), 4));
        addQuestions(getToken(), examDtoResponse.getId(), questions, ErrorCode.SUCCESS);
        ExamSetStateDtoRequest examSetStateDtoRequest = new ExamSetStateDtoRequest(2, 10, true);
        makeReady(getToken(), examDtoResponse.getId(), examSetStateDtoRequest, ErrorCode.SUCCESS);
    }

    @Test
    public void testMakeReadyExamReady() {
        createAndRegisterAndLoginTeacher("Алексей", "Петров", null, "Алгебра", "Доцент", "log1i122na", "qwerty123", ErrorCode.SUCCESS);
        ExamDtoResponse examDtoResponse = createExam(getToken(), "Математический анализ", 1, ErrorCode.SUCCESS);
        List<QuestionDtoRequest> questions = new ArrayList<>();
        questions.add(new QuestionDtoRequest("Корень из 4", 1, Arrays.asList("2", "8", "1", "16"), 1));
        questions.add(new QuestionDtoRequest("Корень из 16", 2, Arrays.asList("2", "4", "8", "32"), 2));
        questions.add(new QuestionDtoRequest("Корень из 25", 3, Arrays.asList("0", "1", "5", "125"), 3));
        questions.add(new QuestionDtoRequest("Корень из 36", 4, Arrays.asList("12", "169", "1", "6"), 4));
        addQuestions(getToken(), examDtoResponse.getId(), questions, ErrorCode.SUCCESS);
        ExamSetStateDtoRequest examSetStateDtoRequest = new ExamSetStateDtoRequest(2, 10, true);
        makeReady(getToken(), examDtoResponse.getId(), examSetStateDtoRequest, ErrorCode.SUCCESS);
        makeReady(getToken(), examDtoResponse.getId(), examSetStateDtoRequest, ErrorCode.EXAM_IS_READY);
    }

    @Test
    public void testAddQuestionsForReadyExam() {
        createAndRegisterAndLoginTeacher("Алексей", "Петров", null, "Алгебра", "Доцент", "log1i122na", "qwerty123", ErrorCode.SUCCESS);
        ExamDtoResponse examDtoResponse = createExam(getToken(), "Математический анализ", 1, ErrorCode.SUCCESS);
        List<QuestionDtoRequest> questions = new ArrayList<>();
        questions.add(new QuestionDtoRequest("Корень из 4", 1, Arrays.asList("2", "8", "1", "16"), 1));
        questions.add(new QuestionDtoRequest("Корень из 16", 2, Arrays.asList("2", "4", "8", "32"), 2));
        questions.add(new QuestionDtoRequest("Корень из 25", 3, Arrays.asList("0", "1", "5", "125"), 3));
        questions.add(new QuestionDtoRequest("Корень из 36", 4, Arrays.asList("12", "169", "1", "6"), 4));
        addQuestions(getToken(), examDtoResponse.getId(), questions, ErrorCode.SUCCESS);
        ExamSetStateDtoRequest examSetStateDtoRequest = new ExamSetStateDtoRequest(2, 10, true);
        makeReady(getToken(), examDtoResponse.getId(), examSetStateDtoRequest, ErrorCode.SUCCESS);
        addQuestions(getToken(), examDtoResponse.getId(), questions, ErrorCode.EXAM_IS_READY);
    }

    @Test
    public void testMakeExamReadyByStudent() {
        createAndRegisterAndLoginTeacher("Алексей", "Петров", null, "Алгебра", "Доцент", "log1i122na", "qwerty123", ErrorCode.SUCCESS);
        ExamDtoResponse examDtoResponse = createExam(getToken(), "Математический анализ", 1, ErrorCode.SUCCESS);
        List<QuestionDtoRequest> questions = new ArrayList<>();
        questions.add(new QuestionDtoRequest("Корень из 4", 1, Arrays.asList("2", "8", "1", "16"), 1));
        questions.add(new QuestionDtoRequest("Корень из 16", 2, Arrays.asList("2", "4", "8", "32"), 2));
        questions.add(new QuestionDtoRequest("Корень из 25", 3, Arrays.asList("0", "1", "5", "125"), 3));
        questions.add(new QuestionDtoRequest("Корень из 36", 4, Arrays.asList("12", "169", "1", "6"), 4));
        addQuestions(getToken(), examDtoResponse.getId(), questions, ErrorCode.SUCCESS);
        ExamSetStateDtoRequest examSetStateDtoRequest = new ExamSetStateDtoRequest(2, 3, true);
        createAndRegisterAndLoginStudent("Алексей", "Петров", null, "МПБ-505", 1, "log11i122na", "qwerty123", ErrorCode.SUCCESS);
        makeReady(getToken(), examDtoResponse.getId(), examSetStateDtoRequest, ErrorCode.USER_IS_NOT_TEACHER);
    }

    @Test
    public void testMakeExamReadyHavingNotEnoughQuestions() {
        createAndRegisterAndLoginTeacher("Алексей", "Петров", null, "Алгебра", "Доцент", "log1i122na", "qwerty123", ErrorCode.SUCCESS);
        ExamDtoResponse examDtoResponse = createExam(getToken(), "Математический анализ", 1, ErrorCode.SUCCESS);
        List<QuestionDtoRequest> questions = new ArrayList<>();
        questions.add(new QuestionDtoRequest("Корень из 4", 1, Arrays.asList("2", "8", "1", "16"), 1));
        questions.add(new QuestionDtoRequest("Корень из 16", 2, Arrays.asList("2", "4", "8", "32"), 2));
        questions.add(new QuestionDtoRequest("Корень из 25", 3, Arrays.asList("0", "1", "5", "125"), 3));
        questions.add(new QuestionDtoRequest("Корень из 36", 4, Arrays.asList("12", "169", "1", "6"), 4));
        addQuestions(getToken(), examDtoResponse.getId(), questions, ErrorCode.SUCCESS);
        ExamSetStateDtoRequest examSetStateDtoRequest = new ExamSetStateDtoRequest(10, 10, true);
        makeReady(getToken(), examDtoResponse.getId(), examSetStateDtoRequest, ErrorCode.EXAM_QUESTIONS_LESS_THAN_QUESTIONS_COUNT_EXAM);
    }

    @Test
    public void testMakeExamReadyHavingNoQuestions() {
        createAndRegisterAndLoginTeacher("Алексей", "Петров", null, "Алгебра", "Доцент", "log1i122na", "qwerty123", ErrorCode.SUCCESS);
        ExamDtoResponse examDtoResponse = createExam(getToken(), "Математический анализ", 1, ErrorCode.SUCCESS);
        ExamSetStateDtoRequest examSetStateDtoRequest = new ExamSetStateDtoRequest(10, 10, true);
        makeReady(getToken(), examDtoResponse.getId(), examSetStateDtoRequest, ErrorCode.QUESTIONS_IS_NOT_EXIST);
    }

    @Test
    public void testMakeExamReadyHavingNotEnoughAnswers() {
        createAndRegisterAndLoginTeacher("Алексей", "Петров", null, "Алгебра", "Доцент", "log1i122na", "qwerty123", ErrorCode.SUCCESS);
        ExamDtoResponse examDtoResponse = createExam(getToken(), "Математический анализ", 1, ErrorCode.SUCCESS);
        List<QuestionDtoRequest> questions = new ArrayList<>();
        questions.add(new QuestionDtoRequest("Корень из 4", 1, Arrays.asList("2", "8"), 1));
        questions.add(new QuestionDtoRequest("Корень из 16", 2, Arrays.asList("2", "4"), 2));
        questions.add(new QuestionDtoRequest("Корень из 25", 3, Arrays.asList("0", "1"), 0));
        questions.add(new QuestionDtoRequest("Корень из 36", 4, Arrays.asList("12", "169", "1", "6"), 5));
        addQuestions(getToken(), examDtoResponse.getId(), questions, ErrorCode.SUCCESS);
        ExamSetStateDtoRequest examSetStateDtoRequest = new ExamSetStateDtoRequest(4, 10, true);
        makeReady(getToken(), examDtoResponse.getId(), examSetStateDtoRequest,
                ErrorCode.QUESTION_HAS_LESS_ANSWERS,
                ErrorCode.QUESTION_HAS_LESS_ANSWERS,
                ErrorCode.QUESTION_HAS_LESS_ANSWERS,
                ErrorCode.QUESTION_INVALID_CORRECT,
                ErrorCode.QUESTION_INVALID_CORRECT
        );
    }

    @Test
    public void testMakeReadyExamHavingInvalidCorrectAnswerNumber() {
        createAndRegisterAndLoginTeacher("Алексей", "Петров", null, "Алгебра", "Доцент", "log1i122na", "qwerty123", ErrorCode.SUCCESS);
        ExamDtoResponse examDtoResponse = createExam(getToken(), "Математический анализ", 1, ErrorCode.SUCCESS);
        List<QuestionDtoRequest> questions = new ArrayList<>();
        questions.add(new QuestionDtoRequest("Корень из 4", 1, Arrays.asList("2", "8", "3"), 1));
        questions.add(new QuestionDtoRequest("Корень из 16", 2, Arrays.asList("2", "4", "1"), 4));
        questions.add(new QuestionDtoRequest("Корень из 25", 3, Arrays.asList("0", "1", "6"), -1));
        questions.add(new QuestionDtoRequest("Корень из 36", 4, Arrays.asList("12", "169", "1", "6"), 0));
        addQuestions(getToken(), examDtoResponse.getId(), questions, ErrorCode.SUCCESS);
        ExamSetStateDtoRequest examSetStateDtoRequest = new ExamSetStateDtoRequest(4, 10, true);
        makeReady(getToken(), examDtoResponse.getId(), examSetStateDtoRequest,
                ErrorCode.QUESTION_INVALID_CORRECT,
                ErrorCode.QUESTION_INVALID_CORRECT,
                ErrorCode.QUESTION_INVALID_CORRECT
        );
    }

    @Test
    public void testMakeReadyExamHavingNullCorrectAnswerNumbers() {
        createAndRegisterAndLoginTeacher("Алексей", "Петров", null, "Алгебра", "Доцент", "log1i122na", "qwerty123", ErrorCode.SUCCESS);
        ExamDtoResponse examDtoResponse = createExam(getToken(), "Математический анализ", 1, ErrorCode.SUCCESS);
        List<QuestionDtoRequest> questions = new ArrayList<>();
        questions.add(new QuestionDtoRequest("Корень из 4", 1, Arrays.asList("2", "8", "3"), null));
        questions.add(new QuestionDtoRequest("Корень из 16", 2, Arrays.asList("2", "4", "1"), 3));
        questions.add(new QuestionDtoRequest("Корень из 25", 3, Arrays.asList("0", "1", "6"), null));
        questions.add(new QuestionDtoRequest("Корень из 36", 4, Arrays.asList("12", "169", "1", "6"), 2));
        addQuestions(getToken(), examDtoResponse.getId(), questions, ErrorCode.SUCCESS);
        ExamSetStateDtoRequest examSetStateDtoRequest = new ExamSetStateDtoRequest(4, 10, true);
        makeReady(getToken(), examDtoResponse.getId(), examSetStateDtoRequest,
                ErrorCode.QUESTION_INVALID_CORRECT,
                ErrorCode.QUESTION_INVALID_CORRECT
        );
    }

    @Test
    public void testMakeReadyExamHavingNullAnswers() {
        createAndRegisterAndLoginTeacher("Алексей", "Петров", null, "Алгебра", "Доцент", "log1i122na", "qwerty123", ErrorCode.SUCCESS);
        ExamDtoResponse examDtoResponse = createExam(getToken(), "Математический анализ", 1, ErrorCode.SUCCESS);
        List<QuestionDtoRequest> questions = new ArrayList<>();
        questions.add(new QuestionDtoRequest("Корень из 4", 1, null, 1));
        questions.add(new QuestionDtoRequest("Корень из 16", 2, Arrays.asList("2", "4", "1"), 4));
        questions.add(new QuestionDtoRequest("Корень из 25", 3, Arrays.asList("0", "1", "6"), 2));
        questions.add(new QuestionDtoRequest("Корень из 36", 4, Arrays.asList("12", "169", "1", "6"), 1));
        addQuestions(getToken(), examDtoResponse.getId(), questions, ErrorCode.SUCCESS);
        ExamSetStateDtoRequest examSetStateDtoRequest = new ExamSetStateDtoRequest(4, 10, true);
        makeReady(getToken(), examDtoResponse.getId(), examSetStateDtoRequest,
                ErrorCode.QUESTION_HAS_LESS_ANSWERS,
                ErrorCode.QUESTION_INVALID_CORRECT,
                ErrorCode.QUESTION_INVALID_CORRECT);
    }

    @Test
    public void testMakeReadyExamHavingEmptyAnswers() {
        createAndRegisterAndLoginTeacher("Алексей", "Петров", null, "Алгебра", "Доцент", "log1i122na", "qwerty123", ErrorCode.SUCCESS);
        ExamDtoResponse examDtoResponse = createExam(getToken(), "Математический анализ", 1, ErrorCode.SUCCESS);
        List<QuestionDtoRequest> questions = new ArrayList<>();
        questions.add(new QuestionDtoRequest("Корень из 4", 1, new ArrayList<String>(), 1));
        questions.add(new QuestionDtoRequest("Корень из 16", 2, Arrays.asList("2", "4", "1"), 4));
        questions.add(new QuestionDtoRequest("Корень из 25", 3, Arrays.asList("0", "1", "6"), 2));
        questions.add(new QuestionDtoRequest("Корень из 36", 4, Arrays.asList("12", "169", "1", "6"), 1));
        addQuestions(getToken(), examDtoResponse.getId(), questions, ErrorCode.SUCCESS);
        ExamSetStateDtoRequest examSetStateDtoRequest = new ExamSetStateDtoRequest(4, 10, true);
        makeReady(getToken(), examDtoResponse.getId(), examSetStateDtoRequest,
                ErrorCode.QUESTION_HAS_LESS_ANSWERS,
                ErrorCode.QUESTION_INVALID_CORRECT,
                ErrorCode.QUESTION_INVALID_CORRECT);
    }

    @Test
    public void testMakeExamReadyWithWrongQuestionsCount() {
        createAndRegisterAndLoginTeacher("Алексей", "Петров", null, "Алгебра", "Доцент", "log1i122na", "qwerty123", ErrorCode.SUCCESS);
        ExamDtoResponse examDtoResponse = createExam(getToken(), "Математический анализ", 1, ErrorCode.SUCCESS);
        List<QuestionDtoRequest> questions = new ArrayList<>();
        questions.add(new QuestionDtoRequest("Корень из 4", 1, Arrays.asList("2", "4", "1"), 2));
        addQuestions(getToken(), examDtoResponse.getId(), questions, ErrorCode.SUCCESS);
        ExamSetStateDtoRequest examSetStateDtoRequest = new ExamSetStateDtoRequest(1, 10, true);
        makeReady(getToken(), examDtoResponse.getId(), examSetStateDtoRequest, ErrorCode.EXAM_QUESTIONS_COUNT_LESS_THAN_ALLOWED);
    }

    @Test
    public void testMakeExamReadyWithWrongTime() {
        createAndRegisterAndLoginTeacher("Алексей", "Петров", null, "Алгебра", "Доцент", "log1i122na", "qwerty123", ErrorCode.SUCCESS);
        ExamDtoResponse examDtoResponse = createExam(getToken(), "Математический анализ", 1, ErrorCode.SUCCESS);
        List<QuestionDtoRequest> questions = new ArrayList<>();
        questions.add(new QuestionDtoRequest("Корень из 4", 1, Arrays.asList("2", "8", "1", "16"), 1));
        questions.add(new QuestionDtoRequest("Корень из 16", 2, Arrays.asList("2", "4", "8", "32"), 2));
        questions.add(new QuestionDtoRequest("Корень из 25", 3, Arrays.asList("0", "1", "5", "125"), 3));
        questions.add(new QuestionDtoRequest("Корень из 36", 4, Arrays.asList("12", "169", "1", "6"), 4));
        addQuestions(getToken(), examDtoResponse.getId(), questions, ErrorCode.SUCCESS);
        ExamSetStateDtoRequest examSetStateDtoRequest = new ExamSetStateDtoRequest(4, 0, true);
        makeReady(getToken(), examDtoResponse.getId(), examSetStateDtoRequest, ErrorCode.EXAM_TIME_LESS_THAN_ALLOWED);
    }

    @Test
    public void testMakeExamReadyWithEmptyAnswerBody() {
        createAndRegisterAndLoginTeacher("Алексей", "Петров", null, "Алгебра", "Доцент", "log1i122na", "qwerty123", ErrorCode.SUCCESS);
        ExamDtoResponse examDtoResponse = createExam(getToken(), "Математический анализ", 1, ErrorCode.SUCCESS);
        List<QuestionDtoRequest> questions = new ArrayList<>();
        questions.add(new QuestionDtoRequest("Корень из 4", 1, Arrays.asList("", "8", "1", "16"), 1));
        questions.add(new QuestionDtoRequest("Корень из 16", 2, Arrays.asList("2", "4", "8", "32"), 2));
        questions.add(new QuestionDtoRequest("Корень из 25", 3, Arrays.asList("0", "1", "5", "125"), 3));
        questions.add(new QuestionDtoRequest("Корень из 36", 4, Arrays.asList("12", "169", "1", "6"), 4));
        addQuestions(getToken(), examDtoResponse.getId(), questions, ErrorCode.SUCCESS);
        ExamSetStateDtoRequest examSetStateDtoRequest = new ExamSetStateDtoRequest(4, 10, true);
        makeReady(getToken(), examDtoResponse.getId(), examSetStateDtoRequest, ErrorCode.ANSWER_IS_NULL);
    }

    @Test
    public void testMakeExamReadyWithSomeErrors() {
        createAndRegisterAndLoginTeacher("Алексей", "Петров", null, "Алгебра", "Доцент", "log1i122na", "qwerty123", ErrorCode.SUCCESS);
        ExamDtoResponse examDtoResponse = createExam(getToken(), "Математический анализ", 1, ErrorCode.SUCCESS);
        List<QuestionDtoRequest> questions = new ArrayList<>();
        questions.add(new QuestionDtoRequest("Корень из 4", 1, Arrays.asList("", "8", "1", "16"), 1));
        questions.add(new QuestionDtoRequest(null, 2, Arrays.asList("2", "", "8", "32"), 2));
        questions.add(new QuestionDtoRequest("Корень из 25", 3, Arrays.asList("0", "1", "5", "125"), 3));
        questions.add(new QuestionDtoRequest("Корень из 36", 4, Arrays.asList("12", "169", "1", "6"), 5));
        addQuestions(getToken(), examDtoResponse.getId(), questions, ErrorCode.SUCCESS);
        ExamSetStateDtoRequest examSetStateDtoRequest = new ExamSetStateDtoRequest(4, 0, true);
        makeReady(getToken(), examDtoResponse.getId(), examSetStateDtoRequest,
                ErrorCode.EXAM_HAS_EMPTY_QUESTION,
                ErrorCode.QUESTION_INVALID_CORRECT,
                ErrorCode.ANSWER_IS_NULL,
                ErrorCode.ANSWER_IS_NULL,
                ErrorCode.EXAM_TIME_LESS_THAN_ALLOWED);
    }

    @Test
    public void testMakeExamReadyByAnotherTeacher() {
        createAndRegisterAndLoginTeacher("Алексей", "Петров", null, "Алгебра", "Доцент", "log1i122na", "qwerty123", ErrorCode.SUCCESS);
        ExamDtoResponse examDtoResponse = createExam(getToken(), "Математический анализ", 1, ErrorCode.SUCCESS);
        List<QuestionDtoRequest> questions = new ArrayList<>();
        questions.add(new QuestionDtoRequest("Корень из 4", 1, Arrays.asList("4", "8", "1", "16"), 1));
        questions.add(new QuestionDtoRequest("Корень из 16", 2, Arrays.asList("2", "4", "8", "32"), 2));
        questions.add(new QuestionDtoRequest("Корень из 25", 3, Arrays.asList("0", "1", "5", "125"), 3));
        questions.add(new QuestionDtoRequest("Корень из 36", 4, Arrays.asList("12", "169", "1", "6"), 4));
        addQuestions(getToken(), examDtoResponse.getId(), questions, ErrorCode.SUCCESS);
        ExamSetStateDtoRequest examSetStateDtoRequest = new ExamSetStateDtoRequest(4, 10, true);
        createAndRegisterAndLoginTeacher("Алексей", "Петров", null, "Алгебра", "Доцент", "login", "qwerty123", ErrorCode.SUCCESS);
        makeReady(getToken(), examDtoResponse.getId(), examSetStateDtoRequest, ErrorCode.ACCESS_IS_DENIED);
    }

    @Test
    public void testMakeUnexistentExamReady() {
        ExamSetStateDtoRequest examSetStateDtoRequest = new ExamSetStateDtoRequest(4, 10, true);
        createAndRegisterAndLoginTeacher("Алексей", "Петров", null, "Алгебра", "Доцент", "login", "qwerty123", ErrorCode.SUCCESS);
        makeReady(getToken(), 123155, examSetStateDtoRequest, ErrorCode.ACCESS_IS_DENIED);
    }

    @Test
    public void testGetTeacherExams() {
        createAndRegisterAndLoginTeacher("Алексей", "Иванов", "Валентинович", "Алгебра", "Доцент", "login1", "qwerty123", ErrorCode.SUCCESS);
        String teacher1Token = getToken();
        createAndRegisterAndLoginTeacher("Михаил", "Петров", "Петрович", "Физика", "Доцент", "login2", "qwerty123", ErrorCode.SUCCESS);
        String teacher2Token = getToken();
        ExamDtoResponse examDtoResponse = createExam(teacher1Token, "Алгебра", 1, ErrorCode.SUCCESS);
        createExam(teacher1Token, "Алгебра", 2, ErrorCode.SUCCESS);
        createExam(teacher1Token, "Алгебра", 3, ErrorCode.SUCCESS);
        createExam(teacher2Token, "Физика", 2, ErrorCode.SUCCESS);
        createExam(teacher2Token, "Физика", 3, ErrorCode.SUCCESS);
        List<QuestionDtoRequest> questions = new ArrayList<>();
        questions.add(new QuestionDtoRequest("Корень из 4", 1, Arrays.asList("4", "8", "1", "16"), 1));
        questions.add(new QuestionDtoRequest("Корень из 16", 2, Arrays.asList("2", "4", "8", "32"), 2));
        questions.add(new QuestionDtoRequest("Корень из 25", 3, Arrays.asList("0", "1", "5", "125"), 3));
        questions.add(new QuestionDtoRequest("Корень из 36", 4, Arrays.asList("12", "169", "1", "6"), 4));
        addQuestions(teacher1Token, examDtoResponse.getId(), questions, ErrorCode.SUCCESS);
        ExamSetStateDtoRequest examSetStateDtoRequest = new ExamSetStateDtoRequest(4, 10, true);
        makeReady(teacher1Token, examDtoResponse.getId(), examSetStateDtoRequest, ErrorCode.SUCCESS);
        Client client = new Client();
        Object response = client.get(getBaseURL() + "/exams", ExamsParamDtoResponse.class, teacher1Token);
        if (response instanceof ExamsParamDtoResponse) {
            ExamsParamDtoResponse examsParamDtoResponse = (ExamsParamDtoResponse) response;
            assertEquals(3, examsParamDtoResponse.getList().size());
            assertEquals("Алгебра", examsParamDtoResponse.getList().get(0).getName());
            assertEquals(1, examsParamDtoResponse.getList().get(0).getSemester());
            assertTrue(examsParamDtoResponse.getList().get(0).isReady());
            assertEquals("Алгебра", examsParamDtoResponse.getList().get(1).getName());
            assertEquals(2, examsParamDtoResponse.getList().get(1).getSemester());
            assertFalse(examsParamDtoResponse.getList().get(1).isReady());
            assertEquals("Алгебра", examsParamDtoResponse.getList().get(2).getName());
            assertEquals(3, examsParamDtoResponse.getList().get(2).getSemester());
            assertFalse(examsParamDtoResponse.getList().get(1).isReady());
        } else {
            fail();
        }
        response = client.get(getBaseURL() + "/exams", ExamsParamDtoResponse.class, teacher2Token);
        if (response instanceof ExamsParamDtoResponse) {
            ExamsParamDtoResponse examsParamDtoResponse = (ExamsParamDtoResponse) response;
            assertEquals(2, examsParamDtoResponse.getList().size());
            assertEquals("Физика", examsParamDtoResponse.getList().get(0).getName());
            assertEquals(2, examsParamDtoResponse.getList().get(0).getSemester());
            assertFalse(examsParamDtoResponse.getList().get(0).isReady());
            assertEquals("Физика", examsParamDtoResponse.getList().get(1).getName());
            assertEquals(3, examsParamDtoResponse.getList().get(1).getSemester());
            assertFalse(examsParamDtoResponse.getList().get(1).isReady());
        } else {
            fail();
        }
    }

    @Test
    public void testGetTeacherExamsWhenHeHasNoExams() {
        createAndRegisterAndLoginTeacher("Алексей", "Иванов", "Валентинович", "Алгебра", "Доцент", "login1", "qwerty123", ErrorCode.SUCCESS);
        Object response = new Client().get(getBaseURL() + "/exams", ExamsParamDtoResponse.class, getToken());
        if (response instanceof ExamsParamDtoResponse) {
            ExamsParamDtoResponse examsParamDtoResponse = (ExamsParamDtoResponse) response;
            assertEquals(0, examsParamDtoResponse.getList().size());
        } else {
            fail();
        }
    }

    @Test
    public void testGetStudentExams() {
        createAndRegisterAndLoginTeacher("Алексей", "Петров", null, "Алгебра", "Доцент", "login1", "qwerty123", ErrorCode.SUCCESS);
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

        createAndRegisterAndLoginTeacher("Михаил", "Шевченко", "Алексеевич", "Физика", "Доцент", "login2", "qwerty123", ErrorCode.SUCCESS);
        String teacher2Token = getToken();
        ExamDtoResponse examDtoResponse2 = createExam(teacher2Token, "Физика", 1, ErrorCode.SUCCESS);
        List<QuestionDtoRequest> questions2 = new ArrayList<>();
        questions2.add(new QuestionDtoRequest("Третий закон Ньютона", 1, Arrays.asList("F = -F", "F != -F", "F = m*a"), 1));
        questions2.add(new QuestionDtoRequest("Ускорение свободного падения", 2, Arrays.asList("9", "9.8", "10"), 2));
        questions2.add(new QuestionDtoRequest("Обозначение момента силы", 3, Arrays.asList("N", "F", "M", "H"), 3));
        addQuestions(teacher2Token, examDtoResponse2.getId(), questions2, ErrorCode.SUCCESS);
        ExamSetStateDtoRequest examSetStateDtoRequest2 = new ExamSetStateDtoRequest(3, 11, true);
        makeReady(teacher2Token, examDtoResponse2.getId(), examSetStateDtoRequest2, ErrorCode.SUCCESS);

        createAndRegisterAndLoginStudent("Дмитрий", "Макаров", null, "МПБ-505", 1, "login3", "qwerty123", ErrorCode.SUCCESS);
        String student1Token = getToken();
        Client client = new Client();
        Object response = client.get(getBaseURL() + "/studentexams?filter=ALL", ExamsAndTeachersDtoResponse.class, student1Token);
        if (response instanceof ExamsAndTeachersDtoResponse) {
            ExamsAndTeachersDtoResponse examsAndTeachersDtoResponse = (ExamsAndTeachersDtoResponse) response;
            assertEquals(2, examsAndTeachersDtoResponse.getList().size());
            assertEquals("Алгебра", examsAndTeachersDtoResponse.getList().get(0).getName());
            assertEquals("Алексей", examsAndTeachersDtoResponse.getList().get(0).getFirstName());
            assertEquals("Петров", examsAndTeachersDtoResponse.getList().get(0).getLastName());
            assertNull(examsAndTeachersDtoResponse.getList().get(0).getPatronymic());
            assertEquals("Алгебра", examsAndTeachersDtoResponse.getList().get(0).getDepartment());
            assertEquals("Доцент", examsAndTeachersDtoResponse.getList().get(0).getPosition());
            assertEquals(4, (int) examsAndTeachersDtoResponse.getList().get(0).getQuestionsCountPerExam());
            assertEquals(10, examsAndTeachersDtoResponse.getList().get(0).getTimeInMinutes());

            assertEquals("Физика", examsAndTeachersDtoResponse.getList().get(1).getName());
            assertEquals("Михаил", examsAndTeachersDtoResponse.getList().get(1).getFirstName());
            assertEquals("Шевченко", examsAndTeachersDtoResponse.getList().get(1).getLastName());
            assertEquals("Алексеевич", examsAndTeachersDtoResponse.getList().get(1).getPatronymic());
            assertEquals("Физика", examsAndTeachersDtoResponse.getList().get(1).getDepartment());
            assertEquals("Доцент", examsAndTeachersDtoResponse.getList().get(1).getPosition());
            assertEquals(3, (int) examsAndTeachersDtoResponse.getList().get(1).getQuestionsCountPerExam());
            assertEquals(11, examsAndTeachersDtoResponse.getList().get(1).getTimeInMinutes());
        } else {
            fail();
        }

        createAndRegisterAndLoginStudent("Александр", "Петров", "Викторович", "МПБ-505", 6, "login4", "qwerty123", ErrorCode.SUCCESS);
        String student2Token = getToken();
        response = client.get(getBaseURL() + "/studentexams?filter=ALL", ExamsAndTeachersDtoResponse.class, student2Token);
        if (response instanceof ExamsAndTeachersDtoResponse) {
            ExamsAndTeachersDtoResponse examsAndTeachersDtoResponse = (ExamsAndTeachersDtoResponse) response;
            assertEquals(0, examsAndTeachersDtoResponse.getList().size());
        } else {
            fail();
        }
    }

    @Test
    public void getCurrentStudentExams() {
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

        createAndRegisterAndLoginStudent("Дмитрий", "Макаров", null, "МПБ-505", 1, "login3", "qwerty123", ErrorCode.SUCCESS);
        String student1Token = getToken();
        ExamForSolutionDtoResponse examForSolutionDtoResponse = getStudentExamQuestions(student1Token, examDtoResponse1.getId(),
                questions1, examSetStateDtoRequest1, ErrorCode.SUCCESS);
        getCurrentStudentExams(student1Token, new ExamSetStateDtoRequest[]{examSetStateDtoRequest1}, ErrorCode.SUCCESS);
        List<Integer> solutuion = Arrays.asList(examForSolutionDtoResponse.getQuestions().get(0).getCorrect(),
                examForSolutionDtoResponse.getQuestions().get(1).getCorrect(),
                examForSolutionDtoResponse.getQuestions().get(2).getCorrect(),
                examForSolutionDtoResponse.getQuestions().get(3).getCorrect());
        sendSolution(teacherDtoResponse, getToken(), examDtoResponse1.getId(), questions1, examSetStateDtoRequest1,
                solutuion, ErrorCode.SUCCESS);
        getCurrentStudentExams(student1Token, new ExamSetStateDtoRequest[0], ErrorCode.SUCCESS);
    }

    @Test
    public void testGetRemainingStudentExams() {
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

        ExamDtoResponse examDtoResponse2 = createExam(teacher1Token, "Геометрия", 1, ErrorCode.SUCCESS);
        List<QuestionDtoRequest> questions2 = new ArrayList<>();
        questions2.add(new QuestionDtoRequest("Корень из 25", null, Arrays.asList("0", "1", "5", "125"), 3));
        questions2.add(new QuestionDtoRequest("Корень из 36", null, Arrays.asList("12", "169", "1", "6"), 4));
        addQuestions(teacher1Token, examDtoResponse2.getId(), questions2, ErrorCode.SUCCESS);
        ExamSetStateDtoRequest examSetStateDtoRequest2 = new ExamSetStateDtoRequest(2, 10, true);
        makeReady(teacher1Token, examDtoResponse2.getId(), examSetStateDtoRequest2, ErrorCode.SUCCESS);

        createAndRegisterAndLoginStudent("Дмитрий", "Макаров", null, "МПБ-505", 1, "login3", "qwerty123", ErrorCode.SUCCESS);
        String student1Token = getToken();
        ExamForSolutionDtoResponse examForSolutionDtoResponse = getStudentExamQuestions(student1Token, examDtoResponse1.getId(),
                questions1, examSetStateDtoRequest1, ErrorCode.SUCCESS);
        getCurrentStudentExams(student1Token, new ExamSetStateDtoRequest[]{examSetStateDtoRequest1}, ErrorCode.SUCCESS);
        List<Integer> solutuion = Arrays.asList(examForSolutionDtoResponse.getQuestions().get(0).getCorrect(),
                examForSolutionDtoResponse.getQuestions().get(1).getCorrect(),
                examForSolutionDtoResponse.getQuestions().get(2).getCorrect(),
                examForSolutionDtoResponse.getQuestions().get(3).getCorrect());
        sendSolution(teacherDtoResponse, getToken(), examDtoResponse1.getId(), questions1, examSetStateDtoRequest1,
                solutuion, ErrorCode.SUCCESS);
        getRemainingStudentExams(student1Token, new ExamSetStateDtoRequest[]{examSetStateDtoRequest2}, ErrorCode.SUCCESS);
    }

    @Test
    public void testGetPassedStudentExams() {
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

        ExamDtoResponse examDtoResponse2 = createExam(teacher1Token, "Геометрия", 1, ErrorCode.SUCCESS);
        List<QuestionDtoRequest> questions2 = new ArrayList<>();
        questions2.add(new QuestionDtoRequest("Корень из 25", null, Arrays.asList("0", "1", "5", "125"), 3));
        questions2.add(new QuestionDtoRequest("Корень из 36", null, Arrays.asList("12", "169", "1", "6"), 4));
        addQuestions(teacher1Token, examDtoResponse2.getId(), questions2, ErrorCode.SUCCESS);
        ExamSetStateDtoRequest examSetStateDtoRequest2 = new ExamSetStateDtoRequest(2, 10, true);
        makeReady(teacher1Token, examDtoResponse2.getId(), examSetStateDtoRequest2, ErrorCode.SUCCESS);

        createAndRegisterAndLoginStudent("Дмитрий", "Макаров", null, "МПБ-505", 1, "login3", "qwerty123", ErrorCode.SUCCESS);
        String student1Token = getToken();
        ExamForSolutionDtoResponse examForSolutionDtoResponse = getStudentExamQuestions(student1Token, examDtoResponse1.getId(),
                questions1, examSetStateDtoRequest1, ErrorCode.SUCCESS);
        getCurrentStudentExams(student1Token, new ExamSetStateDtoRequest[]{examSetStateDtoRequest1}, ErrorCode.SUCCESS);
        List<Integer> solutuion = Arrays.asList(examForSolutionDtoResponse.getQuestions().get(0).getCorrect(),
                examForSolutionDtoResponse.getQuestions().get(1).getCorrect(),
                examForSolutionDtoResponse.getQuestions().get(2).getCorrect(),
                examForSolutionDtoResponse.getQuestions().get(3).getCorrect());
        sendSolution(teacherDtoResponse, getToken(), examDtoResponse1.getId(), questions1, examSetStateDtoRequest1,
                solutuion, ErrorCode.SUCCESS);
        getPassedStudentExams(student1Token, new ExamSetStateDtoRequest[]{examSetStateDtoRequest1}, ErrorCode.SUCCESS);
    }

    @Test
    public void testSendSolutionWithWrongAnswerNumber() {
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

        createAndRegisterAndLoginStudent("Дмитрий", "Макаров", null, "МПБ-505", 1, "login3", "qwerty123", ErrorCode.SUCCESS);
        String student1Token = getToken();
        ExamForSolutionDtoResponse examForSolutionDtoResponse = getStudentExamQuestions(student1Token, examDtoResponse1.getId(),
                questions1, examSetStateDtoRequest1, ErrorCode.SUCCESS);
        getCurrentStudentExams(student1Token, new ExamSetStateDtoRequest[]{examSetStateDtoRequest1}, ErrorCode.SUCCESS);
        List<Integer> solutuion = Arrays.asList(1, 2, 4);
        sendSolution(teacherDtoResponse, getToken(), examDtoResponse1.getId(), questions1, examSetStateDtoRequest1,
                solutuion, ErrorCode.INCOMPLETE_SOLUTION_SET);
    }

    @Test
    public void testSetSolutionForNotTakenExam() {
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

        createAndRegisterAndLoginStudent("Дмитрий", "Макаров", null, "МПБ-505", 1, "login3", "qwerty123", ErrorCode.SUCCESS);
        String student1Token = getToken();
        List<Integer> solutuion = Arrays.asList(1, 2, 4, 2);
        sendSolution(teacherDtoResponse, student1Token, examDtoResponse1.getId(), questions1, examSetStateDtoRequest1,
                solutuion, ErrorCode.EXAM_NOT_TAKEN);
    }

    @Test
    public void testGetStudentGetExamForAnotherSemester() {
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

        createAndRegisterAndLoginStudent("Дмитрий", "Макаров", null, "МПБ-505", 2, "login3", "qwerty123", ErrorCode.SUCCESS);
        String student1Token = getToken();
        ExamForSolutionDtoResponse examForSolutionDtoResponse = getStudentExamQuestions(student1Token, examDtoResponse1.getId(),
                questions1, examSetStateDtoRequest1, ErrorCode.NO_READY_EXAM_FOR_THIS_SEMESTER);
    }

    @Test
    public void testGetStudentGetUnreadyExam() {
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

        createAndRegisterAndLoginStudent("Дмитрий", "Макаров", null, "МПБ-505", 2, "login3", "qwerty123", ErrorCode.SUCCESS);
        String student1Token = getToken();
        ExamForSolutionDtoResponse examForSolutionDtoResponse = getStudentExamQuestions(student1Token, examDtoResponse1.getId(),
                questions1, new ExamSetStateDtoRequest(), ErrorCode.NO_READY_EXAM_FOR_THIS_SEMESTER);
    }

    @Test
    public void testGetStudentResultWithAllCorrectAnswers() {
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

        createAndRegisterAndLoginStudent("Дмитрий", "Макаров", null, "МПБ-505", 1, "login3", "qwerty123", ErrorCode.SUCCESS);
        String student1Token = getToken();
        ExamForSolutionDtoResponse examForSolutionDtoResponse = getStudentExamQuestions(student1Token, examDtoResponse1.getId(),
                questions1, examSetStateDtoRequest1, ErrorCode.SUCCESS);
        getCurrentStudentExams(student1Token, new ExamSetStateDtoRequest[]{examSetStateDtoRequest1}, ErrorCode.SUCCESS);
        List<Integer> solutuion = Arrays.asList(examForSolutionDtoResponse.getQuestions().get(0).getCorrect(),
                examForSolutionDtoResponse.getQuestions().get(1).getCorrect(),
                examForSolutionDtoResponse.getQuestions().get(2).getCorrect(),
                examForSolutionDtoResponse.getQuestions().get(3).getCorrect());
        sendSolution(teacherDtoResponse, getToken(), examDtoResponse1.getId(), questions1, examSetStateDtoRequest1,
                solutuion, ErrorCode.SUCCESS);
        getExamWithCorrectResults(teacherDtoResponse, student1Token, examDtoResponse1, questions1, examSetStateDtoRequest1,
                examForSolutionDtoResponse, ErrorCode.SUCCESS);
    }

}

