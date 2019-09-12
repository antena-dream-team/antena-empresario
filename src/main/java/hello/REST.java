package hello;

import static spark.Spark.get;
import static spark.Spark.post;

import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.json.*;

import com.mongodb.client.FindIterable;
import com.mongodb.client.result.DeleteResult;

import antenaJwtAuth.Jwt;
import antenaJwtAuth.JwtRest;

import org.bson.Document;
import spark.Request;
import spark.Response;
import spark.Route;


public class REST{

	private Model model;
	private String WhoIsauth;

	public REST(Model store) {
		model = store;
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

				String jsonString = request.body();
				JSONObject jsonobj =  new JSONObject(jsonString);
				Document found = model.searchByEmail(jsonobj.getString("email"));

                return found.toJson();
            }
        });
    }

    public  void getProjectByEmpresario() {
		get("/buscaprojetoporempresario", new Route() {
			@Override
			public Object handle(final Request request, final Response response) {

				String jsonString = request.body();
				JSONObject jsonobj =  new JSONObject(jsonString);

				FindIterable<Document> projectFound = model.getProjectByEmpresario(jsonobj.getString("email"));


				return StreamSupport.stream(projectFound.spliterator(), false)
						.map(Document::toJson)
						.collect(Collectors.joining(", ", "[", "]"));

			}
		});
	}

}
