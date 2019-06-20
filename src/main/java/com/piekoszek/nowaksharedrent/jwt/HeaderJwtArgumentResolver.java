package com.piekoszek.nowaksharedrent.jwt;

import lombok.AllArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;

@AllArgsConstructor
public class HeaderJwtArgumentResolver implements HandlerMethodArgumentResolver {

    private JwtService jwtService;

    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        return methodParameter.getParameterAnnotation(Jwt.class) != null;
    }

    @Override
    public Object resolveArgument(
            MethodParameter methodParameter,
            ModelAndViewContainer modelAndViewContainer,
            NativeWebRequest nativeWebRequest,
            WebDataBinderFactory webDataBinderFactory) throws Exception {

                HttpServletRequest request = (HttpServletRequest) nativeWebRequest.getNativeRequest();
                return jwtService.readToken(request.getHeader("Authorization"));
    }
}