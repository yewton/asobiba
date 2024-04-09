package net.yewton.asobiba.nanka.web

import org.springframework.http.HttpStatus
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus

@ControllerAdvice
class ErrorHandlerAdvice {

    @ExceptionHandler(NormalError::class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    fun normalError(error: NormalError, model: Model): String {
        model.addAttribute("message", error.message)
        return "customerror/normal"
    }

    @ExceptionHandler(TeaPotError::class)
    fun teaPot(error: TeaPotError) {
        // これは 400 にはならず、root cause である TeaPotError のステータスが返る
        throw BadRequestError(error)
    }

    @ExceptionHandler(MoreSpecificBadRequestError::class)
    fun moreSpecificBadRequestError(error: MoreSpecificBadRequestError) {
        // TeaPotError と構造は同じで、 MoreSpecificBadRequestError が 400 なので 400 になる
        throw SpecificBadRequestError(error)
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(SpecificBadRequestError::class)
    fun specificBad(error: NormalError, model: Model): String {
        model.addAttribute("message", error.message)
        return "customerror/normal"
    }
}
