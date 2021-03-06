package com.jerry.security.uc.interceptor;

import com.jerry.security.auth.common.util.jwt.IJWTInfo;
import com.jerry.security.auth.common.util.jwt.JWTHelper;
import com.jerry.security.common.context.BaseContextHandler;
import com.jerry.security.uc.util.user.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 */
@Configuration
public class JWTInterceptor extends HandlerInterceptorAdapter {

    @Value("${jwt.token-header}")
    private String tokenHeader;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader(tokenHeader);

        IJWTInfo infoFromToken = jwtTokenUtil.getInfoFromToken(token);
        BaseContextHandler.setName(infoFromToken.getName());
        BaseContextHandler.setUsername(infoFromToken.getUniqueName());
        BaseContextHandler.setUserID(infoFromToken.getId());
        BaseContextHandler.setToken(token);

        return super.preHandle(request, response, handler);
    }
}
