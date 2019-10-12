package hello;

import com.mongodb.MongoClient;
import org.bson.Document;
import org.bson.conversions.Bson;

import com.github.fakemongo.Fongo;
import com.mongodb.BasicDBObject;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.FindOneAndUpdateOptions;
import com.mongodb.client.result.DeleteResult;
import antenaJwtAuth.IJwtModel;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import static com.mongodb.client.model.Filters.*;
import com.mongodb.client.model.CreateCollectionOptions;
import com.mongodb.client.model.ValidationOptions;

public class Model implements IJwtModel{

//	Fongo fongo = new Fongo("mongo");
//	MongoDatabase db = fongo.getDatabase("app");

	MongoClient mongoClient = new MongoClient();
	MongoDatabase db = mongoClient.getDatabase("app");
	
	public void addProjeto(Document projeto) {
		MongoCollection<Document> projetos = db.getCollection("projeto");
    	projetos.insertOne(projeto);
	}
	
	public DeleteResult deleteProject(Document project) {
		MongoCollection<Document> projectsFound = db.getCollection("projeto");
		return projectsFound.deleteOne(project);
	}
	
	public void addEmpresario(Document empresario) {
		MongoCollection<Document> empresarios = db.getCollection("empresario");
		empresarios.insertOne(empresario);
	}
	
	public Document updateProjeto(Document projeto) {
		MongoCollection<Document> projetos = db.getCollection("projeto");
    	BasicDBObject query = new BasicDBObject();
    	query.append("_id", projeto.get("_id"));
    	Bson newDocument = new Document("$set", projeto);
    	return projetos.findOneAndUpdate(query, newDocument, (new FindOneAndUpdateOptions()).upsert(true));
	}

	public Document updateEmpresario(Document empresario) {
		MongoCollection<Document> projetos = db.getCollection("empresario");
		BasicDBObject query = new BasicDBObject();
		query.append("_id", empresario.get("_id"));
		Bson newDocument = new Document("$set", empresario);
		return projetos.findOneAndUpdate(query, newDocument, (new FindOneAndUpdateOptions()).upsert(true));
	}

	public FindIterable<Document> getAllProjetos() {
		MongoCollection<Document> projetos = db.getCollection("projeto");
		FindIterable<Document> todos = projetos.find();

		for(Document projeto: todos) {
			System.out.println(projeto);
		}
		return todos;
	}

	public Document searchByEmail(String email) {
		MongoCollection<Document> users = db.getCollection("empresario");
    	Document found = users.find(new Document("email", email)).first();
    	return found;
    }

    public FindIterable<Document> getProjectByEmpresario(String email) {
		MongoCollection<Document> projetos = db.getCollection("projeto");
		FindIterable<Document> found = projetos.find(new Document("email", email));

		return found;
	}
}
