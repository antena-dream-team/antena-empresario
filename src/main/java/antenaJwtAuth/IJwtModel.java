package antenaJwtAuth;
import org.bson.Document;

public interface IJwtModel {
	public Document searchByEmail(String email);
}
