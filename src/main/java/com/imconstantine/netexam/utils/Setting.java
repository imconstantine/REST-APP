package com.imconstantine.netexam.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Setting {

    private static final Logger LOGGER = LoggerFactory.getLogger(Setting.class);

    private static final String MAX_NAME_LENGTH = "max_name_length";
    private static final String MIN_PASSWORD_LENGTH = "min_password_length";
    private static final String MIN_ANSWERS = "min_answers";
    private static final String MIN_QUESTIONS_COUNT_PER_EXAM = "min_questions_count_per_exam";
    private static final String MIN_TIME = "min_time";

    public static boolean loadConfigFile(String path) {
        try (FileInputStream fis = new FileInputStream(path)) {
            Properties property = new Properties();
            property.load(fis);
            System.setProperty("server.port", property.getProperty("rest_http_port"));
            System.setProperty(MAX_NAME_LENGTH, property.getProperty(MAX_NAME_LENGTH));
            System.setProperty(MIN_PASSWORD_LENGTH, property.getProperty(MIN_PASSWORD_LENGTH));
            System.setProperty(MIN_ANSWERS, property.getProperty(MIN_ANSWERS));
            System.setProperty(MIN_QUESTIONS_COUNT_PER_EXAM, property.getProperty(MIN_QUESTIONS_COUNT_PER_EXAM));
            System.setProperty(MIN_TIME, property.getProperty(MIN_TIME));
            return true;
        } catch (IOException exception) {
            LOGGER.info("Can't load settings from config file ", exception);
            return false;
        }
    }


}
