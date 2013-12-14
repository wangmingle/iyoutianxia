package test.controller;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;

import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import test.area.AreaBo;
import test.area.AreaModel;
import test.notes.NotesBo;
import test.notes.NotesModel;
import test.routes.RouteBo;
import test.routes.RouteModel;
import test.usr.UserBo;
import test.usr.UserModel;

import com.firefly.annotation.Controller;
import com.firefly.annotation.HttpParam;
import com.firefly.annotation.PathVariable;
import com.firefly.annotation.RequestMapping;
import com.firefly.mvc.web.HttpMethod;
import com.firefly.mvc.web.View;
import com.firefly.mvc.web.view.RedirectView;
import com.firefly.mvc.web.view.TemplateView;
import com.firefly.mvc.web.view.TextView;
import com.firefly.profile.Common;
import com.firefly.server.http.PartImpl;

@Controller
public class RouteController {
	
	RouteBo bo;
	public RouteController(){
		bo=new RouteBo();
	}
	@RequestMapping(value = "/route",method = {HttpMethod.GET, HttpMethod.POST})
	public View route(HttpServletRequest request, HttpServletResponse response) throws SQLException, InstantiationException, IllegalAccessException {
		
		HttpSession session=request.getSession();
		UserModel user=(UserModel) session.getAttribute("user");
		session.getAttribute("user");
		
		RouteModel queryObj=new RouteModel();
		List<RouteModel> list=bo.listModel("*", queryObj, null, null, null, null, -1, -1);
		request.setAttribute("routelist", list);
		
		if(user!=null){
			user.getId();
			request.setAttribute("user", user);
		}
		
		return new TemplateView("/route/routelist.html");
		
	}
	
	@RequestMapping(value = "/preroutepub")
	public View preroutepub(HttpServletRequest request, HttpServletResponse response) throws SQLException {
		HttpSession session = request.getSession();
		session.getAttribute("user");
		UserModel user=(UserModel) session.getAttribute("user");
		
		
		
		return new TemplateView("/route/addroute.html");
	}
	
	@RequestMapping(value = "/routepub",method = {HttpMethod.GET, HttpMethod.POST})
	public View routepub(HttpServletRequest request, HttpServletResponse response,@HttpParam("route") RouteModel route) throws SQLException, InstantiationException, IllegalAccessException {
		HttpSession session = request.getSession();
		session.getAttribute("user");
		UserModel user=(UserModel) session.getAttribute("user");
		if(user!=null){
			route.setAuthor(user.getId());
		}
		String idString=bo.addModel(route);
		
		
		String sqlString="select r.id as id ,r.name as name,r.startdate as startdate,r.days as days,r.tags as tags,r.startcity as startcity,r.endcity as endcity,concat(p1.name,',',a1.name) as startcityStr,concat(p2.name,',',a2.name) as endcityStr from route r "
						+"left join area a1 on r.startcity = a1.id "
						+"left join area p1 on a1.pid = p1.id "
						+"left join area a2 on r.endcity = a2.id "
						+"left join area p2 on a2.pid = p2.id "
						+"where r.id = '"+idString+"'";
		
		List<RouteModel> rmlist=bo.customListModel(sqlString);
		
		if(rmlist!=null&&rmlist.size()>0){
			request.setAttribute("route", rmlist.get(0));
		}
		return new TemplateView("/route/addroute1.html");
	}
	
	@RequestMapping(value = "/myroute",method = {HttpMethod.GET, HttpMethod.POST})
	public View myroute(HttpServletRequest request, HttpServletResponse response) throws SQLException, InstantiationException, IllegalAccessException {
		
		HttpSession session=request.getSession();
		UserModel user=(UserModel) session.getAttribute("user");
		session.getAttribute("user");
		
		RouteModel queryObj=new RouteModel();
		List<RouteModel> list=bo.listModel("*", queryObj, null, null, null, null, -1, -1);
		request.setAttribute("routelist", list);
		
		if(user!=null){
			user.getId();
			request.setAttribute("user", user);
		}
		
		return new TemplateView("/route/routelist.html");
		
	}
}
