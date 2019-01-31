package com.imconstantine.netexam.endpoint;

import com.imconstantine.netexam.exception.NetExamException;
import com.imconstantine.netexam.dto.request.TeacherSignUpDtoRequest;
import com.imconstantine.netexam.dto.request.TeacherUpdateDtoRequest;
import com.imconstantine.netexam.dto.response.TeacherDtoResponse;
import com.imconstantine.netexam.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@RequestMapping(value = "/api/teacher")
public class TeacherEndpoint  {

    private TeacherService service;

    @Autowired
    public TeacherEndpoint(TeacherService teacherService) {
        this.service = teacherService;
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping(consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public TeacherDtoResponse teacherSignUp(HttpServletResponse response,
                                            @Valid @RequestBody TeacherSignUpDtoRequest teacherDto) throws NetExamException {
        return service.signUp(response, teacherDto);
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public TeacherDtoResponse teacherUpdate(@CookieValue("JAVASESSIONID") String token,
                                            @RequestBody TeacherUpdateDtoRequest teacherDto) throws NetExamException {
        return service.update(token, teacherDto);
    }

}
