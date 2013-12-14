package test.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import test.usr.UserModel;

import com.firefly.annotation.Interceptor;
import com.firefly.mvc.web.HandlerChain;
import com.firefly.mvc.web.View;
import com.firefly.mvc.web.view.RedirectView;
import com.firefly.utils.log.Log;
import com.firefly.utils.log.LogFactory;

@Interceptor(uri = "/profile/*")
public class IndexInterceptor {
	private static Log log = LogFactory.getInstance().getLog("firefly-system");
	
	public View dispose(HandlerChain chain, HttpServletRequest request, HttpServletResponse response) {
		
		HttpSession session=request.getSession();
		UserModel user=(UserModel) session.getAttribute("user");
		session.getAttribute("user");
		
		System.out.println("用户是否登录："+user==null);
		if(user==null){
			//request.setAttribute("login", "true");
			//request.setAttribute("user", user);
			
			return new RedirectView("/");
		}
		
		return chain.doNext(request, response, chain);
	}
}
