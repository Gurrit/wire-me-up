package com.codetest.app.auth

import com.codetest.auth.{AuthService, UserDetails}
import jakarta.servlet.http.HttpServletRequest
import org.springframework.core.MethodParameter
import org.springframework.web.bind.support.WebDataBinderFactory
import org.springframework.web.context.request.NativeWebRequest
import org.springframework.web.method.support.{HandlerMethodArgumentResolver, ModelAndViewContainer}

class AuthenticatedUserArgumentResolver(authService: AuthService) extends HandlerMethodArgumentResolver {

  private val BearerPrefix = "Bearer "

  override def supportsParameter(parameter: MethodParameter): Boolean =
    parameter.hasParameterAnnotation(classOf[AuthenticatedUser]) &&
      parameter.getParameterType == classOf[UserDetails]

  override def resolveArgument(
      parameter: MethodParameter,
      mavContainer: ModelAndViewContainer,
      webRequest: NativeWebRequest,
      binderFactory: WebDataBinderFactory
  ): AnyRef = {
    val request = webRequest.getNativeRequest(classOf[HttpServletRequest])
    resolveUserDetails(request)
  }

  private def resolveUserDetails(request: HttpServletRequest): UserDetails = {
    /* TODO (Candidate)
     *  Extract the token using `request.getHeader("Authorization")`
     *  and use `authService.authenticate(String jwt)` to get the user if the token is valid,
     *  or return a 401 Status if not valid, or malformed etc..
     *  returning a `UserDetails` object from resolveArgument will automagically inject it into the Controller method call.
     */
    throw new UnsupportedOperationException("Implement Me")
  }
}
