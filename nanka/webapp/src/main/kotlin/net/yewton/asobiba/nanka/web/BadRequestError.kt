package net.yewton.asobiba.nanka.web

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(HttpStatus.BAD_REQUEST)
open class BadRequestError : RuntimeException {
    constructor() : super("バッドですよ")
    constructor(message: String) : super(message)
    constructor(cause: Throwable) : super("バッドには理由が有る", cause)
    constructor(message: String, cause: Throwable) : super(message, cause)
}
