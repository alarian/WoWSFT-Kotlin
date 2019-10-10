package wowsft.controller

import org.apache.commons.lang3.StringUtils
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import wowsft.config.CustomMessage
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseBody

import javax.servlet.http.HttpServletRequest

import wowsft.model.Constant.GENERAL_INTERNAL_ERROR

open class ExceptionController
{
    companion object {
        private val log = LoggerFactory.getLogger(ExceptionController::class.java)
    }

    @ResponseBody
    @ExceptionHandler(Exception::class)
    fun otherErrors(t: Throwable, request: HttpServletRequest): CustomMessage
    {
        log.info(request.requestURL.toString() + if (StringUtils.isNotEmpty(request.queryString)) "?" + request.queryString else "")
        log.error(t.localizedMessage, t)

        return CustomMessage("1001", GENERAL_INTERNAL_ERROR)
    }
}
