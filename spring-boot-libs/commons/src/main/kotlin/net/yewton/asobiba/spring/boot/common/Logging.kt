package net.yewton.asobiba.spring.boot.common

import org.slf4j.Logger
import org.slf4j.LoggerFactory

// https://www.baeldung.com/kotlin/logging
// https://dev.to/zakhi/logging-in-kotlin-made-easy-546h
interface Logging {
    companion object Extensions {
        inline val <reified T : Logging> T.logger: Logger get() = LoggerFactory.getLogger(T::class.java)
    }
}
