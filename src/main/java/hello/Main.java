package hello;

import org.bson.Document;

import com.mongodb.client.FindIterable;

public class Main {

	public static void main(String[] args) {
		
		Model model = new Model();
		
		model.addResponsavelEmpresario(Document.parse("{'name':'pedro', 'password':'12345', 'diferentao': 'diferenciado'}"));
		model.addResponsavelEmpresario(Document.parse("{'name':'pedro', 'password':'11111'}"));
		
		FindIterable<Document> found = model.searchByName("pedro");
		
		for(Document doc:found){
			System.out.println(doc);
		}
	}
	
}
