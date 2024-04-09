package net.yewton.asobiba.nanka.web

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(HttpStatus.I_AM_A_TEAPOT)
class TeaPotError : RuntimeException("一回ハンドリングされます")
