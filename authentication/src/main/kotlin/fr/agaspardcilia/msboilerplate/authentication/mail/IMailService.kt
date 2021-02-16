package fr.agaspardcilia.msboilerplate.authentication.mail

import org.springframework.scheduling.annotation.Async

interface IMailService {

    @Async
    fun sendMail(mail: MailDto)
}
