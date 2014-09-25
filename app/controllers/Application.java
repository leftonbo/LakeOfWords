package controllers;

import static play.data.Form.form;
import models.Comment;
import models.forms.FormComment;
import play.data.Form;
import play.mvc.*;
import views.html.*;

public class Application extends Controller {
	
	final public static Result GO_HOME = redirect(routes.Application.index());

    public static Result index() {
    	Form<FormComment> fm = form(FormComment.class);
        return ok(index.render(fm));
    }
    
    public static Result go_home() {
        return GO_HOME;
    }
    
    public static Result post() {
    	Form<FormComment> fm = form(FormComment.class).bindFromRequest();
    	
    	if (fm.hasErrors()) {
            return badRequest(index.render(fm));
    	}
    	
    	FormComment fc = fm.get();
    	
    	Comment newCom = Comment.makeComment(fc.comment);
    	
    	boolean fail = false;
    	switch (newCom.result) {
    	case -1:
    		Application.flash("danger", "Post Failed - Encode was failed.");
    		fail = true; 		break;
    	case -2:
    		Application.flash("danger", "Post Failed - an API has occured an error.");
    		fail = true; 		break;
    	}
    	
    	if (fail) {
            return internalServerError(index.render(fm));
    	}
    	
        return TODO;
    }

}
