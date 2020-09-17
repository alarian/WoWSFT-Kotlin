package WoWSFT.config

import com.fasterxml.jackson.databind.MappingJsonFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.http.converter.HttpMessageConverter
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport

@Configuration
@ComponentScan("WoWSFT")
class WebConfig : WebMvcConfigurationSupport() {
//    @Bean
//    fun viewResolver(): ViewResolver
//    {
//        val resolver = InternalResourceViewResolver()
//        resolver.setPrefix("/templates/")
//        resolver.setSuffix(".html")
//        resolver.setExposeContextBeansAsAttributes(true)
//        return resolver
//    }

    override fun configureMessageConverters(converters: MutableList<HttpMessageConverter<*>>) {
        converters.add(customMJ2HMC())
        addDefaultHttpMessageConverters(converters)
    }

    @Bean
    fun createObjectMapper(): CustomObjectMapper = CustomObjectMapper(MappingJsonFactory())

    @Bean
    fun customMJ2HMC(): CustomMJ2HMC = CustomMJ2HMC().also { it.objectMapper = CustomObjectMapper(MappingJsonFactory()) }

    override fun addResourceHandlers(registry: ResourceHandlerRegistry) {
        registry
            .addResourceHandler("/**")
            .addResourceLocations("classpath:/static/")
    }

    override fun configureDefaultServletHandling(configurer: DefaultServletHandlerConfigurer) {
        configurer.enable()
    }
}