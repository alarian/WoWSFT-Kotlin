package WoWSFT.controller

import WoWSFT.config.CustomMessage
import WoWSFT.model.Constant.GENERAL_INTERNAL_ERROR
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseBody
import javax.servlet.http.HttpServletRequest

open class ExceptionController
{
    companion object {
        private val log = LoggerFactory.getLogger(ExceptionController::class.java)
    }

    @ResponseBody
    @ExceptionHandler(Exception::class)
    fun otherErrors(t: Throwable, request: HttpServletRequest): CustomMessage
    {
        log.info(request.requestURL.toString() + (if (!request.queryString.isNullOrBlank()) "?${request.queryString}" else ""))
        log.error(t.localizedMessage, t)

        return CustomMessage("1001", GENERAL_INTERNAL_ERROR)
    }
}