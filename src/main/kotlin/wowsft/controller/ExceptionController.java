package wowsft.controller;

import wowsft.config.CustomMessage;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

import static wowsft.model.Constant.GENERAL_INTERNAL_ERROR;

public class ExceptionController
{
    @ResponseBody
    @ExceptionHandler(Exception.class)
    public CustomMessage otherErrors(Throwable t, HttpServletRequest request)
    {
        log.info(request.getRequestURL().toString() + (StringUtils.isNotEmpty(request.getQueryString()) ? "?" + request.getQueryString() : ""));
        log.error(t.getLocalizedMessage(), t);

        return new CustomMessage("1001", GENERAL_INTERNAL_ERROR);
    }
}
