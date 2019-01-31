package com.imconstantine.netexam.utils;

import com.imconstantine.netexam.dto.response.*;
import com.imconstantine.netexam.exception.NetExamException;
import com.imconstantine.netexam.dto.request.ExamDtoRequest;
import com.imconstantine.netexam.dto.request.QuestionDtoRequest;
import com.imconstantine.netexam.dto.request.StudentSignUpDtoRequest;
import com.imconstantine.netexam.dto.request.TeacherSignUpDtoRequest;
import com.imconstantine.netexam.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConverterUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(ConverterUtils.class);

    public static Exam convertToModel(ExamDtoRequest dto) {
        return new Exam(dto.getName(),
                dto.getSemester(),
                false);
    }

    public static ExamDtoResponse convertToDtoResponse(Exam model) {
        return new ExamDtoResponse(model.getId(),
                model.getExamName(),
                model.getSemester());
    }

    public static Teacher convertToModel(TeacherSignUpDtoRequest dto) {
        return new Teacher(dto.getFirstName(),
                dto.getLastName(),
                dto.getPatronymic(),
                dto.getLogin(),
                dto.getPasswordHashKey(),
                dto.getDepartment(),
                dto.getPosition(),
                UserType.TEACHER);
    }

    public static TeacherDtoResponse convertToDtoResponse(Teacher model) {
        return new TeacherDtoResponse(model.getFirstName(),
                model.getLastName(),
                model.getPatronymic(),
                model.getDepartment(),
                model.getPosition(),
                model.getType().toString());
    }

    public static Student convertToModel(StudentSignUpDtoRequest dto) {
        return new Student(dto.getFirstName(),
                dto.getLastName(),
                dto.getPatronymic(),
                dto.getLogin(),
                dto.getHashKey(),
                dto.getGroup(),
                dto.getSemester(),
                UserType.STUDENT);
    }

    public static StudentDtoResponse convertToDtoResponse(Student model) {
        return new StudentDtoResponse(model.getFirstName(),
                model.getLastName(),
                model.getPatronymic(),
                model.getGroup(),
                model.getSemester(),
                model.getType().toString());
    }

    public static List<Question> convertToModel(List<QuestionDtoRequest> dtoList) {
        List<Question> questionList = new ArrayList<>();
        Question question;
        for (QuestionDtoRequest dtoItem : dtoList) {
            question = new Question();
            question.setNumber(dtoItem.getNumber());
            question.setContent(dtoItem.getQuestion());
            question.setCorrect(dtoItem.getCorrect());
            if (dtoItem.getAnswers() != null) {
                for (String answer : dtoItem.getAnswers()) {
                    question.getAnswerList().add(new Answer(answer));
                }
            }
            questionList.add(question);
        }
        return questionList;
    }

    public static ExamQuestionsDtoResponse convertToDtoResponse(List<Question> questionList) {
        List<QuestionDtoResponse> dtoResponseList = new ArrayList<>();
        QuestionDtoResponse questionDtoResponse;
        for (Question question : questionList) {
            questionDtoResponse = new QuestionDtoResponse();
            questionDtoResponse.setId(question.getId());
            questionDtoResponse.setNumber(question.getNumber());
            questionDtoResponse.setQuestion(question.getContent());
            questionDtoResponse.setCorrect(question.getCorrect());
            for (Answer answer : question.getAnswerList()) {
                questionDtoResponse.getAnswerList().add(answer.getBody());
            }
            dtoResponseList.add(questionDtoResponse);
        }
        return new ExamQuestionsDtoResponse(dtoResponseList);
    }

    public static ExamQuestionsDtoResponse convertQuestionsToDtoResponse(List<Question> questionList) {
        List<QuestionDtoResponse> dtoResponseList = new ArrayList<>();
        QuestionDtoResponse questionDtoResponse;
        for (Question question : questionList) {
            questionDtoResponse = new QuestionDtoResponse();
            questionDtoResponse.setId(question.getId());
            questionDtoResponse.setNumber(question.getNumber());
            questionDtoResponse.setQuestion(question.getContent());
            questionDtoResponse.setCorrect(question.getCorrect());
            for (Answer answer : question.getAnswerList()) {
                questionDtoResponse.getAnswerList().add(answer.getBody());
            }
            dtoResponseList.add(questionDtoResponse);
        }
        return new ExamQuestionsDtoResponse(dtoResponseList);
    }

    public static ExamSetStateDtoResponse convertToStateDtoResponse(Exam exam) {
        return new ExamSetStateDtoResponse(exam.getQuestionsCountPerExam(), exam.getTimeInMinutes());
    }

    public static ExamParamDtoResponse convertToParamDtoResponse(Exam exam) {
        return new ExamParamDtoResponse(exam.getExamName(),
                exam.getId(),
                exam.getSemester(),
                exam.isReady(),
                exam.getQuestionsCountPerExam(),
                exam.getTimeInMinutes(),
                exam.getShowDetails());
    }

    public static ExamsParamDtoResponse convertToParamDtoResponse(List<Exam> models) {

        List<ExamParamDtoResponse> dtoResponses = new ArrayList<>();
        for (Exam exam : models) {
            dtoResponses.add(convertToParamDtoResponse(exam));
        }
        return new ExamsParamDtoResponse(dtoResponses);
    }

    public static ExamsAndTeachersDtoResponse converToDtoResponse(List<ExamInfo> models) {
        List<ExamAndTeacherDtoResponse> dtoResponses = new ArrayList<>();
        for (ExamInfo examInfo : models) {
            dtoResponses.add(new ExamAndTeacherDtoResponse(examInfo.getId(), examInfo.getName(), examInfo.getFirstName(),
                    examInfo.getLastName(), examInfo.getPatronymic(), examInfo.getDepartment(), examInfo.getPosition(),
                    examInfo.getQuestionsCountPerExam(), examInfo.getTimeInMinutes()));
        }
        return new ExamsAndTeachersDtoResponse(dtoResponses);
    }

    public static ExamForSolutionDtoResponse convertToResponse(Exam exam, Teacher teacher) {
        TeacherDtoResponse teacherDtoResponse = new TeacherDtoResponse(teacher.getFirstName(),
                teacher.getLastName(), teacher.getPatronymic(), teacher.getDepartment(), teacher.getPosition());

        return new ExamForSolutionDtoResponse(new ExamAndTeacherDtoResponse(exam.getId(), exam.getExamName(),
                teacherDtoResponse, exam.getTimeInMinutes()),
                convertToDtoResponse(exam.getQuestions()).getQuestionDtoResponses());
    }

    public static StudentExamResultDtoResponse convertToDtoResponse(Exam exam, int correctCounter, List<AnswerType> details) {
        StudentExamResultDtoResponse dtoResponse = new StudentExamResultDtoResponse();
        dtoResponse.setQuestionsCount(exam.getQuestionsCountPerExam());
        dtoResponse.setCorrect(correctCounter);
        if (exam.getShowDetails()) {
            dtoResponse.setDetails(details);
        }
        return dtoResponse;
    }

    public static byte[] convertObjectToByteArray(Object object) throws NetExamException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try (ObjectOutputStream oos = new ObjectOutputStream(baos)) {
            oos.writeObject(object);
            return baos.toByteArray();
        } catch (IOException exception) {
            LOGGER.info("Can't convert object {} ", object, exception);
            throw new NetExamException(ErrorCode.SOMETHING_WENT_WRONG);
        }
    }

    public static List<AnswerType> convertByteArrayToModel(byte[] byteArray) {
        List<AnswerType> list = null;
        try (ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(byteArray))) {
            list = (List<AnswerType>) ois.readObject();
        } catch (IOException e) {
            LOGGER.info("", e);
        } catch (ClassNotFoundException e) {
            LOGGER.info("", e);
        }
        return list;
    }

    public static ExamStudentsResult convertModelToDto(Exam exam, List<Group> groups) {
        ExamStudentsResult examStudentsResult = new ExamStudentsResult();
        examStudentsResult.setName(exam.getExamName());
        Map<String, GroupDto> groupsDto = new HashMap<>();
        List<StudentAndResultDto> studentsDto;
        ResultDto resultDto;
        for (Group group : groups) {
            studentsDto = new ArrayList<>();
            for (Student student : group.getStudents()) {
                resultDto = ResultCounterProcess.count(student.getResult());
                studentsDto.add(new StudentAndResultDto(student.getFirstName(), student.getLastName(), student.getPatronymic(),
                        resultDto.getCorrect(), resultDto.getWrong(), resultDto.getNoAnswer()));
            }
            groupsDto.put(group.getName(), new GroupDto(studentsDto));
        }
        examStudentsResult.setGroups(groupsDto);
        return examStudentsResult;
    }

    public static StudentExamResultListDtoResponse convertModelToDtoResponse(List<ExamInfo> examInfoList) {
        List<StudentResultMainDtoResponse> dtoResponse = new ArrayList<>();
        StudentResultMainDtoResponse studentResultMainDtoResponse;
        ResultDto resultDto;
        StudentExamResultDtoResponse resultDtoResponse;
        for (ExamInfo examInfo : examInfoList) {
            studentResultMainDtoResponse = new StudentResultMainDtoResponse();
            resultDto = ResultCounterProcess.count(examInfo.getResult());
            resultDtoResponse = new StudentExamResultDtoResponse(examInfo.getQuestionsCountPerExam(), resultDto.getCorrect(),
                    examInfo.isShowDetails() ? examInfo.getResult() : examInfo.getResult());
            studentResultMainDtoResponse.setResult(resultDtoResponse);
            studentResultMainDtoResponse.setId(examInfo.getId());
            studentResultMainDtoResponse.setName(examInfo.getName());
            studentResultMainDtoResponse.setTeacher(new TeacherDtoResponse(examInfo.getFirstName(), examInfo.getLastName(),
                    examInfo.getPatronymic(), examInfo.getDepartment(), examInfo.getPosition()));
            dtoResponse.add(studentResultMainDtoResponse);
        }
        return new StudentExamResultListDtoResponse(dtoResponse);
    }

}
