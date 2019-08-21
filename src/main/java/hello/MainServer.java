package hello;

import static spark.Spark.*;




import java.util.LinkedList;
import java.util.List;



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
		controller.cadastro();
    }
	
    public static void initializeModel(){
		
	}
	
}
