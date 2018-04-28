package com.jarvis.user.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.io.UnsupportedEncodingException;

/**
 * Created by ZZF on 2018/4/23.
 */
public class TokenUtil {

    public static final String JWT_HEADER_NAME = "Authorization";

    public static final String JWT_SECRET = "cPe2hzkQ3wg3kbad";

    public static final String JWT_CLAIM_USER_NAME = "username";

    public static final String JWT_CLAIM_REAL_NAME = "realName";

    public static final String JWT_CLAIM_USER_ID = "userId";

    public static final String JWT_CLAIM_AGENCY_ID = "agencyId";

    public static final String JWT_CLAIM_GROUP_ID = "groupId";

    public static final String JWT_CLAIM_MOBILE = "mobile";

    public static final String JWT_CLAIM_CODE = "code";

    public static String generateToken(String agencyIds, String mobile,String code) {
        try {
            return JWT.create()
                    .withClaim(JWT_CLAIM_AGENCY_ID, agencyIds)
                    .withClaim(JWT_CLAIM_MOBILE, mobile)
                    .withClaim(JWT_CLAIM_CODE, code)
                    .sign(Algorithm.HMAC256(JWT_SECRET));
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }

    }

    public static String getCode(String token){
        if (token == null) {
            return null;
        }
        try {
            JWTVerifier verifier = JWT.require(Algorithm.HMAC256(JWT_SECRET)).build();
            DecodedJWT decodedJWT = verifier.verify(token);
            return decodedJWT.getClaim(JWT_CLAIM_CODE).asString();
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }

    }
}
