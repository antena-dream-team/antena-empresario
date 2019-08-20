package hello;

import static spark.Spark.get;
import static spark.Spark.post;

import jdk.nashorn.internal.parser.JSONParser;
import org.bson.Document;
import org.json.JSONException;
import org.json.JSONObject;
import spark.Request;
import spark.Response;
import spark.Route;


public class REST{

	private Model model;
	

	public REST(Model store){
		this.model = store;
	}
	
	public void home() {
		get("/", new Route() {
			@Override
			public Object handle(final Request request, final Response response) {
				
				response.header("Access-Control-Allow-Origin", "*");
				JSONObject jsonobj = new JSONObject();
				jsonobj.put("Ola!", "Amigo!");
				return jsonobj;
			}
		});
	}
	
	public void cadastro() {
		post("/cadastro", new Route() {
			@Override
			public Object handle(final Request request, final Response response) {

				try {
					response.header("Access-Control-Allow-Origin", "*");
					String jsonString = request.body();
					JSONObject jsonObj = new JSONObject(jsonString);
					model.addEmpresario(Document.parse(jsonString));

					return "Usuário adicionado";

				} catch (JSONException ex) {
					return "Deu ruim";
				}

			}
		});
	}
	
		
}
