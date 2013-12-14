package test.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import test.area.AreaBo;
import test.area.AreaJsonModel;
import test.area.AreaModel;

import com.firefly.annotation.Controller;
import com.firefly.annotation.RequestMapping;
import com.firefly.mvc.web.HttpMethod;
import com.firefly.mvc.web.View;

@Controller
public class AreaController {
	AreaBo bo;
	public AreaController(){
		bo=new AreaBo();
	}
	
	@RequestMapping(value = "/area",method = {HttpMethod.GET, HttpMethod.POST})
	public View route(HttpServletRequest request, HttpServletResponse response) throws SQLException, InstantiationException, IllegalAccessException, IOException {
		String pString=request.getParameter("input");
		System.out.println("::::::::"+pString);
		
		String sqlString="select a.id as id ,a.name as name,a.ename as ename,b.name as pnameStr "
				+"from area a "
				+"left join area b "
				+"on a.pid = b.id "
				+"where a.pid <> '-1'"
				+" and (a.name like '%"+pString+"%' or a.ename like '%"+pString+"%') ";
		
		List<AreaModel> list= bo.customListModel(sqlString);
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
