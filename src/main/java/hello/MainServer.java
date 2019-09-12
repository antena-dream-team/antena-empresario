package hello;

import static spark.Spark.*;

import org.bson.Document;


public class MainServer {
	final static Model model = new Model();

    public static void main(String[] args) {

		// Get port config of heroku on environment variable
        ProcessBuilder process = new ProcessBuilder();
        Integer port;
        if (process.environment().get("PORT") != null) {
            port = Integer.parseInt(process.environment().get("PORT"));
        } else {
            port = 8081;
        }
        port(port);
        
        initializeModel();

		staticFileLocation("/static");
		
		REST controller = new REST(model); 
		
		controller.home();
		controller.cadastroEmpresario();
        controller.cadastroProjeto();
        controller.getProjetos();
        controller.deletaProjeto();
        controller.atualizaProjeto();
        controller.loginEmpresario();
        controller.getProjectByEmpresario();
        // auth
        controller.Auth();
    }

    public static void initializeModel(){
    	model.addProjeto(Document.parse("{'name':'pedro', 'password':'12345', 'diferentao': 'diferenciado'}"));
		model.getAllProjetos();
	}
	
}
