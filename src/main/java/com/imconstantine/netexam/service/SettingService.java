package com.imconstantine.netexam.service;

import com.imconstantine.netexam.database.dao.UserDao;
import com.imconstantine.netexam.database.daoimpl.UserDaoImpl;
import com.imconstantine.netexam.exception.NetExamException;
import com.imconstantine.netexam.dto.response.SettingsDtoResponse;
import com.imconstantine.netexam.model.UserType;
import com.imconstantine.netexam.utils.SessionOperationsUtils;
import com.imconstantine.netexam.utils.validator.Constants;
import org.springframework.stereotype.Service;

@Service
public class SettingService {

    private UserDao userDao = new UserDaoImpl();

    public SettingsDtoResponse getSettings(String token) throws NetExamException {
        SettingsDtoResponse settingsDtoResponse = new SettingsDtoResponse();
        settingsDtoResponse.setMaxNameLength(Constants.MAX_NAME_LENGTH);
        settingsDtoResponse.setMinPasswordLength(Constants.MIN_PASSWORD_LENGTH);
        try {
            SessionOperationsUtils.isTokenValid(token);
            UserType userType = userDao.getUserTypeByToken(token);
            if (userType == UserType.TEACHER) {
                settingsDtoResponse.setMinAnswers(Constants.MIN_ANSWERS);
                settingsDtoResponse.setMinQuestionsCountPerExam(Constants.MIN_QUESTIONS_COUNT_PER_EXAM);
                settingsDtoResponse.setMinTime(Constants.MIN_TIME);
            }
            return settingsDtoResponse;
        } catch (NetExamException exception) {
            return settingsDtoResponse;
        }
    }

}
