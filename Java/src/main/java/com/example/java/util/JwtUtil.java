package com.example.java.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.junit.Test;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class JwtUtil {
    public static String SECRET = "1c2h3e4n5w6e7i8x9i0n";

    public static String getToken(Map<String, String> map) {
        JWTCreator.Builder builder = JWT.create();
        map.forEach((k, v) -> {
            builder.withClaim(k, v);
        });
        Calendar instance = Calendar.getInstance();
        instance.add(Calendar.HOUR, 2);  //默认2小时过期

        builder.withExpiresAt(instance.getTime());
        String token = builder.sign(Algorithm.HMAC256(SECRET));
        return token;
    }

    public static DecodedJWT verfy(String token) {
        DecodedJWT decodedJWT = JWT.require(Algorithm.HMAC256(SECRET)).build().verify(token);
        return decodedJWT;
    }
}
