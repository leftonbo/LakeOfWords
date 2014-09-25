package models;

import java.util.Date;

import javax.persistence.Id;
import javax.persistence.Transient;
import javax.persistence.Version;

import org.apache.commons.lang3.StringUtils;

import com.avaje.ebean.annotation.CreatedTimestamp;

import models.apis.ApiColorColor;
import models.apis.ApiWord;
import play.data.validation.Constraints;
import play.db.ebean.Model;

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
    
    public String emotion;

    @Transient
    public int result;
    
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
		} else {
			// OK
			newc.color1 = apiColor.colors.get(0);
			newc.color2 = apiColor.colors.get(1);
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
}
