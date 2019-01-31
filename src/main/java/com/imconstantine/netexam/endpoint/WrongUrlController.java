package com.imconstantine.netexam.endpoint;

import com.imconstantine.netexam.exception.NetExamException;
import com.imconstantine.netexam.utils.ErrorCode;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
public class WrongUrlController implements ErrorController {

    @RequestMapping("/error")
    @ResponseBody
    public void handleError() throws NetExamException {
        throw new NetExamException(ErrorCode.WRONG_URL);
    }

    @Override
    public String getErrorPath() {
        return null;
    }
}
