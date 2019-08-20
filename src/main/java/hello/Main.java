package hello;

import org.bson.Document;

import com.mongodb.client.FindIterable;

public class Main {

	public static void main(String[] args) {
		
		Model model = new Model();
		
	    model.addProjeto(Document.parse("{'name':'pedro', 'password':'12345', 'diferentao': 'diferenciado'}"));
		model.getAllProjetos();
		
		Document pedro = model.searchByName("pedro");
		pedro.put("name", "pedrao");
		
		System.out.println(pedro.get("name"));
		
		model.updateProjeto(pedro);
		model.getAllProjetos();
	}
	
}
