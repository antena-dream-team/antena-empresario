package hello;

import static spark.Spark.get;
import static spark.Spark.post;

import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.json.*;

import com.mongodb.client.FindIterable;
import com.mongodb.client.result.DeleteResult;

import antenaJwtAuth.JwtRest;

import org.bson.Document;
import spark.Request;
import spark.Response;
import spark.Route;


public class REST extends JwtRest{

	private Model model;


	public REST(Model store) {
		super(store);
	}

	public void home() {
		
		
		get("/test",  new Route() {
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
	
	public void deletaProjeto() {
		post("/deletaProjeto", new Route() {
			@Override
			public Boolean handle(final Request request, final Response response) {

				try {
					response.header("Access-Control-Allow-Origin", "*");
					return model.deleteProject( Document.parse( request.body() ) ).getDeletedCount() > 0 ? true: false;

				}catch(Exception ex){ throw ex; }

			}
		});
	}

	public void atualizaProjeto() {
		post("/atualizaProjeto", new Route() {
			@Override
			public Object handle(final Request request, final Response response) {

				try {
					response.header("Access-Control-Allow-Origin", "*");
					return model.updateProjeto(Document.parse( request.body() )) == null? "projeto n�o encontrado": "projeto deletado";
				}catch(Exception ex) { throw ex; }
			}
		});
	}

	public void getProjetos() {
		get("/projetos", new Route() {
			@Override
			public Object handle(final Request request, final Response response) {

				 FindIterable<Document> projectsFound = model.getAllProjetos();

				 return StreamSupport.stream(projectsFound.spliterator(), false)
			        .map(Document::toJson)
			        .collect(Collectors.joining(", ", "[", "]"));
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
