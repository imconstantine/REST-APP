package com.imconstantine.netexam.endpoint;

import com.imconstantine.netexam.exception.NetExamException;
import com.imconstantine.netexam.dto.request.LoginDtoRequest;
import com.imconstantine.netexam.dto.response.UserDto;
import com.imconstantine.netexam.service.LoggingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.MediaType;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@RequestMapping(value = "/api/session")
public class LoggingEndpoint {

    private LoggingService service;

    @Autowired
    public LoggingEndpoint(LoggingService loggingService) {
        service = loggingService;
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public UserDto getSession(HttpServletResponse response, @Valid @RequestBody LoginDtoRequest loginDto) throws NetExamException {
        return service.logIn(response, loginDto);
    }

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public String dropSession(HttpServletResponse response, @CookieValue("JAVASESSIONID") String token) throws NetExamException {
        return service.logOut(response, token);
    }

}
