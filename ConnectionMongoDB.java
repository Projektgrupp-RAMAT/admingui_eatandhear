package admingui_eatandhear;

import java.net.UnknownHostException;

import com.mongodb.DB;
import com.mongodb.MongoClient;

/**
*
* @author Markus Eriksson
*/

public class ConnectionMongoDB {

	private static MongoClient mongoClient;

	public ConnectionMongoDB() {

	}

	public static DB getConnection() throws UnknownHostException {

		try {

			mongoClient = new MongoClient("localhost", 27017);
			return mongoClient.getDB("test");

		} catch (UnknownHostException e) {
			throw e;
		}
	}

	public static void closeConnection() {
			mongoClient.close();
	}
}