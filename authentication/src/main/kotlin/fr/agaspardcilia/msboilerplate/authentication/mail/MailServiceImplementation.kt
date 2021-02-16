package fr.agaspardcilia.msboilerplate.authentication.mail

import fr.agaspardcilia.msboilerplate.authentication.mail.IMailService
import fr.agaspardcilia.msboilerplate.authentication.mail.MailDto
import org.springframework.context.annotation.Profile
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.stereotype.Service

@Profile("mail")
@Service
class MailServiceImplementation(
    val javaMailSender: JavaMailSender
) : IMailService {

    override fun sendMail(mail: MailDto) {
        TODO("Not yet implemented")
    }

}
