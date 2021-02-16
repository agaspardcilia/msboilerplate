package fr.agaspardcilia.msboilerplate.authentication.mail

data class MailDto (
    val from: String,
    val to: String,
    val subject: String,
    val content: String
)
