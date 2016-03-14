package user;


import model.User;
import com.jfinal.core.Controller;
import com.jfinal.validate.Validator;

/**
 * BlogValidator.
 */
public class UserValidator extends Validator {
	
	protected void validate(Controller controller) {
		//addError("titleMsg", "请输入Blog标题!");
		validateRequiredString("user.name", "nameMsg", "请输入用户姓名!");
		validateRequiredString("user.pwd", "pwdMsg", "请输入用户密码!");
	}
	
	protected void handleError(Controller controller) {
		controller.keepModel(User.class);
		
		String actionKey = getActionKey();
		if (actionKey.equals("/user/save"))
			controller.render("register.html");
		else if (actionKey.equals("/user/update"))
			controller.render("edit.html");
	}
}
