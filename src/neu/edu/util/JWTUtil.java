package neu.edu.util;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;

public class JWTUtil {

	public static final String AUTHORIZATION_PROPERTY = "Authorization";
	// private static final String AUTHENTICATION_SCHEME = "Basic";
	public static final String AUTHENTICATION_SCHEME = "Bearer";
	
	private final static  String SECRET_KEY = "lifealive";


	public static String generateToken(String id,String[] roles) {

		Map<String, Object> headerClaims = new HashMap<>();
		headerClaims.put("typ", "JWT");
		
		try {
			Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY);
			String token = JWT.create()
					.withHeader(headerClaims)
					.withClaim("sub", id)
					.withArrayClaim("roles",roles )
					.withIssuer("auth0")
					.sign(algorithm);

			System.out.println("Token :"+token);
			return wrapAuthenticationHeader(token);
		} catch (UnsupportedEncodingException exception) {
			// UTF-8 encoding not supported
		} catch (JWTCreationException exception) {
			// Invalid Signing configuration / Couldn't convert Claims.
		}

		return null;
	}
	
	private static String wrapAuthenticationHeader(String token){
		return AUTHENTICATION_SCHEME+" "+token;
	}

	public static DecodedJWT validateToken(String token) {

		try {
			Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY);
			JWTVerifier verifier = JWT.require(algorithm)
									  .withIssuer("auth0")
									  .build(); // Reusable verifier instance
			DecodedJWT jwt = verifier.verify(token);
			
			return jwt;

		} catch (UnsupportedEncodingException exception) {
			// UTF-8 encoding not supported
		} catch (JWTVerificationException exception) {
			// Invalid signature/claims
		}

		return null;
	}

}
