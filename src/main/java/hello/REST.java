package hello;

import static spark.Spark.get;
import static spark.Spark.post;

import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.json.*;

import com.mongodb.client.FindIterable;

import antenaJwtAuth.Jwt;

import org.bson.Document;
import spark.Request;
import spark.Response;
import spark.Route;


public class REST {

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
					String senhaDigitada = myjson.getString("senha");
					String senhaArmazenada = user.getString("senha");
					boolean usuarioAtivo = user.getBoolean("ativo");

					if (email.length() > 0 && senhaDigitada.equals(senhaArmazenada) && usuarioAtivo) {
						response.status(200);
						return AuthEngine.GenerateJwt(email);
					}
					response.status(403);
					return "Usuário inexistente ou inativo";

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
					Document userData = Document.parse(jsonString);

					userData.append("ativo", false);

					Document found = model.searchByEmail(userData.getString("email"));

					if (found == null || found.isEmpty()) {
						model.addEmpresario(userData);
						new emailService(userData).sendSimpleEmail();
						return userData.toJson();
					} else {
						return "Email já cadastrado";
					}


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

					Document project = Document.parse(jsonString);
					model.addProjeto(project);
					
					return project.toJson();

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
					return model.deleteProject( Document.parse( request.body() ) ).getDeletedCount() > 0;

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

    public void ativarUsuario() {
		get("/ativar/:email", new Route() {
			@Override
			public Object handle(final Request request, final Response response) {
				String email = request.params(":email");

				Document found = model.searchByEmail(email);

				if (!found.isEmpty()) {
//					Jwt AuthEngine = new Jwt();
//					AuthEngine.GenerateJwt(email);

					response.redirect("http://localhost:63342/antena-empresario/antena-empresario.main/static/empresa.html?_ijt=9h8o1d56h0ung6ejgcr4pot81a");

				}

				return null;
			}
		});
	}

    public void getProjectByEmpresario() {
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
