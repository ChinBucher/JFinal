package user;

import model.Blog;
import model.User;

import com.jfinal.core.Controller;

public class AdminController extends Controller{
	public void index(){
		User user = getSessionAttr("loginUser");
		int role = user.get("role");
		System.out.println("role:"+role);
		setAttr("role",role);
		
		setAttr("userPage", User.user.paginate(getParaToInt(0, 1), 10));
		setAttr("blogPage", Blog.me.paginate(getParaToInt(0, 1), 10));
		render("/user/admin.html");
	}

}
