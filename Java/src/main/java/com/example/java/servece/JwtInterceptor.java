package com.example.java.servece;

import com.auth0.jwt.exceptions.AlgorithmMismatchException;
import com.auth0.jwt.exceptions.InvalidClaimException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.example.java.util.JwtUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class JwtInterceptor implements HandlerInterceptor {

    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new JwtInterceptor())
                .addPathPatterns("/user/**")
                .excludePathPatterns("/user/login");
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("token");
        Map<String, Object> map = new HashMap<>();
        if (token == null || token.trim().equals("")) {
            map.put("message", "无token值!");
        } else {
            try {
                JwtUtil.verfy(token);
                return true;
            } catch (SignatureVerificationException e) {
                e.printStackTrace();
                map.put("message", "签名不一致!");
            } catch (TokenExpiredException e) {
                e.printStackTrace();
                map.put("message", "令牌过期!");
            } catch (AlgorithmMismatchException e) {
                e.printStackTrace();
                map.put("message", "算法不匹配!");
            } catch (InvalidClaimException e) {
                e.printStackTrace();
                map.put("message", "失效payload!");
            } catch (Exception e) {
                e.printStackTrace();
                map.put("message", "token无效");
            }
        }
        map.put("state", false);
        String json = new ObjectMapper().writeValueAsString(map);
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().println(json);
        return false;
    }
}
