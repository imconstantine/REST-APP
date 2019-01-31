package com.imconstantine.netexam.endpoint;

import com.imconstantine.netexam.dto.response.*;
import com.imconstantine.netexam.exception.NetExamException;
import com.imconstantine.netexam.dto.request.ExamDtoRequest;
import com.imconstantine.netexam.dto.request.ExamSetStateDtoRequest;
import com.imconstantine.netexam.dto.request.ExamUpdateDtoRequest;
import com.imconstantine.netexam.dto.request.QuestionDtoRequest;
import com.imconstantine.netexam.service.ExamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/api/exams")
public class ExamEndpoint {

    private ExamService service;

    @Autowired
    public ExamEndpoint(ExamService examService) {
        service = examService;
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ExamDtoResponse examAdding(@CookieValue("JAVASESSIONID") String token,
                                      @Valid @RequestBody ExamDtoRequest examDtoRequest) throws NetExamException {
        if (examDtoRequest.getSourceId() == null) {
            return service.addExam(token, examDtoRequest);
        } else {
            return service.copyExam(token, examDtoRequest);
        }
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping(path = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ExamDtoResponse examEditing(@CookieValue("JAVASESSIONID") String token,
                                       @PathVariable("id") Integer id, @RequestBody ExamUpdateDtoRequest examDtoRequest) throws NetExamException {
        return service.updateExam(token, examDtoRequest, id);
    }

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public EmptyDtoResponse examDeleting(@CookieValue("JAVASESSIONID") String token, @PathVariable("id") Integer id) throws NetExamException {
        return service.deleteExam(token, id);
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping(path = "/{id}/questions", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ExamQuestionsDtoResponse examSetQuestions(@CookieValue("JAVASESSIONID") String token,
                                                     @PathVariable("id") Integer id,
                                                     @RequestBody List<QuestionDtoRequest> listOfQuestionsDto) throws NetExamException {
        return service.setQuestions(token, listOfQuestionsDto, id);
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping(path = "/{id}/state", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ExamSetStateDtoResponse examSetReadyState(@CookieValue("JAVASESSIONID") String token,
                                                     @PathVariable("id") Integer id,
                                                     @Valid @RequestBody ExamSetStateDtoRequest examSetStateDtoRequest) throws NetExamException {
        return service.setState(token, examSetStateDtoRequest, id);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(path = "/{id}/questions", produces = MediaType.APPLICATION_JSON_VALUE)
    public ExamQuestionsDtoResponse getExamByTeacher(@CookieValue("JAVASESSIONID") String token, @PathVariable("id") Integer id) throws NetExamException {
        return service.getQuestions(token, id);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ExamParamDtoResponse getExamParamsByTeacher(@CookieValue("JAVASESSIONID") String token, @PathVariable("id") Integer id) throws NetExamException {
        return service.getExamParam(token, id);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ExamsParamDtoResponse getListOfExamsParamsByTeacher(@CookieValue("JAVASESSIONID") String token,
                                                               @RequestParam("name") @Nullable String name,
                                                               @RequestParam("semester") @Nullable Integer semester,
                                                               @RequestParam("ready") @Nullable Boolean ready) throws NetExamException {
        return service.getExamsParams(token, name, semester, ready);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(path = "/{id}/students", produces = MediaType.APPLICATION_JSON_VALUE)
    public ExamStudentsResult getStudentsResults(@CookieValue("JAVASESSIONID") String token, @PathVariable("id") Integer id) throws NetExamException {
        return service.getStudentsResult(token, id);
    }

}
