package com.piekoszek.nowaksharedrent.jwt;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;


@AllArgsConstructor
public class HeaderJwtArgumentResolver implements HandlerMethodArgumentResolver {

    private JwtService jwtService;

    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        return methodParameter.getParameterAnnotation(Jwt.class) != null;
    }

    @Override
    public Object resolveArgument(
            @NonNull MethodParameter methodParameter,
            ModelAndViewContainer modelAndViewContainer,
            NativeWebRequest nativeWebRequest,
            WebDataBinderFactory webDataBinderFactory) {

                HttpServletRequest request = (HttpServletRequest) nativeWebRequest.getNativeRequest();
                jwtService.validateToken(request.getHeader("Authorization"));
                return jwtService.readToken(request.getHeader("Authorization"));
    }
}