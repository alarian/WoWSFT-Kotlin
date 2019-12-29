package WoWSFT

import WoWSFT.model.Constant.JSON_PARSER
import WoWSFT.parser.JsonParser
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer
import org.springframework.context.annotation.Bean
import org.springframework.scheduling.annotation.EnableAsync
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor
import java.util.*
import javax.annotation.PostConstruct

@EnableAsync
@SpringBootApplication
class Application : SpringBootServletInitializer()
{
    @PostConstruct
    fun started()
    {
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"))
    }

    @Bean
    fun executor(): ThreadPoolTaskExecutor
    {
        val executor = ThreadPoolTaskExecutor()
        executor.corePoolSize = 5
        executor.setThreadNamePrefix("Thread-")
        executor.initialize()

        return executor
    }

    @Bean(JSON_PARSER)
    fun jsonParser(): JsonParser
    {
        return JsonParser()
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>)
        {
            runApplication<Application>(*args)
        }
    }
}