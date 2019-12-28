package WoWSFT

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer
import org.springframework.context.annotation.Bean
import org.springframework.core.io.ClassPathResource
import org.springframework.scheduling.annotation.EnableAsync
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor
import WoWSFT.model.Constant
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

    @Bean(Constant.TYPE_SHIP)
    fun zShip(): ZipFile
    {
        return ZipFile(ClassPathResource("/json/live/files.zip").file.path)
    }

    @Bean(Constant.TYPE_SHELL)
    fun zShell(): ZipFile
    {
        return ZipFile(ClassPathResource("/json/live/shells.zip").file.path)
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>)
        {
            runApplication<Application>(*args)
        }
    }
}