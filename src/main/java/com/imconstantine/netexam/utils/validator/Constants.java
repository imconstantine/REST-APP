package com.imconstantine.netexam.utils.validator;

import java.util.regex.Pattern;

public class Constants {

    public final static int MAX_NAME_LENGTH = Integer.parseInt(System.getProperty("max_name_length"));
    public final static int MIN_PASSWORD_LENGTH = Integer.parseInt(System.getProperty("min_password_length"));
    public final static int MIN_ANSWERS = Integer.parseInt(System.getProperty("min_answers"));
    public final static int MIN_QUESTIONS_COUNT_PER_EXAM = Integer.parseInt(System.getProperty("min_questions_count_per_exam"));
    public final static int MIN_TIME = Integer.parseInt(System.getProperty("min_time"));

    final static Pattern VALID_NAME_WORD = Pattern.compile("^[А-Яа-я][а-я]+(-|\\s)?[А-Яа-я][а-я]+$");
    final static Pattern VALID_LOGIN = Pattern.compile("^([А-ЯЁа-яё0-9]+|[a-z0-9]+)$");

    private Constants() {}

}
