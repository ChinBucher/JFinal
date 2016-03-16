package user;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import blog.BlogValidator;

import com.jfinal.aop.Before;
import com.jfinal.core.ActionKey;
import com.jfinal.core.Controller;
import com.jfinal.ext.interceptor.SessionInViewInterceptor;

import model.Blog;
import model.User;

public class UserController extends Controller{
	
	//@Before(SessionInViewInterceptor.class)
	public void index() {
		// # session 取值？？？null  # 
		//# freemarker 无法读取 session #
		//# 设置属性代替 #
		
		//# 根据用户角色跳转页面 主要为管理员 #
		/*
		User loginUser = getSessionAttr("loginUser");
		int role = loginUser.getRole();
		System.out.println("role:"+role);
		setAttr("role",role);
		
		if(role == 1){
			redirect("/admin");
		}else{
			setAttr("userPage", User.user.paginate(getParaToInt(0, 1), 10));
			render("user.html");
		}	
		*/
		redirect("/admin");
	}
	
	
	
	@Before(UserValidator.class)
	public void save() {
		System.out.println(getModel(User.class));
		getModel(User.class).save();
		redirect("/user");
	}
	
	public void edit() {
		setAttr("user", User.user.findById(getParaToInt()));
	}
	
	@Before(UserValidator.class)
	public void update() {
		getModel(User.class).update();
		redirect("/user");
	}
	
	public void delete() {
		User.user.deleteById(getParaToInt());
		redirect("/user");
	}
	
	
	//# 登陆注销模块 #
	//# ??? #
	//private static Logger log = LoggerFactory.getLogger(UserController.class);
	/*
	//@ActionKey("login")
	public void login(){
		render("login.html");
	}
	*/
	public void logout(){
		removeSessionAttr("loginUser");
		setAttr("role",null);
		redirect("/");
	}
	
	public void dologin(){
		String loginName = getPara("loginName");
		String pwd = getPara("password");
		String user_pwd = "";
		System.out.println("loginName?"+loginName);
		System.out.println("password?"+pwd);
		//校验参数，不能为空
		//# ①前端验证 #
		//# ②是否可以使用拦截器事先 验证 #
		String sql = "select * from user where name = ? and pwd = ?;";
		User nowUser = User.user.findFirst(sql,loginName,pwd);
		System.out.println("nowUser: "+nowUser);
		
        if (nowUser == null) {
        	//不存在
        	setAttr("code",0);
        }else{
        	//判断密码
        	user_pwd = nowUser.get("pwd");
        	System.out.println("pwd: "+user_pwd);
            if(user_pwd.equals(pwd)){
            	setAttr("code",1);
            	setAttr("name",nowUser.get("name"));
            }else{
            	setAttr("code",0);
            }
          //set login session
            setSessionAttr("loginUser", nowUser);
        }
        //int role = getSessionAttr("userRole");
		//System.out.println("role:"+role);
        renderJson();
	}
	
	//@Before(UserValidator.class)
		public void doregister() {
			System.out.println("doregister");
			String name = getPara("user.name"),
					  pwd = getPara("user.pwd");
			
			String sql = "select * from user where name = ? and pwd = ? ;";		
			User user = User.user.findFirst(sql,name,pwd);
			
			//String user_name = user.getName();
			
			if(user == null){
				setAttr("code",-1);
			}else if( user.getName() == null){
				User register_user = getModel(User.class);
				System.out.println("用户名可使用");
				register_user.save();
				setAttr("code",1);
			}else{
				System.out.println("用户名不可用");
				setAttr("code",0);
			}
			renderJson();
		}
		
		//用户详情
		public void detail(){
			//得到当前用户
			User userNow = getSessionAttr("loginUser");
			System.out.println("userNow:"+userNow);
			//得到当前用户的博客
			int author_id = userNow.get("id");
			System.out.println("author_id:"+author_id);
			//String sql = "select * from blog where author_id = ?;";
			//Blog blog = Blog.me.findFirst(sql,id);
			List<Blog> blog = Blog.me.find("select * from blog where author_id = "+author_id+";");
			
			setAttr("user",userNow);
			setAttr("blogPage", blog);
			render("/user/detail.html");
		}
		

}
