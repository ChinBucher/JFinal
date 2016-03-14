/*
 * # 基本配置 #
 */
package config;

import user.AdminController;
import user.LoginController;
import user.RegisterConller;
import user.UserController;
import index.IndexController;
import model.Blog;
import model.User;
import blog.BlogController;

import com.jfinal.config.Constants;
import com.jfinal.config.Handlers;
import com.jfinal.config.Interceptors;
import com.jfinal.config.JFinalConfig;
import com.jfinal.config.Plugins;
import com.jfinal.config.Routes;
import com.jfinal.core.JFinal;
import com.jfinal.ext.handler.ContextPathHandler;
import com.jfinal.ext.interceptor.SessionInViewInterceptor;
import com.jfinal.kit.PropKit;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.c3p0.C3p0Plugin;

public class Config extends JFinalConfig {

	@Override
	public void configConstant(Constants me) {
		// TODO Auto-generated method stub
		// 加载少量必要配置，随后可用PropKit.get(...)获取值
		PropKit.use("jdbc.txt");
		me.setDevMode(PropKit.getBoolean("devMode", false));
	}

	@Override
	public void configRoute(Routes me) {
		// TODO Auto-generated method stub
		me.add("/", IndexController.class);	
		me.add("/blog",BlogController.class);
	
		// # 用户列表 #
		me.add("/user",UserController.class);
		// # 登陆 #
		me.add("/login",LoginController.class,"/user");
		// # 注册 #
		me.add("/register",RegisterConller.class,"/user");
		me.add("/admin",AdminController.class,"/user");
		
		
	}
	
	//# 配置插件 #
	public static C3p0Plugin createC3p0Plugin() {
		String Url = PropKit.get("jdbcUrl");
		String User = PropKit.get("user");
		String Psw = PropKit.get("password");
		return new C3p0Plugin(Url,User,Psw.trim());
		//trim() 函数移除字符串两侧的空白字符或其他预定义字符。功能除去字符串开头和末尾的空格或其他字符。
	} 	
	
	@Override
	public void configPlugin(Plugins me) {
		// TODO Auto-generated method stub
		// 配置C3p0数据库连接池插件
		C3p0Plugin C3p0Plugin = createC3p0Plugin();
		me.add(C3p0Plugin);
		
		// 配置ActiveRecord插件
		ActiveRecordPlugin arp = new ActiveRecordPlugin(C3p0Plugin);
		me.add(arp);
		
		// 所有配置在 MappingKit 中搞定
		//_MappingKit.mapping(arp);
		//arp.addMapping("user","Id",User.class);
		arp.addMapping("user",User.class);
		arp.addMapping("blog",Blog.class);
	}

	@Override
	public void configInterceptor(Interceptors me) {
		// TODO Auto-generated method stub
		//session拦截器，用于在View模板中取出session值  
		me.add(new SessionInViewInterceptor());
	}

	//# 处理器配置 #
	@Override
	public void configHandler(Handlers me) {
		// TODO Auto-generated method stub
		me.add(new ContextPathHandler("CTX"));
		
	}
	
	/**
	 * 建议使用 JFinal 手册推荐的方式启动项目
	 * 运行此 main 方法可以启动项目，此main方法可以放置在任意的Class类定义中，不一定要放于此
	 */
	public static void main(String[] args) {
		JFinal.start("WebRoot", 80, "/", 5);
	}
}
