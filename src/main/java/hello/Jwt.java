package hello;
import com.auth0.jwt.*;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;


public final class Jwt {
	 
	private Algorithm SecretWord = Algorithm.HMAC256("antena-project");
	
	public String GenerateJwt() {
			// set key to generate jwt
			
			String token = new String();
			
			// try to generate jwt
			try {
				token = JWT.create()
				        .withIssuer("auth0")
				        .sign(this.SecretWord);	
			}catch(JWTCreationException ex){throw ex;}
			
			return token;
		}
	
	public boolean verifyJwt( String Tolken ) {		
		try {
		    JWTVerifier verifier = JWT.require(this.SecretWord)
		        .withIssuer("auth0")
		        .build(); //Reusable verifier instance
		     verifier.verify(Tolken);
		} catch (JWTVerificationException ex){return  false;}
		
		return true;
	}
}
