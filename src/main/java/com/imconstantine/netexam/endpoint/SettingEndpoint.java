package com.imconstantine.netexam.endpoint;

import com.imconstantine.netexam.exception.NetExamException;
import com.imconstantine.netexam.dto.response.SettingsDtoResponse;
import com.imconstantine.netexam.service.SettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping(value = "/api/settings")
public class SettingEndpoint {

    private SettingService service;

    @Autowired
    public SettingEndpoint(SettingService settingService) {
        this.service = settingService;
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public SettingsDtoResponse getSettings(@CookieValue("JAVASESSIONID") String token) throws NetExamException, IOException {
        return service.getSettings(token);
    }

}
