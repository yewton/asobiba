package net.yewton.asobiba.nanka.web

open class SpecificBadRequestError : BadRequestError {
    constructor(message: String) : super(message)
    constructor(cause: Throwable) : super("やんごとなき理由", cause)
}
