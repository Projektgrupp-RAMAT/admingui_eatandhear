/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package admingui_eatandhear;

import java.util.Vector;
import org.bson.types.ObjectId;


public class Comment {

//	private ObjectId _id;
        private String restId;
	private String userId;
	private String userName;
	private String soundLvl;
	private String text;
        private boolean flagged;

	public Comment(String restId, String userId, String userName, String soundLvl, String text) {

            this.restId = restId;
            this.userId = userId;
            this.userName = userName;
            this.soundLvl = soundLvl;
            this.text = text;
            
	}
        
        public void setFlagged(boolean flagged){
            this.flagged = flagged;
        }
        public boolean getFlagged(){
            return flagged;
        }
        
         public Vector<Object> getVector(){
           
            Vector<Object> comment = new Vector<Object>();
            
       //     comment.add(get_id());
            comment.add(getRestId());
            comment.add(getUserName());
            comment.add(getUserId());
            comment.add(getText());
            comment.add(getSoundLvl());
            comment.add(getFlagged());
            
            
            return comment;
            
        }
        public String getRestId(){
            
            return restId;
            
        }
        
        public void setRestId(String restId){
            
            this.restId = restId;
            
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