package com.codetest.app.config

import com.codetest.app.auth.AuthenticatedUserArgumentResolver
import com.codetest.auth.AuthService
import org.springframework.context.annotation.Configuration
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

import java.util

// proxyBeanMethods = false avoids the CGLIB no-arg-constructor requirement
// for Scala classes with constructor parameters.
@Configuration(proxyBeanMethods = false)
class WebConfig(authService: AuthService) extends WebMvcConfigurer {

  override def addArgumentResolvers(resolvers: util.List[HandlerMethodArgumentResolver]): Unit =
    resolvers.add(new AuthenticatedUserArgumentResolver(authService))
}
