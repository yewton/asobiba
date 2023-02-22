package net.yewton.asobiba.batista

import net.yewton.asobiba.batista.FirstTransactional.Companion.NAME
import org.springframework.transaction.annotation.Transactional

@Target(AnnotationTarget.CLASS, AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
@Transactional(transactionManager = NAME, label = ["Yay"])
annotation class FirstTransactional {

    companion object {
        const val NAME = "firstTransactionManager"
    }
}
