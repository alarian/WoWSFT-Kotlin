package wowsft

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer
import org.springframework.context.annotation.Bean
import org.springframework.web.client.RestTemplate
import java.util.*
import javax.annotation.PostConstruct

@SpringBootApplication
class Application : SpringBootServletInitializer()
{
    @PostConstruct
    fun started()
    {
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"))
    }

    @Bean
    fun restTemplate() : RestTemplate
    {
        return RestTemplate()
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>)
        {
//            SpringApplication.run(Application::class.java, *args)
            runApplication<Application>(*args)
        }
    }
}