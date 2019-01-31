package com.imconstantine.netexam;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.imconstantine.netexam.database.utils.MyBatisUtils;
import com.imconstantine.netexam.dto.request.*;
import com.imconstantine.netexam.dto.response.*;
import com.imconstantine.netexam.model.AnswerType;
import com.imconstantine.netexam.model.UserType;
import com.imconstantine.netexam.utils.ErrorCode;
import com.imconstantine.netexam.utils.Setting;
import com.imconstantine.netexam.utils.validator.Constants;
import org.junit.Assume;
import org.junit.Before;
import org.junit.BeforeClass;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.junit.Assert.*;

public class ClientTestBase {

    RestTemplate restTemplate = new RestTemplate();

    private static ObjectMapper mapper = new ObjectMapper();

    private static String baseURL = "http://localhost:8080/api";
    private static final String TOKEN_NAME = "JAVASESSIONID";
    private static boolean setUpIsDone = false;

    private Client client = new Client();

    private static String token;

    @BeforeClass()
    public static void setUp() {
        if (!setUpIsDone) {
            Assume.assumeTrue(Setting.loadConfigFile("myconfig.ini"));
            Assume.assumeTrue(MyBatisUtils.initSqlSessionFactory());
            setUpIsDone = true;
        }
    }

    @Before
    public void debugClear() {
        restTemplate.getForObject(baseURL + "/debug/clear", String.class);
    }

    protected TeacherDtoResponse createAndRegisterAndLoginTeacher(String firstName, String lastName, String patronymic, String department,
                                                                  String position, String login, String password,
                                                                  ErrorCode... expectedStatuses) {
        TeacherSignUpDtoRequest request = new TeacherSignUpDtoRequest(firstName, lastName, patronymic, login, password, department, position);
        Client client = new Client();
        Object response = client.post(baseURL + "/teacher", request, TeacherDtoResponse.class);
        if (response instanceof TeacherDtoResponse) {
            assertEquals(ErrorCode.SUCCESS, expectedStatuses[0]);
            token = client.getToken();
            assertNotNull(token);
            TeacherDtoResponse teacherDtoResponse = (TeacherDtoResponse) response;
            checkTeacherFields(firstName, lastName, patronymic, department, position, teacherDtoResponse);
            return teacherDtoResponse;
        } else {
            checkFailureResponse(response, expectedStatuses);
            return null;
        }
    }


    protected StudentDtoResponse createAndRegisterAndLoginStudent(String firstName, String lastName, String patronymic, String group,
                                                                  Integer semester, String login, String password,
                                                                  ErrorCode... expectedStatuses) {
        StudentSignUpDtoRequest request = new StudentSignUpDtoRequest(firstName, lastName, patronymic, login, password, group, semester);
        Client client = new Client();
        Object response = client.post(baseURL + "/student", request, StudentDtoResponse.class);
        if (response instanceof StudentDtoResponse) {
            assertEquals(ErrorCode.SUCCESS, expectedStatuses[0]);
            token = client.getToken();
            assertNotNull(token);
            StudentDtoResponse studentDtoResponse = (StudentDtoResponse) response;
            checkStudentFields(firstName, lastName, patronymic, group, semester, studentDtoResponse);
            return studentDtoResponse;
        } else {
            checkFailureResponse(response, expectedStatuses);
            return null;
        }
    }

    protected UserDto loginUser(String login, String password, ErrorCode expectedStatus) {
        LoginDtoRequest request = new LoginDtoRequest(login, password);
        Client client = new Client();
        Object response = client.post(baseURL + "/session", request, UserDto.class);
        if (response instanceof UserDto) {
            assertEquals(ErrorCode.SUCCESS, expectedStatus);
            UserDto userDto = (UserDto) response;
            token = client.getToken();
            assertNotNull(token);
            return userDto;
        } else {
            List<ErrorEntityDtoResponse> errorList = (List<ErrorEntityDtoResponse>) response;
            this.checkFailureResponse(errorList, expectedStatus);
            return null;
        }
    }

    protected void logoutUser(String token, ErrorCode expectedStatus) {
        Client client = new Client();
        Object response = client.delete(baseURL + "/session", EmptyDtoResponse.class, token);
        if (response instanceof EmptyDtoResponse) {
            assertEquals(ErrorCode.SUCCESS, expectedStatus);
        } else {
            List<ErrorEntityDtoResponse> errorList = (List<ErrorEntityDtoResponse>) response;
            this.checkFailureResponse(errorList, expectedStatus);
        }
    }

    protected ExamDtoResponse createExam(String token, String name, Integer semester, ErrorCode expectedStatus) {
        ExamDtoRequest request = new ExamDtoRequest(name, semester, null);
        Client client = new Client();
        Object response = client.post(baseURL + "/exams", request, ExamDtoResponse.class, token);
        if (response instanceof ExamDtoResponse) {
            assertEquals(ErrorCode.SUCCESS, expectedStatus);
            ExamDtoResponse examDtoResponse = (ExamDtoResponse) response;
            assertNotEquals(0, examDtoResponse.getId());
            assertEquals(name, examDtoResponse.getName());
            assertEquals((int) semester, examDtoResponse.getSemester());
            return examDtoResponse;
        } else {
            List<ErrorEntityDtoResponse> errorList = (List<ErrorEntityDtoResponse>) response;
            this.checkFailureResponse(errorList, expectedStatus);
            return null;
        }
    }

    protected ExamDtoResponse editExam(String token, String name, Integer semester, int examId, ErrorCode expectedStatus) {
        ExamUpdateDtoRequest request = new ExamUpdateDtoRequest(name, semester);
        Client client = new Client();
        Object response = client.put(baseURL + "/exams/" + examId, request, ExamDtoResponse.class, token);
        if (response instanceof ExamDtoResponse) {
            assertEquals(ErrorCode.SUCCESS, expectedStatus);
            ExamDtoResponse examDtoResponse = (ExamDtoResponse) response;
            assertNotEquals(0, examDtoResponse.getId());
            if (name != null) {
                assertEquals(name, examDtoResponse.getName());
            }
            if (semester != null) {
                assertEquals((int) semester, examDtoResponse.getSemester());
            }
            return examDtoResponse;
        } else {
            List<ErrorEntityDtoResponse> errorList = (List<ErrorEntityDtoResponse>) response;
            this.checkFailureResponse(errorList, expectedStatus);
            return null;
        }
    }

    protected ExamDtoResponse copyExam(String token, String name, Integer semester, int sourceId, ErrorCode expectedStatus) {
        ExamDtoRequest request = new ExamDtoRequest(name, semester, sourceId);
        Client client = new Client();
        Object response = client.post(baseURL + "/exams", request, ExamDtoResponse.class, token);
        if (response instanceof ExamDtoResponse) {
            assertEquals(ErrorCode.SUCCESS, expectedStatus);
            ExamDtoResponse examDtoResponse = (ExamDtoResponse) response;
            assertNotEquals(0, examDtoResponse.getId());
            assertEquals(name, examDtoResponse.getName());
            assertEquals((int) semester, examDtoResponse.getSemester());
            return examDtoResponse;
        } else {
            List<ErrorEntityDtoResponse> errorList = (List<ErrorEntityDtoResponse>) response;
            this.checkFailureResponse(errorList, expectedStatus);
            return null;
        }
    }

    protected ExamQuestionsDtoResponse addQuestions(String token, int examId, List<QuestionDtoRequest> questions,
                                                    ErrorCode... expectedStatuses) {
        Client client = new Client();
        Object response = client.post(baseURL + "/exams/" + examId + "/questions", questions, ExamQuestionsDtoResponse.class, token);
        if (response instanceof ExamQuestionsDtoResponse) {
            assertEquals(ErrorCode.SUCCESS, expectedStatuses[0]);
            ExamQuestionsDtoResponse examQuestionsDtoResponse = (ExamQuestionsDtoResponse) response;
            assertEquals(questions.size(), examQuestionsDtoResponse.getQuestionDtoResponses().size());
            for (int i = 0; i < questions.size(); i++) {
                assertEquals(questions.get(i).getQuestion(), examQuestionsDtoResponse.getQuestionDtoResponses().get(i).getQuestion());
                if (questions.get(i).getNumber() != null) {
                    assertEquals(questions.get(i).getNumber(), examQuestionsDtoResponse.getQuestionDtoResponses().get(i).getNumber());
                } else {
                    assertEquals(0, (int) examQuestionsDtoResponse.getQuestionDtoResponses().get(i).getNumber());
                }
                if (questions.get(i).getAnswers() != null) {
                    assertEquals(questions.get(i).getAnswers().size(), examQuestionsDtoResponse.getQuestionDtoResponses()
                            .get(i).getAnswerList().size());
                    assertArrayEquals(questions.get(i).getAnswers().toArray(), examQuestionsDtoResponse.getQuestionDtoResponses()
                            .get(i).getAnswerList().toArray());
                } else {
                    assertEquals(0, examQuestionsDtoResponse.getQuestionDtoResponses().get(i).getAnswerList().size());
                    assertEquals(questions.get(i).getCorrect(), examQuestionsDtoResponse.getQuestionDtoResponses().get(i).getCorrect());
                }
            }
            return examQuestionsDtoResponse;
        } else {
            checkFailureResponse(response, expectedStatuses);
            return null;
        }
    }

    protected void makeReady(String token, int examId, ExamSetStateDtoRequest examSetStateDtoRequest, ErrorCode... expectedStatuses) {
        Client client = new Client();
        Object response = client.put(baseURL + "/exams/" + examId + "/state", examSetStateDtoRequest, ExamSetStateDtoResponse.class, token);
        if (response instanceof ExamSetStateDtoResponse) {
            assertEquals(ErrorCode.SUCCESS, expectedStatuses[0]);
            ExamSetStateDtoResponse examSetStateDtoResponse = (ExamSetStateDtoResponse) response;
            assertEquals(examSetStateDtoRequest.getQuestionsCountPerExam(), examSetStateDtoResponse.getQuestionsCountPerExam());
            assertEquals(examSetStateDtoRequest.getTimeInMinutes(), examSetStateDtoResponse.getTimeInMinutes());
        } else {
            checkFailureResponse(response, expectedStatuses);
        }
    }

    protected ExamQuestionsDtoResponse getTeacherExamQuestions(String token, int examId, List<QuestionDtoRequest> questions,
                                                               ErrorCode... expectedStatuses) {
        Client client = new Client();
        Object response = client.get(baseURL + "/exams/" + examId + "/questions", ExamQuestionsDtoResponse.class, token);
        if (response instanceof ExamQuestionsDtoResponse) {
            assertEquals(ErrorCode.SUCCESS, expectedStatuses[0]);
            ExamQuestionsDtoResponse examQuestionsDtoResponse = (ExamQuestionsDtoResponse) response;
            assertEquals(questions.size(), examQuestionsDtoResponse.getQuestionDtoResponses().size());
            for (int i = 0; i < questions.size(); i++) {
                assertEquals(questions.get(i).getQuestion(), examQuestionsDtoResponse.getQuestionDtoResponses().get(i).getQuestion());
                if (questions.get(i).getNumber() != null) {
                    assertEquals(questions.get(i).getNumber(), examQuestionsDtoResponse.getQuestionDtoResponses().get(i).getNumber());
                } else {
                    assertEquals(null, examQuestionsDtoResponse.getQuestionDtoResponses().get(i).getNumber());
                }
                if (questions.get(i).getAnswers() != null) {
                    assertEquals(questions.get(i).getAnswers().size(), examQuestionsDtoResponse.getQuestionDtoResponses()
                            .get(i).getAnswerList().size());
                    assertArrayEquals(questions.get(i).getAnswers().toArray(), examQuestionsDtoResponse.getQuestionDtoResponses()
                            .get(i).getAnswerList().toArray());
                } else {
                    assertEquals(0, examQuestionsDtoResponse.getQuestionDtoResponses().get(i).getAnswerList().size());
                    assertEquals(questions.get(i).getCorrect(), examQuestionsDtoResponse.getQuestionDtoResponses().get(i).getCorrect());
                }
            }
            return examQuestionsDtoResponse;
        } else {
            checkFailureResponse(response, expectedStatuses);
            return null;
        }
    }

    protected ExamParamDtoResponse getTeacherExamParam(String token, int examId, Integer questionsCountPerExam, Integer timeInMinutes,
                                                       ErrorCode... expectedStatuses) {
        Client client = new Client();
        Object response = client.get(baseURL + "/exams/" + examId, ExamParamDtoResponse.class, token);
        if (response instanceof ExamParamDtoResponse) {
            assertEquals(ErrorCode.SUCCESS, expectedStatuses[0]);
            ExamParamDtoResponse examParamDtoResponse = (ExamParamDtoResponse) response;
            assertEquals(questionsCountPerExam, examParamDtoResponse.getQuestionsCountPerExam());
            assertEquals(timeInMinutes, examParamDtoResponse.getTimeInMinutes());
            return examParamDtoResponse;
        } else {
            checkFailureResponse(response, expectedStatuses);
            return null;
        }
    }

    protected ExamForSolutionDtoResponse getStudentExamQuestions(String token, int examId, List<QuestionDtoRequest> questions,
                                                                 ExamSetStateDtoRequest examSetStateDtoRequest,
                                                                 ErrorCode... expectedStatuses) {
        Client client = new Client();
        Object response = client.get(baseURL + "/studentexams/" + examId, ExamForSolutionDtoResponse.class, token);
        if (response instanceof ExamForSolutionDtoResponse) {
            assertEquals(ErrorCode.SUCCESS, expectedStatuses[0]);
            ExamForSolutionDtoResponse examForSolutionDtoResponse = (ExamForSolutionDtoResponse) response;
            assertEquals((int) examSetStateDtoRequest.getQuestionsCountPerExam(), examForSolutionDtoResponse.getQuestions().size());
            outer:
            for (QuestionDtoResponse questionDtoResponse : examForSolutionDtoResponse.getQuestions()) {
                for (QuestionDtoRequest questionDtoRequest : questions) {
                    if (questionDtoRequest.getQuestion().equals(questionDtoResponse.getQuestion())
                            && questionDtoRequest.getNumber() == questionDtoResponse.getNumber()) {
                        assertArrayEquals(questionDtoRequest.getAnswers().toArray(), questionDtoResponse.getAnswerList().toArray());
                        continue outer;
                    }
                }
            }
            return examForSolutionDtoResponse;
        } else {
            checkFailureResponse(response, expectedStatuses);
            return null;
        }
    }

    protected ExamsAndTeachersDtoResponse getAllStudentExams(String token, ExamSetStateDtoRequest[] examSetStateDtoRequests,
                                                                 ErrorCode... expectedStatuses) {
        Client client = new Client();
        Object response = client.get(baseURL + "/studentexams?filter=ALL", ExamsAndTeachersDtoResponse.class, token);
        if (response instanceof ExamsAndTeachersDtoResponse) {
            assertEquals(ErrorCode.SUCCESS, expectedStatuses[0]);
            ExamsAndTeachersDtoResponse examAndTeacherListDtoResponse = (ExamsAndTeachersDtoResponse) response;
            assertEquals(examSetStateDtoRequests.length, examAndTeacherListDtoResponse.getList().size());
            return examAndTeacherListDtoResponse;
        } else {
            checkFailureResponse(response, expectedStatuses);
            return null;
        }
    }

    protected ExamsAndTeachersDtoResponse getCurrentStudentExams(String token, ExamSetStateDtoRequest[] examSetStateDtoRequests,
                                                                 ErrorCode... expectedStatuses) {
        Client client = new Client();
        Object response = client.get(baseURL + "/studentexams?filter=CURRENT", ExamsAndTeachersDtoResponse.class, token);
        if (response instanceof ExamsAndTeachersDtoResponse) {
            assertEquals(ErrorCode.SUCCESS, expectedStatuses[0]);
            ExamsAndTeachersDtoResponse examAndTeacherListDtoResponse = (ExamsAndTeachersDtoResponse) response;
            assertEquals(examSetStateDtoRequests.length, examAndTeacherListDtoResponse.getList().size());
            return examAndTeacherListDtoResponse;
        } else {
            checkFailureResponse(response, expectedStatuses);
            return null;
        }
    }

    protected ExamsAndTeachersDtoResponse getRemainingStudentExams(String token, ExamSetStateDtoRequest[] examSetStateDtoRequests,
                                                                   ErrorCode... expectedStatuses) {
        Client client = new Client();
        Object response = client.get(baseURL + "/studentexams?filter=REMAINING", ExamsAndTeachersDtoResponse.class, token);
        if (response instanceof ExamsAndTeachersDtoResponse) {
            assertEquals(ErrorCode.SUCCESS, expectedStatuses[0]);
            ExamsAndTeachersDtoResponse examAndTeacherListDtoResponse = (ExamsAndTeachersDtoResponse) response;
            assertEquals(examSetStateDtoRequests.length, examAndTeacherListDtoResponse.getList().size());
            return examAndTeacherListDtoResponse;
        } else {
            checkFailureResponse(response, expectedStatuses);
            return null;
        }
    }

    protected ExamsAndTeachersDtoResponse getPassedStudentExams(String token, ExamSetStateDtoRequest[] examSetStateDtoRequests,
                                                                ErrorCode... expectedStatuses) {
        Client client = new Client();
        Object response = client.get(baseURL + "/studentexams?filter=PASSED", ExamsAndTeachersDtoResponse.class, token);
        if (response instanceof ExamsAndTeachersDtoResponse) {
            assertEquals(ErrorCode.SUCCESS, expectedStatuses[0]);
            ExamsAndTeachersDtoResponse examAndTeacherListDtoResponse = (ExamsAndTeachersDtoResponse) response;
            assertEquals(examSetStateDtoRequests.length, examAndTeacherListDtoResponse.getList().size());
            return examAndTeacherListDtoResponse;
        } else {
            checkFailureResponse(response, expectedStatuses);
            return null;
        }
    }


    protected void getExamWithCorrectResults(TeacherDtoResponse teacher, String token, ExamDtoResponse examDtoResponse,
                                  List<QuestionDtoRequest> questions, ExamSetStateDtoRequest examSetStateDtoRequest,
                                  ExamForSolutionDtoResponse examForSolutionDtoResponse,
                                  ErrorCode... expectedStatuses) {
        Client client = new Client();
        Object response = client.get(baseURL + "/studentexams/solutions", StudentExamResultListDtoResponse.class, token);
        if (response instanceof StudentExamResultListDtoResponse) {
            assertEquals(ErrorCode.SUCCESS, expectedStatuses[0]);
            StudentExamResultListDtoResponse studentResults = (StudentExamResultListDtoResponse) response;
            assertEquals(examDtoResponse.getId(), studentResults.getStudentResultMainDtoResponses().get(0).getId());
            assertEquals(1, studentResults.getStudentResultMainDtoResponses().size());
            assertEquals(examDtoResponse.getName(), studentResults.getStudentResultMainDtoResponses().get(0).getName());
            StudentResultMainDtoResponse studentResult = studentResults.getStudentResultMainDtoResponses().get(0);
            assertEquals(studentResult.getTeacher().getFirstName(), teacher.getFirstName());
            assertEquals(studentResult.getTeacher().getLastName(), teacher.getLastName());
            assertEquals(studentResult.getTeacher().getPatronymic(), teacher.getPatronymic());
            assertEquals(studentResult.getResult().getDetails().size(), (int) examSetStateDtoRequest.getQuestionsCountPerExam());
            for (AnswerType answer : studentResult.getResult().getDetails()) {
                assertEquals(answer, AnswerType.YES);
            }
        } else {
            checkFailureResponse(response, expectedStatuses);
        }
    }

    protected void getExamResultsBeforeTimeOver(String token, ExamDtoResponse examDtoResponse,
                                             List<QuestionDtoRequest> question, ExamSetStateDtoRequest examSetStateDtoRequest,
                                             ErrorCode... expectedStatuses) {
        Client client = new Client();
        Object response = client.get(baseURL + "/studentexams/solutions", StudentExamResultListDtoResponse.class, token);
        if (response instanceof StudentExamResultListDtoResponse) {
            assertEquals(ErrorCode.SUCCESS, expectedStatuses[0]);
            StudentExamResultListDtoResponse studentResults = (StudentExamResultListDtoResponse) response;
            assertEquals(examDtoResponse.getId(), studentResults.getStudentResultMainDtoResponses().get(0).getId());
            assertEquals(1, studentResults.getStudentResultMainDtoResponses().size());
            assertEquals(examDtoResponse.getName(), studentResults.getStudentResultMainDtoResponses().get(0).getName());
            StudentResultMainDtoResponse studentResult = studentResults.getStudentResultMainDtoResponses().get(0);
            assertEquals(studentResult.getResult().getDetails().size(), (int) examSetStateDtoRequest.getQuestionsCountPerExam());
            for (AnswerType answer : studentResult.getResult().getDetails()) {
                assertEquals(answer, AnswerType.NO_ANSWER);
            }
        } else {
            checkFailureResponse(response, expectedStatuses);
        }
    }

    protected void getExamResultsForTimeOver(String token, ExamDtoResponse examDtoResponse,
                                             List<QuestionDtoRequest> question, ExamSetStateDtoRequest examSetStateDtoRequest,
                                             ExamForSolutionDtoResponse examForSolutionDtoResponse,
                                             ErrorCode... expectedStatuses) {
        Client client = new Client();
        Object response = client.get(baseURL + "/studentexams/solutions", StudentExamResultListDtoResponse.class, token);
        if (response instanceof StudentExamResultListDtoResponse) {
            assertEquals(ErrorCode.SUCCESS, expectedStatuses[0]);
            StudentExamResultListDtoResponse studentResults = (StudentExamResultListDtoResponse) response;
            assertEquals(examDtoResponse.getId(), studentResults.getStudentResultMainDtoResponses().get(0).getId());
            assertEquals(1, studentResults.getStudentResultMainDtoResponses().size());
            assertEquals(examDtoResponse.getName(), studentResults.getStudentResultMainDtoResponses().get(0).getName());
            StudentResultMainDtoResponse studentResult = studentResults.getStudentResultMainDtoResponses().get(0);
            assertEquals(studentResult.getResult().getDetails().size(), (int) examSetStateDtoRequest.getQuestionsCountPerExam());
            for (AnswerType answer : studentResult.getResult().getDetails()) {
                assertEquals(answer, AnswerType.NO_ANSWER);
            }
        } else {
            checkFailureResponse(response, expectedStatuses);
        }
    }

    protected void getEmptyExamResults(String token, ErrorCode... expectedStatuses) {
        Client client = new Client();
        Object response = client.get(baseURL + "/studentexams/solutions", StudentExamResultListDtoResponse.class, token);
        if (response instanceof StudentExamResultListDtoResponse) {
            assertEquals(ErrorCode.SUCCESS, expectedStatuses[0]);
            StudentExamResultListDtoResponse studentResults = (StudentExamResultListDtoResponse) response;
            assertEquals(0, studentResults.getStudentResultMainDtoResponses().size());
        } else {
            checkFailureResponse(response, expectedStatuses);
        }
    }

    protected void getExamResultsWithNoAnswer(String token, List<QuestionDtoRequest> questions,
                                              ExamSetStateDtoRequest examSetStateDtoRequest,
                                              ExamForSolutionDtoResponse examForSolutionDtoResponse,
                                              ErrorCode... expectedStatuses) {
        Client client = new Client();
        Object response = client.get(baseURL + "/studentexams/solutions", StudentExamResultListDtoResponse.class, token);
        if (response instanceof StudentExamResultListDtoResponse) {
            assertEquals(ErrorCode.SUCCESS, expectedStatuses[0]);
            StudentExamResultListDtoResponse studentResults = (StudentExamResultListDtoResponse) response;
            StudentResultMainDtoResponse studentResult = studentResults.getStudentResultMainDtoResponses().get(0);
            assertEquals((int) examSetStateDtoRequest.getQuestionsCountPerExam(), studentResult.getResult().getDetails().size());
            assertEquals(studentResult.getResult().getDetails().get(0), AnswerType.YES);
            assertEquals(studentResult.getResult().getDetails().get(1), AnswerType.NO_ANSWER);
        } else {
            checkFailureResponse(response, expectedStatuses);
        }
    }

    protected void getExamStudents(String token, int examId, List<QuestionDtoRequest> questions,
                                   ExamSetStateDtoRequest examSetStateDtoRequest, ErrorCode... expectedStatuses) {
        Client client = new Client();
        Object response = client.get(baseURL + "/exams/" + examId + "/students", ExamStudentsResult.class, token);
        if (response instanceof ExamStudentsResult) {
            assertEquals(ErrorCode.SUCCESS, expectedStatuses[0]);
            ExamStudentsResult examStudentsResults = (ExamStudentsResult) response;
            assertEquals("Физика", examStudentsResults.getName());
            assertEquals(2, examStudentsResults.getGroups().size());
            assertEquals(2, examStudentsResults.getGroups().get("МПБ-402").getStudents().size());
            assertEquals(1, examStudentsResults.getGroups().get("МПБ-505").getStudents().size());
        } else {
            checkFailureResponse(response, expectedStatuses);
        }
    }

    protected void getExamStudentsForTimeOver(String token, int examId, List<QuestionDtoRequest> questions,
                                              ExamSetStateDtoRequest examSetStateDtoRequest, ErrorCode... expectedStatuses) {
        Client client = new Client();
        Object response = client.get(baseURL + "/exams/" + examId + "/students", ExamStudentsResult.class, token);
        if (response instanceof ExamStudentsResult) {
            assertEquals(ErrorCode.SUCCESS, expectedStatuses[0]);
            ExamStudentsResult examStudentsResults = (ExamStudentsResult) response;
            assertEquals("Физика", examStudentsResults.getName());
            assertEquals(1, examStudentsResults.getGroups().size());
            assertEquals(1, examStudentsResults.getGroups().get("МПБ-402").getStudents().size());
        } else {
            checkFailureResponse(response, expectedStatuses);
        }
    }

    protected void sendSolution(TeacherDtoResponse teacher, String token, int examId, List<QuestionDtoRequest> questions,
                                ExamSetStateDtoRequest examSetStateDtoRequest, List<Integer> solution,
                                ErrorCode... expectedStatuses) {
        Client client = new Client();
        Object response = client.post(baseURL + "/studentexams/" + examId + "/solutions", solution, StudentExamResultDtoResponse.class, token);
        if (response instanceof StudentExamResultDtoResponse) {
            assertEquals(ErrorCode.SUCCESS, expectedStatuses[0]);
            StudentExamResultDtoResponse resultDtoResponse = (StudentExamResultDtoResponse) response;
            assertEquals((int) examSetStateDtoRequest.getQuestionsCountPerExam(), resultDtoResponse.getDetails().size());
        } else {
            checkFailureResponse(response, expectedStatuses);
        }
    }

    protected SettingsDtoResponse getTeacherSettings(String token, ErrorCode... expectedStatuses) {
        Client client = new Client();
        Object response = client.get(baseURL + "/settings", SettingsDtoResponse.class, token);
        if (response instanceof SettingsDtoResponse) {
            assertEquals(ErrorCode.SUCCESS, expectedStatuses[0]);
            SettingsDtoResponse settingsDtoResponse = (SettingsDtoResponse) response;
            assertEquals(Constants.MIN_TIME, (int) settingsDtoResponse.getMinTime());
            assertEquals(Constants.MAX_NAME_LENGTH, (int) settingsDtoResponse.getMaxNameLength());
            assertEquals(Constants.MIN_ANSWERS, (int) settingsDtoResponse.getMinAnswers());
            assertEquals(Constants.MIN_PASSWORD_LENGTH, (int) settingsDtoResponse.getMinPasswordLength());
            assertEquals(Constants.MIN_QUESTIONS_COUNT_PER_EXAM, (int) settingsDtoResponse.getMinQuestionsCountPerExam());
            return settingsDtoResponse;
        } else {
            checkFailureResponse(response, expectedStatuses);
            return null;
        }
    }

    protected SettingsDtoResponse getStudentSettings(String token, ErrorCode... expectedStatuses) {
        Client client = new Client();
        Object response = client.get(baseURL + "/settings", SettingsDtoResponse.class, token);
        if (response instanceof SettingsDtoResponse) {
            assertEquals(ErrorCode.SUCCESS, expectedStatuses[0]);
            SettingsDtoResponse settingsDtoResponse = (SettingsDtoResponse) response;
            assertEquals(Constants.MAX_NAME_LENGTH, (int) settingsDtoResponse.getMaxNameLength());
            assertEquals(Constants.MIN_PASSWORD_LENGTH, (int) settingsDtoResponse.getMinPasswordLength());
            return settingsDtoResponse;
        } else {
            checkFailureResponse(response, expectedStatuses);
            return null;
        }
    }

    protected SettingsDtoResponse getBaseSettings(ErrorCode... expectedStatuses) {
        Client client = new Client();
        Object response = client.get(baseURL + "/settings", SettingsDtoResponse.class, token);
        if (response instanceof SettingsDtoResponse) {
            assertEquals(ErrorCode.SUCCESS, expectedStatuses[0]);
            SettingsDtoResponse settingsDtoResponse = (SettingsDtoResponse) response;
            assertEquals(Constants.MAX_NAME_LENGTH, (int) settingsDtoResponse.getMaxNameLength());
            assertEquals(Constants.MIN_PASSWORD_LENGTH, (int) settingsDtoResponse.getMinPasswordLength());
            return settingsDtoResponse;
        } else {
            checkFailureResponse(response, expectedStatuses);
            return null;
        }
    }

    protected String getToken(HttpHeaders httpHeaders) {
        return httpHeaders.get("Set-Cookie").get(0).substring(14, 50);
    }

    protected void checkTeacherFields(String firstName, String lastName, String patronymic, String department, String position,
                                      TeacherDtoResponse teacherDtoResponse) {
        assertEquals(firstName, teacherDtoResponse.getFirstName());
        assertEquals(lastName, teacherDtoResponse.getLastName());
        assertEquals(patronymic, teacherDtoResponse.getPatronymic());
        assertEquals(department, teacherDtoResponse.getDepartment());
        assertEquals(position, teacherDtoResponse.getPosition());
        assertEquals(UserType.TEACHER.toString(), teacherDtoResponse.getType());
    }

    protected void checkStudentFields(String firstName, String lastName, String patronymic, String group, int semester,
                                      StudentDtoResponse studentDtoResponse) {
        assertEquals(firstName, studentDtoResponse.getFirstName());
        assertEquals(lastName, studentDtoResponse.getLastName());
        assertEquals(patronymic, studentDtoResponse.getPatronymic());
        assertEquals(group, studentDtoResponse.getGroup());
        assertEquals(semester, studentDtoResponse.getSemester());
        assertEquals(UserType.STUDENT.toString(), studentDtoResponse.getType());
    }

    protected void checkFailureResponse(List<ErrorEntityDtoResponse> errorList, ErrorCode expectedStatus) {
        assertNotNull(errorList);
        assertEquals(1, errorList.size());
        assertEquals(expectedStatus, errorList.get(0).getErrorCode());
    }

    protected void checkFailureResponseLength(List<ErrorEntityDtoResponse> errorList, int expectedStatusesCount) {
        assertNotNull(errorList);
        assertEquals(expectedStatusesCount, errorList.size());
    }

    protected void checkFailureResponse(Object response, ErrorCode... expectedStatuses) {
        List<ErrorEntityDtoResponse> errorList = (List<ErrorEntityDtoResponse>) response;
        if (expectedStatuses.length == 1) {
            checkFailureResponse(errorList, expectedStatuses[0]);
        } else {
            checkFailureResponseLength(errorList, expectedStatuses.length);
        }
    }

    public Client getClient() {
        return client;
    }

    public static String getBaseURL() {
        return baseURL;
    }

    public static String getToken() {
        return token;
    }

}
