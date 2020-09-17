package WoWSFT.config

import WoWSFT.model.Constant.GENERAL_INTERNAL_ERROR
import org.slf4j.LoggerFactory
import org.springframework.web.HttpRequestMethodNotSupportedException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import java.io.IOException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@ControllerAdvice
class CustomControllerAdvice {
    companion object {
        private val log = LoggerFactory.getLogger(CustomControllerAdvice::class.java)
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException::class)
    @Throws(IOException::class)
    fun handleRequestMethodException(t: Throwable?, request: HttpServletRequest, response: HttpServletResponse) {
        log.info(request.requestURL.toString() + (if (!request.queryString.isNullOrBlank()) "?${request.queryString}" else ""))
        response.setHeader("Content-Type", "application/json;charset=UTF-8")
        response.writer.write("{\"message\":\"$GENERAL_INTERNAL_ERROR\",\"status\":\"1002\"}")
    }
}