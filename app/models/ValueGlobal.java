package models;

import javax.persistence.Entity;
import javax.persistence.Id;

import play.db.ebean.*;

@Entity
public class ValueGlobal extends Model {
	private static final long serialVersionUID = 6797217273770791997L;

	@Id
	public Long id;
	
	public String state;
    
    public int backColor;
    public int TitleColor;
    
}
