package fr.agaspardcilia.msboilerplate.template.helloworld

import org.springframework.stereotype.Service
import java.time.Instant

@Service
class HelloWorldService {

    fun getHelloWorldMessage(): String = "Hello world!"

    fun now(): Instant = Instant.now()

}
