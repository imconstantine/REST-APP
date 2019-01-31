package com.imconstantine.netexam;

import com.imconstantine.netexam.dto.response.StudentDtoResponse;
import com.imconstantine.netexam.dto.response.TeacherDtoResponse;
import com.imconstantine.netexam.utils.ErrorCode;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

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
public class CommonClientTest extends ClientTestBase {

    @Test
    public void testGetSettingsByNone() throws Exception {
        getBaseSettings(ErrorCode.SUCCESS);
    }

    @Test
    public void testGetSettingsByTeacher() throws Exception {
        TeacherDtoResponse teacherDtoResponse = createAndRegisterAndLoginTeacher("Алексей", "Иванов", null, "Алгебра",
                "Доцент", "login", "qwerty123", ErrorCode.SUCCESS);
        getTeacherSettings(getToken(), ErrorCode.SUCCESS);
    }

    @Test
    public void testGetSettingsByStudent() throws Exception {
        StudentDtoResponse studentDtoResponse = createAndRegisterAndLoginStudent("Алексей", "Иванов", null, "MPB-402",
                1, "login", "qwerty123", ErrorCode.SUCCESS);
        getStudentSettings(getToken(), ErrorCode.SUCCESS);
    }

    @Test
    public void testWrongUrl() {
        Object response = new Client().post(getBaseURL() + "/some-wrong-url", null, Object.class, "application/json");
        checkFailureResponse(response, ErrorCode.WRONG_URL);
    }

    @Test
    public void testWrongJson() {
        Object response = new Client().postWrongJson(getBaseURL() + "/session", "{ \"firstName\" :", Object.class, "application/json");
        checkFailureResponse(response, ErrorCode.WRONG_JSON);
    }

    @Test
    public void testEmptyJson() {
        Object response = new Client().postWrongJson(getBaseURL() + "/session", "", Object.class, "application/json");
        checkFailureResponse(response, ErrorCode.WRONG_JSON);
    }

    @Test
    public void testJsonWithStringInsteadInt() {
        Object response = new Client().postWrongJson(getBaseURL() + "/exams", "{ \"name\" : \"Физика\", \"semester\" :" +
                " \"6D\" }", Object.class, "application/json", "token");
        checkFailureResponse(response, ErrorCode.WRONG_JSON);
    }

}
