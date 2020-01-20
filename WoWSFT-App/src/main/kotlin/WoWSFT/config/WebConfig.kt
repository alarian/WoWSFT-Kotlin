package WoWSFT.config

import com.fasterxml.jackson.databind.MappingJsonFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.http.converter.HttpMessageConverter
import org.springframework.web.servlet.ViewResolver
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport
import org.springframework.web.servlet.view.InternalResourceViewResolver

@Configuration
@ComponentScan("WoWSFT")
class WebConfig : WebMvcConfigurationSupport()
{
//    @Bean
//    fun viewResolver(): ViewResolver
//    {
//        val resolver = InternalResourceViewResolver()
//        resolver.setPrefix("/templates/")
//        resolver.setSuffix(".html")
//        resolver.setExposeContextBeansAsAttributes(true)
//        return resolver
//    }

    override fun configureMessageConverters(converters: MutableList<HttpMessageConverter<*>>)
    {
        converters.add(customMJ2HMC())
        addDefaultHttpMessageConverters(converters)
    }

    @Bean
    fun createObjectMapper(): CustomObjectMapper
    {
        return CustomObjectMapper(MappingJsonFactory())
    }

    @Bean
    fun customMJ2HMC(): CustomMJ2HMC
    {
        val customMJ2HMC = CustomMJ2HMC()
        customMJ2HMC.objectMapper = CustomObjectMapper(MappingJsonFactory())
        return customMJ2HMC
    }

    public override fun addResourceHandlers(registry: ResourceHandlerRegistry)
    {
        registry.addResourceHandler("/**")
            .addResourceLocations("classpath:/static/")
    }

    public override fun configureDefaultServletHandling(configurer: DefaultServletHandlerConfigurer)
    {
        configurer.enable()
    }
}