package config;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jfinal.handler.Handler;

public class SessionHandler extends Handler{

	@SuppressWarnings("deprecation")
	@Override
	public void handle(String target, HttpServletRequest request,
			HttpServletResponse response, boolean[] isHandled) {
		/*
		// TODO Auto-generated method stub
		request.setAttribute("session", request.getSession());
        nextHandler.handle(target, request, response, isHandled);
        */
		
	}

}
