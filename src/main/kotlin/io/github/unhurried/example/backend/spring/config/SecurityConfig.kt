package io.github.unhurried.example.backend.spring.config

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.core.env.Environment
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.oauth2.jwt.JwtDecoder
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder
import org.springframework.security.web.DefaultSecurityFilterChain
import org.springframework.security.web.SecurityFilterChain
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.UrlBasedCorsConfigurationSource
import java.nio.charset.StandardCharsets
import javax.crypto.spec.SecretKeySpec

@EnableWebSecurity
class SecurityConfig {
    @Autowired
    private lateinit var environment: Environment

    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http.cors().configurationSource(generateCorsConfig())

        val jwksUri = environment.getProperty("backend.security.jwks-uri")
        val jwtSecret = environment.getProperty("backend.security.jwt-secret")
        if (jwksUri != null) {
            http.oauth2ResourceServer().jwt().jwkSetUri(jwksUri)
        } else if (jwtSecret != null) {
            http.oauth2ResourceServer().jwt().decoder(createJwtDecoder(jwtSecret))
        } else {
            throw java.lang.IllegalArgumentException("Either backend.security.jwks-uri" +
                    " or backend.security.jwt-secret must be specified.")
        }

        return http.build()
    }

    private fun generateCorsConfig(): CorsConfigurationSource {
        val configuration = CorsConfiguration()
        val corsOrigin = environment.getProperty("backend.security.cors-origin")
        if (corsOrigin != null) configuration.allowedOrigins = listOf(corsOrigin)
        configuration.allowedMethods = listOf(CorsConfiguration.ALL)
        configuration.allowedHeaders = listOf(CorsConfiguration.ALL)
        configuration.allowCredentials = true
        val source = UrlBasedCorsConfigurationSource()
        source.registerCorsConfiguration("/**", configuration)
        return source
    }

    private fun createJwtDecoder(secret: String): JwtDecoder {
        val bytes = secret.toByteArray(StandardCharsets.UTF_8)
        val paddedSecret = if (bytes.size < 32) bytes.copyOf(32) else bytes
        return NimbusJwtDecoder.withSecretKey(SecretKeySpec(paddedSecret, "HmacSHA256")).build()
    }
}
