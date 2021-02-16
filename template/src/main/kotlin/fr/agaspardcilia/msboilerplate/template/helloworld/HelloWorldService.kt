package fr.agaspardcilia.msboilerplate.template.helloworld

import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class HelloWorldService {

    fun getHelloWorldMessage(): String = "Hello world!"

    fun now(): LocalDateTime = LocalDateTime.now()

}
