package WoWSFT

import WoWSFT.model.Constant.JSON_PARSER
import WoWSFT.model.Constant.TYPE_SHELL
import WoWSFT.model.Constant.TYPE_SHIP
import WoWSFT.parser.JsonParser
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer
import org.springframework.context.annotation.Bean
import org.springframework.core.io.ClassPathResource
import org.springframework.scheduling.annotation.EnableAsync
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor
import java.util.*
import java.util.zip.ZipFile
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

    @Bean(TYPE_SHIP)
    fun zShip(): ZipFile
    {
        return ZipFile(ClassPathResource("/json/live/files.zip").file.path)
    }

    @Bean(TYPE_SHELL)
    fun zShell(): ZipFile
    {
        return ZipFile(ClassPathResource("/json/live/shells.zip").file.path)
    }

    @Bean(JSON_PARSER)
    fun jsonParser(): JsonParser
    {
        return JsonParser()
    }
}

fun main(args: Array<String>)
{
    runApplication<Application>(*args)
}