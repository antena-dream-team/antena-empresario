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
        controller.getEmpresarios();
        controller.deletaProjeto();
        controller.atualizaProjeto();
        controller.loginEmpresario();
        controller.getProjectByEmpresario();
        controller.ativarUsuario();
        // auth
        controller.Auth();
        controller.IsAuth();
    }

    public static void initializeModel(){
    	model.getAllProjetos();

    	model.addProjeto(Document.parse("{\"_id\": \"1o23u1io2jdpasd\",\"titulo\": \"Um projeto na fase 2\",\"descricao-breve\": \"Nesta fase o usuário tem que esperar uma avaliação detalhada\",\"descricao-completa\": \"\",\"descricao-tecnologias\": \"\",\"link-externo-1\": \"\",\"link-externo-2\": \"\",\"fase\": 2,\"reuniao\": {  \"data\": \"\",  \"horario\": \"\",  local: \"\",  \"datas-possiveis\": []},\"status\": {  \"negado\": false,  \"motivo\": \"\"},\"entregas\": [],\"alunos\": [],\"responsavel-cadi\": \"\",\"responsavel-professor\": [],\"responsavel-empresario\": \"flromeiroc@gmail.com\"}"));
    	model.addProjeto(Document.parse("{\"_id\": \"pjpinih321djs\",\"titulo\": \"Um projeto na fase 3\",\"descricao-breve\": \"Nesta fase o usuário tem que esperar uma avaliação detalhada\",\"descricao-completa\": \"Descricao completa lindissima\",\"descricao-tecnologias\": \"Tem até descricao de tecnologia\",\"link-externo-1\": \"http://www.fabioromeiro.com.br\",\"link-externo-2\": \"\",\"fase\": 3,\"reuniao\": {  \"data\": \"\",  \"horario\": \"\",  local: \"\",  \"datas-possiveis\": []},\"status\": {  \"negado\": false,  \"motivo\": \"\"},\"entregas\": [],\"alunos\": [],\"responsavel-cadi\": \"\",\"responsavel-professor\": [],\"responsavel-empresario\": \"flromeiroc@gmail.com\"}"));
    	model.addProjeto(Document.parse("{\"_id\": \"ioeoqromksc812\",\"titulo\": \"Um projeto na fase 4\",\"descricao-breve\": \"Nesta fase o usuário tem que esperar uma avaliação detalhada\",\"descricao-completa\": \"Descricao completa lindissima\",\"descricao-tecnologias\": \"Tem até descricao de tecnologia\",\"link-externo-1\": \"http://www.fabioromeiro.com.br\",\"link-externo-2\": \"\",\"fase\": 4,\"reuniao\": {  \"data\": \"\",  \"horario\": \"\",  local: \"\",  \"datas-possiveis\": [    {      \"data\": \"05/04/2019\",      \"hora\": \"15:50\"    },    {      \"data\": \"24/04/2019\",      \"hora\": \"10:20\"    },    {      \"data\": \"09/05/2019\",      \"hora\": \"13:15\"    }  ]},\"status\": {  \"negado\": false,  \"motivo\": \"\"},\"entregas\": [],\"alunos\": [],\"responsavel-cadi\": \"\",\"responsavel-professor\": [],\"responsavel-empresario\": \"flromeiroc@gmail.com\"}"));
    	model.addProjeto(Document.parse("{\"_id\": \"qowiu3oiqew521\",\"titulo\": \"Um projeto na fase 5 pendente\",\"descricao-breve\": \"Nesta fase o usuário tem que esperar uma avaliação detalhada\",\"descricao-completa\": \"Descricao completa lindissima\",\"descricao-tecnologias\": \"Tem até descricao de tecnologia\",\"link-externo-1\": \"http://www.fabioromeiro.com.br\",\"link-externo-2\": \"\",\"fase\": 5,\"reuniao\": {  \"data\": \"05/04/2019\",  \"horario\": \"15:50\",  local: \"Rua Barbosa\",  \"datas-possiveis\": [    {      \"data\": \"05/04/2019\",      \"hora\": \"15:50\"    },    {      \"data\": \"24/04/2019\",      \"hora\": \"10:20\"    },    {      \"data\": \"09/05/2019\",      \"hora\": \"13:15\"    }  ]},\"status\": {  \"negado\": false,  \"motivo\": \"\"},\"entregas\": [],\"alunos\": [],\"responsavel-cadi\": \"\",\"responsavel-professor\": [],\"responsavel-empresario\": \"flromeiroc@gmail.com\"}"));
    	model.addProjeto(Document.parse("{\"_id\": \"rotejhroncasd51\",\"titulo\": \"Um projeto na fase 5\",\"descricao-breve\": \"Nesta fase o usuário tem que esperar uma avaliação detalhada\",\"descricao-completa\": \"Descricao completa lindissima\",\"descricao-tecnologias\": \"Tem até descricao de tecnologia\",\"link-externo-1\": \"http://www.fabioromeiro.com.br\",\"link-externo-2\": \"\",\"fase\": 5,\"reuniao\": {  \"data\": \"05/04/2019\",  \"horario\": \"15:50\",  local: \"Rua Barbosa\",  \"datas-possiveis\": [    {      \"data\": \"05/04/2019\",      \"hora\": \"15:50\"    },    {      \"data\": \"24/04/2019\",      \"hora\": \"10:20\"    },    {      \"data\": \"09/05/2019\",      \"hora\": \"13:15\"    }  ]},\"status\": {  \"negado\": false,  \"motivo\": \"\"},\"entregas\": [  {    \"aluno-responsavel\": \"flromeiroc@gmail.com\",    \"alunos\": [      \"outros@email.com\"    ],    \"link-repositorio\": \"https://github.com/projeto-antena/antena-empresario\",    \"link-cloud\": \"http://github.com/FabioRomeiro\",    \"comentario\": \"Nos esforçamos bastante nesse projeto mesmo tendo zilhões de provas pra fazer :D\"  }],\"alunos\": [],\"responsavel-cadi\": \"\",\"responsavel-professor\": [],\"responsavel-empresario\": \"flromeiroc@gmail.com\"}"));
	}
}
