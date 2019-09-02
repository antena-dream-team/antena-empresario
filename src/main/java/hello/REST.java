package hello;

import static spark.Spark.get;
import static spark.Spark.post;

import org.json.*;
import org.bson.Document;
import spark.Request;
import spark.Response;
import spark.Route;


public class REST {

	private Model model;


	public REST(Model store) {
		this.model = store;
	}

	public void home() {
		get("/test", new Route() {
			@Override
			public Object handle(final Request request, final Response response) {

				response.header("Access-Control-Allow-Origin", "*");
				JSONObject jsonobj = new JSONObject();
				jsonobj.put("Ola!", "Amigo!");
				return jsonobj;
			}
		});
	}

	public void cadastroEmpresario() {
		post("/cadastroempresario", new Route() {
			@Override
			public Object handle(final Request request, final Response response) {

				try {
					response.header("Access-Control-Allow-Origin", "*");
					String jsonString = request.body();
					model.addEmpresario(Document.parse(jsonString));

					return "Usuário adicionado";

				} catch (JSONException ex) {
					return "Deu ruim";
				}
			}
		});
	}

	public void cadastroProjeto() {
		post("/cadastroprojeto", new Route() {
			@Override
			public Object handle(final Request request, final Response response) {

				try {
					response.header("Access-Control-Allow-Origin", "*");
					String jsonString = request.body();
					model.addProjeto(Document.parse(jsonString));
					
					return "Projeto cadastrado";

				} catch (JSONException ex) {
					return "Deu ruim";
				}
			}
		});
	}
	
	public void getProjetos() {
		get("/projetos", new Route() {
			@Override
			public Object handle(final Request request, final Response response) {

				try {
					response.header("Access-Control-Allow-Origin", "*");
					String jsonString = request.body();

					model.addEmpresario(Document.parse(jsonString));

					return "Usuário adicionado";

				} catch (JSONException ex) {
					return "Deu ruim";
				}
			}
		});
	}

	public void loginEmpresario() {
		post("/loginempresario", new Route() {
			@Override
			public Object handle(final Request request, final Response response) {

				response.body(model.getAllProjetos().toString());

				return model.getAllProjetos();
			}
		});
	}
}
