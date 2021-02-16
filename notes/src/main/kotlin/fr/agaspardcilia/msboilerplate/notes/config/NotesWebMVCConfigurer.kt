package fr.agaspardcilia.msboilerplate.notes.config

import org.springframework.context.annotation.Configuration
import org.springframework.format.FormatterRegistry
import org.springframework.format.datetime.standard.DateTimeFormatterRegistrar
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class NotesWebMVCConfigurer(private val permissionInterceptor: NotesPermissionInterceptor)
    : WebMvcConfigurer {

    override fun addInterceptors(registry: InterceptorRegistry) {
        registry.addInterceptor(permissionInterceptor)
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
