package hello;
import org.bson.Document;
import org.bson.conversions.Bson;

import com.github.fakemongo.Fongo;
import com.mongodb.BasicDBObject;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.FindOneAndUpdateOptions;


public class Model {

	Fongo fongo = new Fongo("mongo");
	MongoDatabase db = fongo.getDatabase("app");
	
	public void addProjeto(Document projeto) {
		MongoCollection<Document> projetos = db.getCollection("projeto");
    	projetos.insertOne(projeto);
	}
	
	
	public void addEmpresario(Document empresario) {
		MongoCollection<Document> empresarios = db.getCollection("empresario");
		empresarios.insertOne(empresario);
	}
	
	
	public void updateProjeto(Document projeto) {
		MongoCollection<Document> projetos = db.getCollection("projeto");
    	BasicDBObject query = new BasicDBObject();
    	query.append("_id", projeto.get("_id"));
    	Bson newDocument = new Document("$set", projeto);
    	projetos.findOneAndUpdate(query, newDocument, (new FindOneAndUpdateOptions()).upsert(true));
	}

	public FindIterable<Document> getAllProjetos() {
		MongoCollection<Document> projetos = db.getCollection("projeto");
		FindIterable<Document> todos = projetos.find();

		for(Document projeto: todos) {
			System.out.println(projeto);
		}
		
		return todos;
	}

	
	public Document searchByName(String name) {
		MongoCollection<Document> users = db.getCollection("empresario");
    	Document found = users.find(new Document("nome", name)).first();
    	return found;
    }
}
