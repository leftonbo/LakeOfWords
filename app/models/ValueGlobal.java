package models;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;

import models.apis.ApiColorColor;
import play.db.ebean.*;

@Entity
public class ValueGlobal extends Model {
	private static final long serialVersionUID = 6797217273770791997L;

	@Id
	public Long id;
	
	public String state = "Natural";
    
    public int backColor  = 0xFFFFFF;
    public int TitleColor = 0x000000;
    
    public String strBCol() {
    	return String.format("#%06X", backColor);
    }
    public String strTCol() {
    	return String.format("#%06X", TitleColor);
    }

    public void changeColors(int auraCol, String emo) {
    	
    	backColor = Color.blendWith(auraCol, backColor, 0.2).encode();
    	
    	if (emo != null && emo != "") {
    		// Color API
        	ApiColorColor apiColor = new ApiColorColor(emo);
    		int apiCRes = apiColor.Send();
    		if (apiCRes == 0) {
    			// OK
    			int colnum = apiColor.colors.size();
    			int tcol = apiColor.colors.get(colnum-1);
    			TitleColor = Color.blendWith(tcol, TitleColor, 0.7).encode();
    		}
    	}
    		
    }
    
	/**
	 * The Finder
	 */
    public static final Finder<Long,ValueGlobal> find =
    	new Finder<Long,ValueGlobal>(Long.class, ValueGlobal.class);
    
    private static ValueGlobal vals;
    
    public static ValueGlobal getGVal() {
    	if (vals != null) return vals;
    	
    	List<ValueGlobal> l = find.all();
    	if (l.size() == 0) {
    		ValueGlobal newnew = new ValueGlobal();
    		l.add(newnew);
    		newnew.save();
    	}
    	vals = l.get(0);
    	
    	return vals;
    }
}
