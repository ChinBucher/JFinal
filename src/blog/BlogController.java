package blog;

import java.util.List;

import com.jfinal.core.Controller;

import model.Blog;

import com.jfinal.aop.Before;
import com.jfinal.core.Controller;

/**
 * BlogController
 * 所有 sql 与业务逻辑写在 Model 或 Service 中，不要写在 Controller 中，养成好习惯，有利于大型项目的开发与维护
 */
@Before(BlogInterceptor.class)
public class BlogController extends Controller {
	public void index() {
		setAttr("blogPage", Blog.me.paginate(getParaToInt(0, 1), 10));
		render("blog.html");
	}
	
	public void add() {
	}
	
	@Before(BlogValidator.class)
	public void save() {
		getModel(Blog.class).save();
		redirect("/blog");
	}
	
	public void edit() {
		setAttr("blog", Blog.me.findById(getParaToInt()));
	}
	
	@Before(BlogValidator.class)
	public void update() {
		getModel(Blog.class).update();
		redirect("/blog");
	}
	
	public void delete() {
		Blog.me.deleteById(getParaToInt());
		redirect("/blog");
	}
	
	public void detail(){
		System.out.println("detail blog");
		int id = getParaToInt();
		System.out.println("id:"+id);
		//String sql = "select * from blog where author_id = ?;";
		//Blog blog = Blog.me.findFirst(sql,id);
		List<Blog> blog = Blog.me.find("select * from blog where id = "+id+";");
		
		setAttr("blog",blog);
		
		render("/blog/detail.html");
	}
}



