package fr.agaspardcilia.msboilerplate.template.config

import org.springframework.context.annotation.Configuration
import org.springframework.format.FormatterRegistry
import org.springframework.format.datetime.standard.DateTimeFormatterRegistrar
import org.springframework.web.servlet.config.annotation.EnableWebMvc
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class TemplateWebMVCConfigurer(private val templatePermissionInterceptor: TemplatePermissionInterceptor)
    : WebMvcConfigurer {

    override fun addInterceptors(registry: InterceptorRegistry) {
        registry.addInterceptor(templatePermissionInterceptor)
            .excludePathPatterns(
                "/actuator/**",
                "/error"
            )
    }

    override fun addFormatters(registry: FormatterRegistry) {
        val registrar =  DateTimeFormatterRegistrar()
        registrar.setUseIsoFormat(true)
        registrar.registerFormatters(registry)
    }
}
