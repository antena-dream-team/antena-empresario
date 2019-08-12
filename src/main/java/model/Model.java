package model;

import org.bson.Document;
import com.github.fakemongo.Fongo;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class Model {
    Fongo fongo = new Fongo("mongo");


    public void addEmpresario(Document user) {
        MongoDatabase db = fongo.getDatabase("antenas");
        MongoCollection<Document> empresario = db.getCollection("empresario");
        empresario.insertOne(user);
    }

    public FindIterable<Document> searchByName(String name) {
        MongoDatabase db = fongo.getDatabase("antenas");
        MongoCollection<Document> users = db.getCollection("empresario");
        FindIterable<Document> found = users.find(new Document("name", name));
        return found;
    }
}
