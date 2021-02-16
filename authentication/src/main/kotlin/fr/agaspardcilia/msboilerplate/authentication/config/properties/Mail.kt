package fr.agaspardcilia.msboilerplate.authentication.config.properties

class Mail {
    val server: Server = Server()

    class Server {
        lateinit var url: String
        lateinit var mailAddress: String
    }
}
