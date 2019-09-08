package antenaJwtAuth;

import static spark.Spark.get;
import static spark.Spark.post;

import org.bson.Document;
import org.json.JSONException;
import org.json.JSONObject;

import hello.Model;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.Spark.*;

public class JwtRest {
	private Model model;
	private String WhoIsauth;

	protected JwtRest(Model model){
		this.model = model;
	}
	
	public String getWhoIsauth() {
		return WhoIsauth;
	}

	public void setWhoIsauth(String whoIsauth) {
		WhoIsauth = whoIsauth;
	}
	
	public void Auth() {
		post("/Auth", new Route() {
			@Override
			public Object handle(final Request request, final Response response) {

				try {
					response.header("Access-Control-Allow-Origin", "*");
					// set
					JSONObject myjson = new JSONObject(request.body());
					Jwt AuthEngine = new Jwt();
					
					
					// try to find user
					Document user = model.searchByEmail(myjson.getString("email"));
					String email = user.getString("email");
					if (email.length() > 0) {
						return AuthEngine.GenerateJwt(email);
					}
					return "Bad Request";

				} catch (JSONException ex) {
					return "Real Bad Request";
				}
			}
		});
	}
	
	public boolean IsAuth(String body) {
				try {
					// setting
					JSONObject myjson = new JSONObject(body);
					Jwt AuthEngine = new Jwt();
					
					// try to find user 
				
					String emailOrNull = AuthEngine.verifyJwt((myjson.getString("token")));
					
					if(emailOrNull == null) {
						return false;
					}else {
						setWhoIsauth(emailOrNull);
						return true;
					} 
					

				} catch (JSONException ex) {
					return false;
				}
				
		
	}
}
