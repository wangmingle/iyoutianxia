package test.controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import test.area.AreaBo;
import test.area.AreaModel;
import test.routes.RouteBo;
import test.routes.RouteDaysBo;
import test.routes.RouteDaysModel;
import test.routes.RouteModel;
import test.usr.UserModel;

import com.firefly.annotation.Controller;
import com.firefly.annotation.HttpParam;
import com.firefly.annotation.RequestMapping;
import com.firefly.mvc.web.HttpMethod;
import com.firefly.mvc.web.View;
import com.firefly.mvc.web.view.TemplateView;

@Controller
public class RouteController {
	
	RouteBo bo;
	RouteDaysBo dayBo;
	public RouteController(){
		bo=new RouteBo();
		dayBo=new RouteDaysBo();
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
		if(user!=null){
			user.getId();
		}
		request.setAttribute("user", user);
		
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
		request.setAttribute("user", user);
		
		String sqlString="select r.id as id ,r.name as name,r.startdate as startdate,r.days as days,r.tags as tags,r.startcity as startcity,r.endcity as endcity,concat(p1.name,',',a1.name) as startcityStr,concat(p2.name,',',a2.name) as endcityStr from route r "
						+"left join area a1 on r.startcity = a1.id "
						+"left join area p1 on a1.pid = p1.id "
						+"left join area a2 on r.endcity = a2.id "
						+"left join area p2 on a2.pid = p2.id "
						+"where r.id = '"+idString+"'";
		
		List<RouteModel> rmlist=bo.customListModel(new String[]{sqlString});
		
		if(rmlist!=null&&rmlist.size()>0){
			request.setAttribute("route", rmlist.get(0));
		}
		//临时方法处理城市，暂时加载到页面
		AreaBo areaBo=new AreaBo();
		AreaModel areaquery=new AreaModel();
		areaquery.setStype("city");
		
		List<AreaModel> arealist=areaBo.listModel("id,lat,lng", areaquery, null, null, null, null, -1, -1);
		request.setAttribute("arealist", arealist);
		
		return new TemplateView("/route/addroute1.html");
	}
	
	@RequestMapping(value = "/rclist",method = {HttpMethod.GET, HttpMethod.POST})
	public View rclist(HttpServletRequest request, HttpServletResponse response) throws SQLException, InstantiationException, IllegalAccessException {
		HttpSession session = request.getSession();
		session.getAttribute("user");
		UserModel user=(UserModel) session.getAttribute("user");
		if(user!=null){
			user.getId();
		}
		request.setAttribute("user", user);
		String id=request.getParameter("id");
		String[] citylist=request.getParameterValues("citylistid");
		String[] days=request.getParameterValues("days");
		
		List<RouteDaysModel> dayslist=new ArrayList<RouteDaysModel>();
		for(int i=0;i<citylist.length;i++){
			RouteDaysModel daysmodel=new RouteDaysModel();
			daysmodel.setCityid(citylist[i]);
			daysmodel.setSsort(i);
			daysmodel.setRouteid(id);
			daysmodel.setFlag(1);
			if(i==0){
				daysmodel.setDayflag("start");
				daysmodel.setDays(Double.parseDouble("0"));
				
			}else if(i==citylist.length-1){
				daysmodel.setDayflag("end");
				daysmodel.setDays(Double.parseDouble("0"));
			}else{
				daysmodel.setDayflag(String.valueOf(i));
				daysmodel.setDays(Double.parseDouble(days[i-1]));
			}
			dayslist.add(daysmodel);
		}
		dayBo.addModel(dayslist);
		
		//RouteDaysModel daysquery=new RouteDaysModel();
		//daysquery.setRouteid(id);
		
		//Map<String, String> orders=new HashMap<String, String>();
		//orders.put("ssort", "asc");
		
		//List<RouteDaysModel> daylist=dayBo.listModel("*", daysquery, null, " and dayflag <> 'start' and dayflag <> 'end' ", null, orders, -1, -1);
		
		String sql="select d.id as id,d.days as days,d.dayflag as dayflag,d.ssort as ssort,concat(a1.name,',',a2.name) as citynameStr "
				+"from routedays d "
				+"left join area a1 on d.cityid = a1.id "
				+"left join area a2 on a1.pid = a2.id "
				+"where d.dayflag <> 'start' and d.dayflag <> 'end' "
				+"and d.routeid='"+id+"' "
				+"order by d.ssort asc";
		List<RouteDaysModel> daylist=dayBo.customListModel(new String[]{sql});
		request.setAttribute("dayslist", daylist);
		
		RouteModel routequery=new RouteModel();
		routequery.setId(id);
		RouteModel route=bo.singleModel(routequery);
		
		request.setAttribute("route", route);
		return new TemplateView("/route/addroute2.html");
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
