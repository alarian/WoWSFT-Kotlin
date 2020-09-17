package WoWSFT

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer
import org.springframework.context.annotation.Bean
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor

@SpringBootApplication
class Application : SpringBootServletInitializer() {
    @Bean
    fun executor(): ThreadPoolTaskExecutor {
        val executor = ThreadPoolTaskExecutor()
        executor.setCorePoolSize(8)
        executor.setThreadNamePrefix("Thread-")
        executor.initialize()

        return executor
    }
}

fun main(args: Array<String>) {
    runApplication<Application>(*args)
}