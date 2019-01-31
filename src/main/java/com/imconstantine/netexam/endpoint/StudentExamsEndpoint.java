package com.imconstantine.netexam.endpoint;

import com.imconstantine.netexam.dto.response.ExamForSolutionDtoResponse;
import com.imconstantine.netexam.dto.response.ExamsAndTeachersDtoResponse;
import com.imconstantine.netexam.dto.response.StudentExamResultDtoResponse;
import com.imconstantine.netexam.dto.response.StudentExamResultListDtoResponse;
import com.imconstantine.netexam.exception.NetExamException;
import com.imconstantine.netexam.service.StudentExamsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.DefaultValue;
import java.util.List;

@RestController
@RequestMapping(value = "/api/studentexams")
public class StudentExamsEndpoint {

    private StudentExamsService service;

    @Autowired
    public StudentExamsEndpoint(StudentExamsService service) {
        this.service = service;
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ExamsAndTeachersDtoResponse getExams(@CookieValue("JAVASESSIONID") String token,
                                                @RequestParam("filter") @Nullable @DefaultValue("ALL") String filter) throws NetExamException {
        return service.getExams(token, filter);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(path="/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ExamForSolutionDtoResponse getExamForSolution(@CookieValue("JAVASESSIONID") String token, @PathVariable("id") Integer id) throws NetExamException {
        return service.getExamForSolution(token, id);
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping(path = "/{id}/solutions")
    public StudentExamResultDtoResponse setSolution(@CookieValue("JAVASESSIONID") String token, @RequestBody List<Integer> solution, @PathVariable("id") Integer id) throws NetExamException {
        return service.setSolution(token, solution, id);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(path = "/solutions")
    public StudentExamResultListDtoResponse getResults(@CookieValue("JAVASESSIONID") String token) throws NetExamException {
        return service.getResults(token);
    }

}
