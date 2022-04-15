package io.github.unhurried.example.backend.spring.config

import org.glassfish.jersey.server.ResourceConfig
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.config.BeanDefinition
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider
import org.springframework.core.type.filter.AnnotationTypeFilter
import org.springframework.stereotype.Component
import java.util.function.Consumer
import javax.ws.rs.ApplicationPath
import javax.ws.rs.Path
import javax.ws.rs.ext.Provider

/** Configurations for Jersey (JAX-RS)  */
// ResourceConfig must be a Spring component. (@Named or @Component)
@Component
// Specify a base path of all APIs.
@ApplicationPath("/")
class JerseyConfig : ResourceConfig() {
    companion object {
        private val log = LoggerFactory.getLogger(this::class.java)
    }
    init {
        // packages(), that can be used to look for components doesn't work when running application with a fat jar
        // because it assumes .class files of component classes can be accessed via file system.
        // To register resource and provider classes dynamically, scan class path manually and retrieve their class names.
        val provider = ClassPathScanningCandidateComponentProvider(false)
        provider.addIncludeFilter(AnnotationTypeFilter(Path::class.java))
        provider.addIncludeFilter(AnnotationTypeFilter(Provider::class.java))
        provider.findCandidateComponents("io/github/unhurried/example/backend/spring").forEach(Consumer { beanDefinition: BeanDefinition ->
            try {
                log.info("Registering {} to jersey config.", beanDefinition.beanClassName)
                register(Class.forName(beanDefinition.beanClassName))
            } catch (e: ClassNotFoundException) {
                log.warn("Failed to register {} to jersey confing.", beanDefinition.beanClassName)
            }
        })
    }
}