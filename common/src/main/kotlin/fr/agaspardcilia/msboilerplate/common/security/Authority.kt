package fr.agaspardcilia.msboilerplate.common.security

import fr.agaspardcilia.msboilerplate.common.security.Permission.*

enum class Authority(vararg val permissions: Permission) {
    ADMIN(
        AUTHENTICATION_USER_GET_ALL,
    ),
    USER(
        AUTHENTICATION_USER_GET,
        AUTHENTICATION_USER_GET_SELF,

        NOTES_GET,
        NOTES_CREATE,
        NOTES_UPDATE,
        NOTES_DELETE,

        TEMPLATE_NOW,
        TEMPLATE_HELLO_WORLD
    ),
    ROLE_ANONYMOUS(
        AUTHENTICATION_JWT_AUTH,
        AUTHENTICATION_USER_REGISTER,
        AUTHENTICATION_USER_REQUEST_PWD_RESET,
        AUTHENTICATION_USER_RESET_PWD,
        AUTHENTICATION_USER_CHANGE_PWD,
        AUTHENTICATION_USER_ACTIVATE
    ),
}

