package com.codetest.app.config

import com.codetest.auth.AuthService
import com.codetest.email.EmailClient
import org.springframework.context.annotation.{Bean, Configuration}

/**
 * Exposes the pre-built modules as Spring beans so you can inject them
 * straight into your controllers/services. Provided as a starter — feel
 * free to move or rewrite this class.
 */
@Configuration
class ProvidedModulesConfig {

  @Bean
  def emailClient(): EmailClient = new EmailClient()

  @Bean
  def authService(): AuthService = new AuthService()
}
