package com.imconstantine.netexam.endpoint;

import com.imconstantine.netexam.exception.NetExamException;
import com.imconstantine.netexam.service.DebugService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/debug")
public class DebugEndpoint {

    private DebugService service;

    @Autowired
    public DebugEndpoint(DebugService service) {
        this.service = service;
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(path = "/clear")
    public String clearDataBase() throws NetExamException {
        return service.clearDateBase();
    }

}
