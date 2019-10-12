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
		
		controller.cadastroEmpresario();
        controller.cadastroProjeto();
        controller.getProjetos();
        controller.deletaProjeto();
        controller.atualizaProjeto();
        controller.loginEmpresario();
        controller.getProjectByEmpresario();
        controller.ativarUsuario();
        // auth
        controller.Auth();
    }

    public static void initializeModel(){
    	model.addProjeto(Document.parse("{\n" +
            "    _id: '1o23u1io2jdpasd',\n" +
            "    titulo: 'Um projeto na fase 3',\n" +
            "    'descricao-breve': 'Nesta fase o usuário tem que esperar uma avaliação detalhada',\n" +
            "    'descricao-completa': 'Agora a descrição completa foi aprovada',\n" +
            "    'descricao-tecnologias': 'Hhaseiuaheiuahwsiue',\n" +
            "    'link-externo-1': '',\n" +
            "    'link-externo-2': '',\n" +
            "    fase: 3,\n" +
            "    reuniao: {\n" +
            "      data: '',\n" +
            "      horario: '',\n" +
            "      local: '',\n" +
            "      'datas-possiveis': []\n" +
            "    },\n" +
            "    status: {\n" +
            "      negado: false,\n" +
            "      motivo: ''\n" +
            "    },\n" +
            "    entregas: [],\n" +
            "    alunos: [],\n" +
            "    'responsavel-cadi': '',\n" +
            "    'responsavel-professor': [],\n" +
            "    'responsavel-empresario': 'Bruna'\n" +
            "  }"));
		model.getAllProjetos();
	}
}
