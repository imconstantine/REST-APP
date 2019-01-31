package com.imconstantine.netexam.utils;

import com.imconstantine.netexam.utils.validator.Constants;

public enum ErrorCode {

    SUCCESS("", ""),
    WRONG_URL("url", "Wrong url"),
    WRONG_JSON("json", "Wrong or empty json body"),
    DATABASE_ERROR("database", "Database error, don't worry"),
    NOT_FOUND("content", "Content not found"),
    FIRSTNAME_IS_NULL("firstName", "Field firstName mustn't be null"),
    FIRSTNAME_TOO_LONG("firstName", "Field firstName too long"),
    LASTNAME_IS_NULL("lastName", "Field lastName mustn't be null"),
    LASTNAME_TOO_LONG("firstName", "Field lastName too long"),
    PATRONYMIC_IS_NULL("patronymic", "Field patronymic mustn't be null"),
    PATRONYMIC_TOO_LONG("firstName", "Field patronymic too long"),
    LOGIN_IS_NULL("login", "Field login mustn't be null"),
    LOGIN_TOO_LONG("login", "Field login too long"),
    PASSWORD_IS_NULL("password", "Field password mustn't be null"),
    OLD_PASSWORD_IS_NULL("oldPassword", "Field oldPassword mustn't be null"),
    NEW_PASSWORD_IS_NULL("newPassword", "Filed newPassword mustn't be null"),
    DEPARTMENT_IS_NULL("department", "Field department mustn't be null"),
    POSITION_IS_NULL("position", "Field position mustn't be null"),
    GROUP_IS_NULL("group", "Field group mustn't be null"),
    SEMESTER_IS_NULL("semester", "Field semester mustn't be null"),
    EXAM_NAME_IS_NULL("name", "Field name mustn't be null"),
    PASSWORD_NOT_MATCH("password", "Password not match"),
    TOKEN_DOES_NOT_EXIST("token", "You aren't login"),
    TOKEN_ALREADY_EXISTS("token", "Token already exists"),
    ID_DOES_NOT_EXIST("id", "ID %s doesn't exist"),
    CAN_NOT_READ_TOKEN("cookie", "You haven't cookie's token"),
    USER_IS_NOT_TEACHER("user", "User isn't teacher"),
    LOGIN_ALREADY_EXISTS("login", "Login %s already exists"),
    LOGIN_IS_NOT_EXISTING("login", "Login %s is not exists"),
    SEMESTER_BAD_VALUE("semester", "Field semester must be between 1 and 12"),
    EXAM_IS_EXISTING("name", "Exam with name %s already exists"),
    INCOMPLETE_SOLUTION_SET("solution", "Incomplete solution set. Result will be saved anyway"),
    EXAM_IS_NOT_EXIST("id", "Exam with id %s isn't existing"),
    EXAM_QUESTIONS_COUNT_IS_NULL("questionsCountPerExam", "Field questionsCountPerExam mustn't be null"),
    EXAM_QUESTIONS_LESS_THAN_QUESTIONS_COUNT_EXAM("questions", "Exam's questions less than questions count per exam"),
    EXAM_TIME_IS_NULL("timeInMinutes", "Field timeInMinutes mustn't be null"),
    EXAM_DETAILS_IS_NULL("showDetails", "Field showDetails mustn't be null"),
    EXAM_HAS_EMPTY_QUESTION("question", "The exam has empty question"),
    QUESTION_HAS_LESS_ANSWERS("answers", "The exam has question which has number of answers less then " + Constants.MIN_ANSWERS),
    QUESTION_INVALID_CORRECT("correct", "The question has invalid correct number"),
    ANSWER_IS_NULL("answer", "Field answer mustn't be null"),
    QUESTIONS_IS_NOT_EXIST("question", "Questions isn't exist"),
    EXAM_IS_READY("ready", "Exam has ready condition"),
    EXAM_NOT_TAKEN("exam", "Exam has not yet begun"),
    NO_READY_EXAM_FOR_THIS_SEMESTER("semester", "No ready exam for this semester"),
    QUESTIONS_NUMBERS_NOT_VALID("number", "Question's numbers must be either all zero or all different"),
    NOT_VALID_QUESTION_NUMBER("number", "Question's number mustn't be negative"),
    WORD_IS_TOO_LONG("", "%s length more than " + Constants.MAX_NAME_LENGTH),
    EXAM_ALREADY_PASSED("exam", "Exam already passed"),
    EXAM_ALREADY_EXISTS("exam", "Exam already exists"),
    EXAM_TIME_IS_UP("time", "The time is up"),
    EXAM_ANSWERS_COUNT_LESS_THAN_ALLOWED("answers", "Number of answers less than " + Constants.MIN_ANSWERS),
    EXAM_QUESTIONS_COUNT_LESS_THAN_ALLOWED("questionsCountPerExam", "Field questionsCountPerExam less than " +
            Constants.MIN_QUESTIONS_COUNT_PER_EXAM),
    EXAM_TIME_LESS_THAN_ALLOWED("timeInMinutes", "Field timeInMinutes less than " + Constants.MIN_TIME),
    PASSWORD_WRONG_LENGTH("password", "The password must be no longer than " + Constants.MAX_NAME_LENGTH +
            " and no less than " + Constants.MIN_PASSWORD_LENGTH),
    ACCESS_IS_DENIED("access", "Access is denied"),
    NULL_REQUEST_BODY("json", "Request body is null"),
    SOMETHING_WENT_WRONG("server", " hmmmm... Something went wrong"),
    INVALID_LOGIN("login", "Field login must consist of russian or english characters and can contain digits"),
    INVALID_FIRSTNAME("firstName", "Field firstName must consist of russian characters and can contain one hyphen"),
    INVALID_LASTNAME("lastName", "Field lastName must consist of russian characters and can contain one hyphen"),
    INVALID_PATRONYMIC("patronymic", "Field patronymic must consist of russian characters and can contain one hyphen"),
    INVALID_FILTER("filter", "Invalid filter. The available filters: ALL | PASSED | REMAINING | CURRENT");

    private String field;
    private String message;

    ErrorCode(String field, String message) {
        this.field = field;
        this.message = message;
    }

    public String getField() {
        return field;
    }

    public String getMessage() {
        return message;
    }

}
