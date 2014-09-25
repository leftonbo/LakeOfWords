package models;

import java.io.UnsupportedEncodingException;

import javax.persistence.Id;

import models.apis.ColorColor;
import play.data.validation.Constraints;
import play.db.ebean.Model;

public class Comment extends Model {
	
	private static final long serialVersionUID = 7044837608968677484L;

	@Id
	public Long id;

    @Constraints.Required
	public String text;
    
    public int color;
    
    public double like;
    public double positive;
    public double aggresive;
    
    
    public static Comment makeComment(String t) {
    	Comment newc = new Comment();
    	newc.text = t;
    	
    	ColorColor api = new ColorColor(t);
    	try {
			int apiRes = api.Send();
			if (apiRes != 0) return null;
			newc.color = api.colors.get(0);
		} catch (UnsupportedEncodingException e) {
			return null;
		}
    	
    	return newc;
    }
}
