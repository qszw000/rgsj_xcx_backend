package cn.hsy.echo.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class TokenUtil {
    private static final String TOKEN_SECRET = "echo";

    private static final long EXPIRE_TIME = 1000 * 60 * 60 * 24 * 3; //token过期时间3天

    public TokenUtil() {
    }

    public static String createToken(String openId) {
        //过期时间
        Date date = new Date(System.currentTimeMillis() + EXPIRE_TIME);
        //秘钥级加密算法
        Algorithm algorithm = Algorithm.HMAC256(TOKEN_SECRET);
        //设置头部信息
        Map<String, Object> header = new HashMap<>(2);
        header.put("alg", "HS256");
        header.put("type", "JWT");
        //附带open_id，生成签名
        return JWT.create()
                .withHeader(header)
                .withClaim("openId", openId)
                .withExpiresAt(date)
                .sign(algorithm);
    }

    public static boolean verify(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(TOKEN_SECRET);
            JWTVerifier verifier = JWT.require(algorithm).build();
            DecodedJWT jwt = verifier.verify(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static String getOpenId(String token) {
        DecodedJWT jwt = JWT.decode(token);
        return jwt.getClaim("openId").asString();
    }
}
