package net.yewton.asobiba.spring.boot.common

import net.yewton.asobiba.spring.boot.common.Logging.Extensions.logger
import org.springframework.core.env.ConfigurableEnvironment

/**
 * プロパティが意図した値に設定されているかな？を
 * 簡易に確認出来るように、指定されたキーの値をログに吐く君
 */
class PropertyChecker(private val environment: ConfigurableEnvironment) : Logging {

    fun perform(keys: List<String>) {
        environment.propertySources.forEach {
            logger.info("{}: {}", it.name, it.source)
        }
        keys.forEach {
            logger.info("$it: {}", environment.getProperty(it))
        }
    }
}
