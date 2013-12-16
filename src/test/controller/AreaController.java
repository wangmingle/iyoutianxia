package test.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
import test.area.AreaBo;
import test.area.AreaJsonModel;
import test.area.AreaModel;
import test.notes.NotesBo;
import test.notes.NotesModel;
import test.usr.UserModel;

import com.firefly.annotation.Controller;
import com.firefly.annotation.HttpParam;
import com.firefly.annotation.RequestMapping;
import com.firefly.mvc.web.HttpMethod;
import com.firefly.mvc.web.View;
import com.firefly.mvc.web.view.RedirectView;
import com.firefly.mvc.web.view.TemplateView;
import com.firefly.tools.Pinyin4jUtil;

@Controller
public class AreaController {
	AreaBo bo;
	public AreaController(){
		bo=new AreaBo();
	}
	@RequestMapping(value = "/psign",method = {HttpMethod.GET, HttpMethod.POST})
	public View psign(HttpServletRequest request, HttpServletResponse response) throws SQLException, InstantiationException, IllegalAccessException {
		
		HttpSession session=request.getSession();
		UserModel user=(UserModel) session.getAttribute("user");
		session.getAttribute("user");
		
		
		return new TemplateView("/route/addrsign.html");
		
	}
	
	@RequestMapping(value = "/areasign",method = {HttpMethod.GET, HttpMethod.POST})
	public View areasign(HttpServletRequest request, HttpServletResponse response,@HttpParam("area") AreaModel area) {
		HttpSession session = request.getSession();
		session.getAttribute("user");
		UserModel user=(UserModel) session.getAttribute("user");
		
		if(user!=null){
			area.setAuthor(user.getId());
		}
		//request.setAttribute("user", user);
		if(area.getEname()==null || "".equals(area.getEname())){
			area.setEname(Pinyin4jUtil.getPinYin(area.getFullname()));
		}
		if(area.getPyname()==null || "".equals(area.getPyname())){
			area.setPyname(Pinyin4jUtil.getPinYin(area.getFullname()));
		}
		area.setShortname(Pinyin4jUtil.getPinYinHeadChar(area.getFullname()));
		String continent=request.getParameter("continent");
		String country=request.getParameter("country");
		String city=request.getParameter("city");
		if(continent!=null&&!"".equals(continent)){
			area.setPid(continent);
		}
		if(country!=null&&!"".equals(country)){
			area.setPid(country);
		}
		if(city!=null&&!"".equals(city)){
			area.setPid(city);
		}
		area.setStatus(1);
		area.setFlag(1);
		String id=bo.addModel(area);
		
		return new RedirectView("/psign");
	}
	
	@RequestMapping(value = "/area",method = {HttpMethod.GET, HttpMethod.POST})
	public View route(HttpServletRequest request, HttpServletResponse response) throws SQLException, InstantiationException, IllegalAccessException, IOException {
		String pString=request.getParameter("input");
		String stype=request.getParameter("stype");
		String sql="";
		//全部
		
		//国家
		
		//城市
		
		//按类型
		
		if(stype!=null&&"country".equals(stype)){
			sql="select a.id as id,a.name,a.fullname,a.ename,a.pyname,a.shortname,p.name,e.name,e.ename "
					+"from area a "
					+"left join area p on  a.pid = p.id "
					+"left join enums e on a.stype = e.id "
					+"where "
					+"1=1 and a.stype = 'country' "
					+"and (a.name like '"+pString+"%' or a.fullname like 'b%' or a.ename like 'b%' or a.pyname like 'b%' or a.shortname like 'b%')";
		}
		
		String sqlString="select a.id as id ,a.name as name,a.ename as ename,b.name as pnameStr "
				+"from area a "
				+"left join area b "
				+"on a.pid = b.id "
				+"where a.pid <> '-1'"
				+" and (a.name like '%"+pString+"%' or a.ename like '%"+pString+"%') ";
		
		List<AreaModel> list= bo.customListModel(new String[]{sqlString});
		List<AreaJsonModel> jslist=new ArrayList<AreaJsonModel>();
		for(AreaModel area:list){
			AreaJsonModel jsonModel=new AreaJsonModel();
			jsonModel.setId(area.getId());
			jsonModel.setValue(area.getId());
			jsonModel.setInfo(area.getName()+","+area.getPnameStr());
			jslist.add(jsonModel);
		}
		
		JSONArray array=JSONArray.fromObject(jslist);
		
		System.out.println(array.toString());
		response.setContentType("application/x-json");  
        PrintWriter pw = response.getWriter();  
        pw.print(array.toString());
        pw.close();
        return null;
		
	}
	
}
