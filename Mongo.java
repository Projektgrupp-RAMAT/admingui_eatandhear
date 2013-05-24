package admingui_eatandhear;

import com.google.gson.Gson;
import com.mongodb.*;
import com.mongodb.util.JSON;
import org.bson.types.ObjectId;

import javax.swing.table.DefaultTableModel;
import java.net.UnknownHostException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Tim Sahi, Markus Eriksson, Andreas Beuger
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

    public void deleteComment(String id) {


        try {

            db = ConnectionMongoDB.getConnection();
            DBCollection coll = db.getCollection("comments");

            BasicDBObject query = new BasicDBObject("_id", new ObjectId(id));

            coll.remove(query);
        } catch (UnknownHostException ex) {
            Logger.getLogger(Mongo.class.getName()).log(Level.SEVERE, null, ex);
        }



    }
    
    public void saveChanges(Comment comment) {
        try {

           db = ConnectionMongoDB.getConnection();
           DBCollection coll = db.getCollection("comments");
            BasicDBObject query = new BasicDBObject("_id", new ObjectId(comment.getId()));

           BasicDBObject newCom = new BasicDBObject("restId", comment.getRestId());
           newCom.append("userId", comment.getUserId());
           newCom.append("userName", comment.getUserName());
           newCom.append("soundLvl", comment.getSoundLvl());
           newCom.append("text", comment.getText());
           newCom.append("flagged", comment.getFlagged());
           BasicDBObject set = new BasicDBObject("$set", newCom);
        
           coll.update(query, set);
            
            
            
        } catch (Exception e) {

        }
          

        
    }
    public String getUptime(){

        double uptime = 0;

        try {
            db = ConnectionMongoDB.getConnection();
            CommandResult cls =  db.command("serverStatus");
            uptime =  (Double)cls.get("uptime") ;

        } catch (UnknownHostException e) {
            e.printStackTrace();
        }



        uptime/=60;
        uptime/=60;
        uptime/=24;

        NumberFormat nf = NumberFormat.getInstance();
        nf.setMaximumFractionDigits(2);
        String uptimeString = nf.format(uptime);



        return uptimeString + " days.";
    }
    public DefaultTableModel getFlaggedComments() {
        
        DBCollection coll;
        DBCursor cursor = null;
        Gson gson;
        ArrayList<Comment> list = new ArrayList<Comment>();
        DBObject dbo;
        Comment comment;

        try {

            db = ConnectionMongoDB.getConnection();
            coll = db.getCollection("comments");
            BasicDBObject query =  new BasicDBObject("flagged", true);
            gson = new Gson();
            cursor = coll.find(query);


            while (cursor.hasNext()) {

                dbo = cursor.next();
                comment =(Comment) gson.fromJson(dbo.toString(), Comment.class);
                comment.setId(dbo.get("_id").toString());
                list.add(comment);

            }

        } catch (UnknownHostException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } finally {

            cursor.close();
            ConnectionMongoDB.closeConnection();
        }

        Vector data = new Vector();

        for (int i = 0; i < list.size(); i++) {

            data.add(list.get(i).getVector());
   

        }

        Vector<String> column = new Vector/*<>*/();

        column.add("ID");
        column.add("Rest ID");
        column.add("Username");
        column.add("User ID");
        column.add("Comment");
        column.add("Soundlevel");
        column.add("Flagged");
      

        DefaultTableModel model = new DefaultTableModel(data, column);


        return model;
        
    }
    public boolean getConnectionStatus() {
        return true;
    }

        public DefaultTableModel getComments() {


        DBCollection coll;
        DBCursor cursor = null;
        Gson gson;
        ArrayList<Comment> list = new ArrayList<Comment>();
        DBObject dbo;
        Comment comment;

        try {

            db = ConnectionMongoDB.getConnection();
            coll = db.getCollection("comments");
            gson = new Gson();
            cursor = coll.find();

            while (cursor.hasNext()) {


                dbo = cursor.next();
                comment =(Comment) gson.fromJson(dbo.toString(), Comment.class);
                comment.setId(dbo.get("_id").toString());
                list.add(comment);
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

        Vector<String> column = new Vector/*<>*/();

        column.add("ID");
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