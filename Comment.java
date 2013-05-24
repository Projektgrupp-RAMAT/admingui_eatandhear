/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package admingui_eatandhear;

import java.util.Vector;
import org.bson.types.ObjectId;

/**
 *
 * @author Tim Sahi, Markus Eriksson, Andreas Beuger
 */

public class Comment {

    private String restaurantId;
	private String userId;
	private String userName;
	private String soundLvl;
	private String text;
    private boolean flagged;
    private String id;

	public Comment(String restId, String userId, String userName, String soundLvl, String text) {

            this.restaurantId = restId;
            this.userId = userId;
            this.userName = userName;
            this.soundLvl = soundLvl;
            this.text = text;

            
	}
    public Comment(String restId, String userId, String userName, String soundLvl, String text, boolean flagged, String id) {

        this.restaurantId = restId;
        this.userId = userId;
        this.userName = userName;
        this.soundLvl = soundLvl;
        this.text = text;
        this.flagged = flagged;
        this.id = id;


    }
        
        public void setFlagged(boolean flagged){
            this.flagged = flagged;
        }
        public boolean getFlagged(){
            return flagged;
        }
        
         public Vector<Object> getVector(){
           
            Vector<Object> comment = new Vector<Object>();
            
            comment.add(getId());
            comment.add(getRestId());
            comment.add(getUserName());
            comment.add(getUserId());
            comment.add(getText());
            comment.add(getSoundLvl());
            comment.add(getFlagged());
            
            
            return comment;
            
        }
        public String getRestId(){
            
            return restaurantId;
            
        }
        public String getId(){

            return id;
        }

        public void setId(String id){

            this.id = id;
        }
        public void setRestId(String restId){
            
            this.restaurantId = restId;
            
        }
//	public ObjectId get_id() {
//		return _id;
//	}
//
//	public void set_id(ObjectId _id) {
//            
//		this._id = _id;
//	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getSoundLvl() {
		return soundLvl;
	}

	public void setSoundLvl(String soundLvl) {
		this.soundLvl = soundLvl;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

}