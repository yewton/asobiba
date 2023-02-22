package net.yewton.asobiba.batista

import net.yewton.asobiba.batista.SecondTransactional.Companion.NAME
import org.springframework.transaction.annotation.Transactional

@Target(AnnotationTarget.CLASS, AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
@Transactional(NAME)
annotation class SecondTransactional {
    companion object {
        const val NAME = "secondTransactionManager"
    }
}
