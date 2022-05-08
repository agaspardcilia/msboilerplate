package fr.agaspardcilia.msboilerplate.gateway.config

import org.slf4j.LoggerFactory
import org.springframework.boot.web.server.WebServerFactory
import org.springframework.boot.web.server.WebServerFactoryCustomizer
import org.springframework.boot.web.servlet.ServletContextInitializer
import org.springframework.context.annotation.Configuration
import org.springframework.core.env.Environment
import javax.servlet.ServletContext
import javax.servlet.ServletException

@Configuration
class WebConfigurer(
        private val env: Environment
) : ServletContextInitializer, WebServerFactoryCustomizer<WebServerFactory> {

    private val log = LoggerFactory.getLogger(javaClass)

    @Throws(ServletException::class)
    override fun onStartup(servletContext: ServletContext) {
        if (env.activeProfiles.isNotEmpty()) {
            log.info("Web application configuration, using profiles: {}", *env.activeProfiles as Array<*>)
        }
        log.info("Web application fully configured")
    }

    override fun customize(factory: WebServerFactory?) {
    }
}
