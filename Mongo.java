package admingui_eatandhear;

import com.google.gson.Gson;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.util.JSON;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Tim
 */
public class Mongo {

    private DB db;

    public boolean insertComment(Comment comment) {
        try {
            db = ConnectionMongoDB.getConnection();
            DBCollection coll = db.getCollection("comments");
            Gson gson = new Gson();

            DBObject newComment = (DBObject) JSON.parse(gson.toJson(comment));
          
            coll.insert(newComment);

            return true;
        } catch (UnknownHostException ex) {
            Logger.getLogger(Mongo.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        } finally {

            ConnectionMongoDB.closeConnection();
        }



    }

    public void deleteComment(String restId, String userId, String text) {


        try {

            db = ConnectionMongoDB.getConnection();
            DBCollection coll = db.getCollection("comments");

            BasicDBObject query = new BasicDBObject("restId", restId);
            query.put("userId", userId);
            query.put("text", text);
            coll.remove(query);
        } catch (UnknownHostException ex) {
            Logger.getLogger(Mongo.class.getName()).log(Level.SEVERE, null, ex);
        }



    }
    
    public void saveChanges(Comment comment, Comment oldComment) {
        try {
            
            db = ConnectionMongoDB.getConnection();
            DBCollection coll = db.getCollection("comments");
            BasicDBObject query = new BasicDBObject("restId", oldComment.getRestId());
            query.put("userId", oldComment.getUserId());
            query.put("userName", oldComment.getUserName());
         //   query.put("soundLvl", oldComment.getSoundLvl());
            query.put("text", oldComment.getText());
   
            BasicDBObject newCom = new BasicDBObject("restId", comment.getRestId());
            newCom.append("userId", comment.getUserId());
            newCom.append("userName", comment.getUserName());
            newCom.append("soundLvl", comment.getSoundLvl());
            newCom.append("text", comment.getText());
            BasicDBObject set = new BasicDBObject("$set", newCom);
        
            coll.update(query, set);
            
            
            
        } catch (UnknownHostException ex) {
            Logger.getLogger(Mongo.class.getName()).log(Level.SEVERE, null, ex);
        }
          

        
    }
    public DefaultTableModel getFlaggedComments() {
        
        DBCollection coll;
        DBCursor cursor = null;
        Gson gson;
        ArrayList<Comment> list = new ArrayList<Comment>();

        try {

            db = ConnectionMongoDB.getConnection();
            coll = db.getCollection("comments");
            BasicDBObject query =  new BasicDBObject("flagged", true);
            gson = new Gson();
            cursor = coll.find(query);

            while (cursor.hasNext()) {
                
                list.add((Comment) gson.fromJson(cursor.next().toString(), Comment.class));
            }

        } catch (UnknownHostException e) {

            e.printStackTrace();
            throw new RuntimeException(e);

        } finally {

            cursor.close();
            ConnectionMongoDB.closeConnection();
        }

        Vector data = new Vector();

        for (int i = 0; i < list.size(); i++) {

            data.add(list.get(i).getVector());
   

        }

        Vector<String> column = new Vector<>();


        column.add("Rest ID");
        column.add("Username");
        column.add("User ID");
        column.add("Comment");
        column.add("Soundlevel");
        column.add("Flagged");
      

        DefaultTableModel model = new DefaultTableModel(data, column);


        return model;
        
    }

    public DefaultTableModel getComments() {


        DBCollection coll;
        DBCursor cursor = null;
        Gson gson;
        ArrayList<Comment> list = new ArrayList<Comment>();

        try {

            db = ConnectionMongoDB.getConnection();
            coll = db.getCollection("comments");
            gson = new Gson();
            cursor = coll.find();

            while (cursor.hasNext()) {
                
                list.add((Comment) gson.fromJson(cursor.next().toString(), Comment.class));
            }

        } catch (UnknownHostException e) {

            e.printStackTrace();
            throw new RuntimeException(e);

        } finally {

            cursor.close();
            ConnectionMongoDB.closeConnection();
        }

        Vector data = new Vector();

        for (int i = 0; i < list.size(); i++) {

            data.add(list.get(i).getVector());
   

        }

        Vector<String> column = new Vector<>();


        column.add("Rest ID");
        column.add("Username");
        column.add("User ID");
        column.add("Comment");
        column.add("Soundlevel");
        column.add("Flagged");
      

        DefaultTableModel model = new DefaultTableModel(data, column);


        return model;
    }
}