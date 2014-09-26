package models;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;
import javax.persistence.Version;

import org.apache.commons.lang3.StringUtils;

import com.avaje.ebean.annotation.CreatedTimestamp;

import models.apis.ApiColorColor;
import models.apis.ApiWord;
import play.data.validation.Constraints;
import play.db.ebean.Model;

@Entity
public class Comment extends Model {
	
	private static final long serialVersionUID = 7044837608968677484L;

	@Id
	public Long id;
	
    @CreatedTimestamp
    public Date createDate;
    @Version
    public Date updateDate;

    @Constraints.Required
	public String text;
    
    public int color1;
    public int color2;
    public int color3;
    
    public String emotion;

    @Transient
    public int result;
    
    public String getColor1String() {
    	return String.format("#%06X", color1);
    }
    public String getColor2String() {
    	return String.format("#%06X", color2);
    }
    public String getColor3String() {
    	return String.format("#%06X", color3);
    }
    
    // ==============================================================
    
    public static Comment makeComment(String t) {
    	Comment newc = new Comment();
    	newc.text = t;
    	
		// Color API
    	ApiColorColor apiColor = new ApiColorColor(t);
		int apiCRes = apiColor.Send();
		if (apiCRes == -1) {
			// failed API
			newc.result = -1;
			return newc;
		} else if (apiCRes == -2) {
			// Color Not Found
			newc.color1 = 0x808080;
			newc.color2 = 0x000000;
			newc.color3 = 0x808080;
		} else {
			// OK
			int colnum = apiColor.colors.size();
			newc.color1 = apiColor.colors.get(colnum-1);
			if (colnum > 1) {
				newc.color2 = apiColor.colors.get(colnum-2);
			} else {
				newc.color2 = apiColor.colors.get(0);
			}
			if (colnum > 2) {
				newc.color3 = apiColor.colors.get(colnum-3);
			} else {
				newc.color3 = apiColor.colors.get(0);
			}
		}
		
		// Word API
		ApiWord apiWord = new ApiWord(t);
		int apiWRes = apiWord.Send();
		if (apiWRes != 0) {
			// failed API
			newc.result = -2;
			return newc;
		} else {
			// OK
			newc.emotion = StringUtils.join(apiWord.sen, ",");
		}
    	
    	return newc;
    }
    
	/**
	 * The Finder
	 */
    public static final Finder<Long,Comment> find =
    	new Finder<Long,Comment>(Long.class, Comment.class);

	public static final List<Comment> all() {
		return find.orderBy("createDate desc").findList();
	}
	
    public static int getSizeByIndex(int i) {
    	switch (i) {
    	case  0:		return 45;
    	case  1:		return 40;
    	case  2:		return 36;
    	case  3:		return 32;
    	case  4:		return 29;
    	case  5:		return 26;
    	case  6:		return 24;
    	case  7:		return 22;
    	case  8:		return 20;
    	case  9:		return 18;
    	case 10:		return 17;
    	case 11:		return 16;
    	case 12:		return 15;
    	case 13:		return 14;
    	case 14:		return 13;
    	case 15:		return 13;
    	case 16:		return 12;
    	case 17:		return 12;
    	case 18:		return 11;
    	case 19:		return 11;
    	}
    	return 10;
    }
}
