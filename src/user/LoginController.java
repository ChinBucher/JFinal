package user;

import com.jfinal.core.Controller;

public class LoginController extends Controller{
	public void index(){
		System.out.println("logincontroller");
		render("/user/login.html");
	}

}
