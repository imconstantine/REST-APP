package com.imconstantine.netexam.endpoint;

import com.imconstantine.netexam.exception.NetExamException;
import com.imconstantine.netexam.dto.request.StudentSignUpDtoRequest;
import com.imconstantine.netexam.dto.request.StudentUpdateDtoRequest;
import com.imconstantine.netexam.dto.response.StudentDtoResponse;
import com.imconstantine.netexam.service.StudentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@RequestMapping(value = "/api/student")
public class StudentEndpoint {

    private static StudentService service;

    public StudentEndpoint(StudentService studentService) {
        service = studentService;
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public StudentDtoResponse studentSignUp(HttpServletResponse response,
                                            @Valid @RequestBody StudentSignUpDtoRequest studentDto) throws NetExamException {
        return service.signUp(response, studentDto);
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public StudentDtoResponse studentUpdate(@CookieValue("JAVASESSIONID") String token,
                                            @RequestBody StudentUpdateDtoRequest studentDto) throws NetExamException {
        return service.update(token, studentDto);
    }
}
