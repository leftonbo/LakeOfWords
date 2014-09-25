package models.forms;

import play.data.validation.Constraints.Required;

public class FormComment {
	@Required(message="")
	public String comment;
}
