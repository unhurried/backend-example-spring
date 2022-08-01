package io.github.unhurried.example.backend.spring.helper

import org.springframework.context.ApplicationContext
import org.springframework.context.ApplicationContextAware
import org.springframework.stereotype.Component

/** A helper class to get beans from Spring's ApplicationContext statically. */
@Component
class GetBeanHelper : ApplicationContextAware {
    companion object {
        lateinit var ctx: ApplicationContext
        fun <T> getBean(requiredType: Class<T>) : T {
            return ctx.getBean(requiredType)
        }
    }

    override fun setApplicationContext(applicationContext: ApplicationContext) {
        ctx = applicationContext
    }
}
