package com.imconstantine.netexam;

import com.imconstantine.netexam.dto.response.StudentDtoResponse;
import com.imconstantine.netexam.dto.response.TeacherDtoResponse;
import com.imconstantine.netexam.utils.ErrorCode;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.fail;


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
public class UserClientTest extends ClientTestBase {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserClientTest.class);

    @Test
    public void testRegisterTeacher() {
        createAndRegisterAndLoginTeacher("Алексей", "Петров", null, "Алгебра", "Доцент", "log1i122na", "qwerty123", ErrorCode.SUCCESS);
    }

    @Test
    public void testRegisterStudent() {
        createAndRegisterAndLoginStudent("Петр", "Вдовиченко", null, "МПБ-402", 8, "logina123", "password", ErrorCode.SUCCESS);
    }

    @Test
    public void testRegisterStudentWithExistentTeacherLogin() {
        createAndRegisterAndLoginTeacher("Алексей", "Петров", null, "Алгебра", "Доцент", "login", "qwerty123", ErrorCode.SUCCESS);
        createAndRegisterAndLoginStudent("Петр", "Вдовиченко", null, "МПБ-402", 8, "login", "password", ErrorCode.LOGIN_ALREADY_EXISTS);
    }

    @Test
    public void testRegisterTeacherWithNullRequest() {
        Object response = new Client().post(getBaseURL() + "/teacher", null, TeacherDtoResponse.class, "application/json");
        checkFailureResponse(response, ErrorCode.WRONG_JSON);
    }

    @Test
    public void testRegisterStudentWithNullRequest() {
        Object response = new Client().post(getBaseURL() + "/student", null, TeacherDtoResponse.class, "application/json");
        checkFailureResponse(response, ErrorCode.WRONG_JSON);
    }

    @Test
    public void testRegisterTeacherTwice() {
        createAndRegisterAndLoginTeacher("Алексей", "Петров", null, "Алгебра", "Доцент", "log1i122na", "qwerty123", ErrorCode.SUCCESS);
        createAndRegisterAndLoginTeacher("Алексей", "Петров", null, "Алгебра", "Доцент", "log1i122na", "qwerty123", ErrorCode.LOGIN_ALREADY_EXISTS);
    }

    @Test
    public void testRegisterTeacherWithNotRussianFirstName() {
        createAndRegisterAndLoginTeacher("Alexey", "Петров", null, "Алгебра", "Доцент", "log1i122na", "qwerty123", ErrorCode.INVALID_FIRSTNAME);
    }

    @Test
    public void testRegisterTeacherWithNullFirstName() {
        createAndRegisterAndLoginTeacher(null, "Петров", null, "Алгебра", "Доцент", "log1i122na", "qwerty123", ErrorCode.FIRSTNAME_IS_NULL);
    }

    @Test
    public void testRegisterTeacherWithNotRussianLastName() {
        createAndRegisterAndLoginTeacher("Алексей", "Petrov-Алексеенко", null, "Алгебра", "Доцент", "log1i122na", "qwerty123", ErrorCode.INVALID_LASTNAME);
    }

    @Test
    public void testRegisterTeacherWithWrongLogin() {
        createAndRegisterAndLoginTeacher("Алексей", "Петров", null, "Алгебра", "Доцент", "log1i122na_%#", "qwerty123", ErrorCode.INVALID_LOGIN);
    }

    @Test
    public void testRegisterTeacherWithTooShortPassword() {
        createAndRegisterAndLoginTeacher("Алексей", "Петров", null, "Алгебра", "Доцент", "log1i122na", "qwe", ErrorCode.PASSWORD_WRONG_LENGTH);
    }

    @Test
    public void testRegisterTeacherWithTooLongPassword() {
        String pswd = "1234567890123456789012345678901234567890123456789012345678901245678";
        createAndRegisterAndLoginTeacher("Алексей", "Петров", null, "Алгебра", "Доцент", "log1i122na", pswd, ErrorCode.PASSWORD_WRONG_LENGTH);
    }

    @Test
    public void testRegisterTeacherWithEmptyPassword() {
        createAndRegisterAndLoginTeacher("Алексей", "Петров", null, "Алгебра", "Доцент", "log1i122na", "", ErrorCode.PASSWORD_IS_NULL);
    }

    @Test
    public void testRegisterTeacherWithAllNullFields() {
        createAndRegisterAndLoginTeacher(null, null, null, null, null, null, null,
                ErrorCode.FIRSTNAME_IS_NULL,
                ErrorCode.LASTNAME_IS_NULL,
                ErrorCode.LOGIN_IS_NULL,
                ErrorCode.PASSWORD_IS_NULL,
                ErrorCode.DEPARTMENT_IS_NULL,
                ErrorCode.POSITION_IS_NULL
        );
    }

    @Test
    public void testRegisterStudentWithAllNullFields() {
        createAndRegisterAndLoginStudent(null, null, null, null, null, null, null,
                ErrorCode.FIRSTNAME_IS_NULL,
                ErrorCode.LASTNAME_IS_NULL,
                ErrorCode.LOGIN_IS_NULL,
                ErrorCode.PASSWORD_IS_NULL,
                ErrorCode.GROUP_IS_NULL,
                ErrorCode.SEMESTER_IS_NULL
        );
    }

    @Test
    public void testRegisterSeveralTeachers() {
        String[] logins = {"login1", "login2", "login3", "login4", "login5"};
        for (int i = 0; i < logins.length; i++) {
            createAndRegisterAndLoginTeacher("Алексей", "Петров", null, "Алгебра", "Доцент", logins[i], "qwerty123", ErrorCode.SUCCESS);
        }
    }

    @Test
    public void testLoginAndLogoutTeacher() {
        createAndRegisterAndLoginTeacher("Алексей", "Петров", null, "Алгебра", "Доцент", "login", "password", ErrorCode.SUCCESS);
        logoutUser(getToken(), ErrorCode.SUCCESS);
        TeacherDtoResponse loginTeacher = (TeacherDtoResponse) loginUser("login", "password", ErrorCode.SUCCESS);
        checkTeacherFields("Алексей", "Петров", null, "Алгебра", "Доцент", loginTeacher);
        logoutUser(getToken(), ErrorCode.SUCCESS);
    }

    @Test
    public void testLoginAndLogoutStudent() {
        createAndRegisterAndLoginStudent("Алексей", "Петров", null, "МПБ-502", 3, "login", "password", ErrorCode.SUCCESS);
        logoutUser(getToken(), ErrorCode.SUCCESS);
        StudentDtoResponse loginStudent = (StudentDtoResponse) loginUser("login", "password", ErrorCode.SUCCESS);
        checkStudentFields("Алексей", "Петров", null, "МПБ-502", 3, loginStudent);
        logoutUser(getToken(), ErrorCode.SUCCESS);
    }

    @Test
    public void testReloginWithoutLogoutTeacherSeveralTimes() {
        TeacherDtoResponse loginTeacher;
        createAndRegisterAndLoginTeacher("Алексей", "Петров", null, "Алгебра", "Доцент", "login", "password", ErrorCode.SUCCESS);
        String oldToken = getToken();
        for (int i = 0; i < 5; i++) {
            loginTeacher = (TeacherDtoResponse) loginUser("login", "password", ErrorCode.SUCCESS);
            assertNotEquals(oldToken, getToken());
            checkTeacherFields("Алексей", "Петров", null, "Алгебра", "Доцент", loginTeacher);
        }
        logoutUser(getToken(), ErrorCode.SUCCESS);
    }

    @Test
    public void testLoginAndLogoutTeacherSeveralTimes() {
        TeacherDtoResponse loginTeacher;
        createAndRegisterAndLoginTeacher("Алексей", "Петров", null, "Алгебра", "Доцент", "login", "password", ErrorCode.SUCCESS);
        for (int i = 0; i < 5; i++) {
            logoutUser(getToken(), ErrorCode.SUCCESS);
            loginTeacher = (TeacherDtoResponse) loginUser("login", "password", ErrorCode.SUCCESS);
            checkTeacherFields("Алексей", "Петров", null, "Алгебра", "Доцент", loginTeacher);
        }
        logoutUser(getToken(), ErrorCode.SUCCESS);
    }

    @Test
    public void testLoginWithNonexistentLogin() {
        loginUser("login231", "password", ErrorCode.LOGIN_IS_NOT_EXISTING);
    }

    @Test
    public void testLoginTeacherWithWrongPassword() {
        createAndRegisterAndLoginTeacher("Алексей", "Петров", null, "Алгебра", "Доцент", "login", "qwerty123", ErrorCode.SUCCESS);
        logoutUser(getToken(), ErrorCode.SUCCESS);
        loginUser("login", "qwerty", ErrorCode.PASSWORD_NOT_MATCH);
    }

    @Test
    public void testLogoutWithWrongToken() {
        logoutUser("1234", ErrorCode.TOKEN_DOES_NOT_EXIST);
    }

    @Test
    public void testRegisterUsersInThreads() {
        int threadsCount = 10;
        ThreadTester[] threads = new ThreadTester[threadsCount];
        for (int i = 0; i < threadsCount; i++) {
            final int k = i;
            threads[i] = new ThreadTester(() -> {
                for (int i1 = 0; i1 < 10; i1++) {
                    createAndRegisterAndLoginTeacher("Иван", "Иванов-Васильев", "Иванович", "алгебра", "доцент",
                            "login" + k + i1, "password", ErrorCode.SUCCESS);
                    logoutUser(getToken(), ErrorCode.SUCCESS);
                }
            });

        }
        for (int i = 0; i < threadsCount; i++) {
            threads[i].start();
            try {
                threads[i].test();
            } catch (InterruptedException e) {
                LOGGER.debug("Interrupted exception with threads");
            } catch (Exception e) {
                LOGGER.debug("Exception with threads");
                fail();
            }
        }
    }

}
