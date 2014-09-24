package models;

import javax.persistence.Id;

import play.data.validation.Constraints;
import play.db.ebean.Model;

public class Comment extends Model {
	
	private static final long serialVersionUID = 7044837608968677484L;

	@Id
	public Long id;

    @Constraints.Required
	public String text;
    
    public Long color;
    
    public double like;
    public double positive;
    public double aggresive;
    
}
