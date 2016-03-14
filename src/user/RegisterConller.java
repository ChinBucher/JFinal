package user;

import com.jfinal.core.Controller;

public class RegisterConller extends Controller{
	public void index(){
		render("/user/register.html");
	}

}
