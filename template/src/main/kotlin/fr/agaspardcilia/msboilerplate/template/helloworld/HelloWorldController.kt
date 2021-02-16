package fr.agaspardcilia.msboilerplate.template.helloworld

import fr.agaspardcilia.msboilerplate.common.security.Permission
import fr.agaspardcilia.msboilerplate.common.security.annotations.PermissionRequired
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDateTime

@RestController
@RequestMapping("/helloworld")
class HelloWorldController(private val helloWorldService: HelloWorldService) {

    private val log = LoggerFactory.getLogger(javaClass)

    @GetMapping
    @PermissionRequired(Permission.TEMPLATE_HELLO_WORLD)
    fun get(): String {
        log.info("Getting the hello world message")
        return helloWorldService.getHelloWorldMessage()
    }

    @GetMapping("/now")
    @PermissionRequired(Permission.TEMPLATE_NOW)
    fun now(): LocalDateTime {
        log.info("Getting current time")
        return helloWorldService.now()
    }
}
