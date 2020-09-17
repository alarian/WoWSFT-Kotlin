package WoWSFT

import WoWSFT.model.Constant.TYPE_SHELL
import WoWSFT.model.Constant.TYPE_SHIP
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer
import org.springframework.context.annotation.Bean
import org.springframework.core.io.ClassPathResource
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor
import java.util.*
import javax.annotation.PostConstruct

@SpringBootApplication
class Application : SpringBootServletInitializer() {
    @PostConstruct
    fun started() {
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"))
    }

    @Bean
    fun executor(): ThreadPoolTaskExecutor {
        val executor = ThreadPoolTaskExecutor()
        executor.setCorePoolSize(5)
        executor.setThreadNamePrefix("Thread-")
        executor.initialize()

        return executor
    }

    @Bean(TYPE_SHIP)
    fun zShip(): String = ClassPathResource("/json/live/files.zip").file.path

    @Bean(TYPE_SHELL)
    fun zShell(): String = ClassPathResource("/json/live/shells.zip").file.path
}

fun main(args: Array<String>) {
    runApplication<Application>(*args)
}