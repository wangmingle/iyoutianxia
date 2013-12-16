package test.area;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet(name="areas",urlPatterns={"/areajson"})
@MultipartConfig
public class AreaServlet extends HttpServlet{

	 public void doGet(HttpServletRequest request,  
	            HttpServletResponse response) throws ServletException, IOException {  
		 	response.setContentType("text/xml;charset=utf-8");
	        AreaBo bo=new AreaBo();
	        
	        String pString=request.getParameter("input");
	        pString=new String(pString.getBytes("iso-8859-1"),"UTF-8");
	        pString=pString.toLowerCase();
	        
	        String stype=request.getParameter("stype");
	        
	        String sql="";
	        if(stype!=null&&"country".equals(stype)){
				sql="select a.id as id,a.name as name,a.fullname as fullname,a.ename as ename,a.pyname as pyname,a.shortname as shortname,p.name as pnameStr,p.ename as penameStr,e.name as stypeStr,e.ename as stypeeStr "
						+"from area a "
						+"left join enums p on  a.pid = p.id "
						+"left join enums e on a.stype = e.id "
						+"where "
						+"1=1 and a.stype = 'country' "
						+"and (a.name like '"+pString+"%' or a.fullname like '"+pString+"%' or a.ename like '"+pString+"%' or a.pyname like '"+pString+"%' or a.shortname like '"+pString+"%')";
			}
	        if(stype!=null&&"city".equals(stype)){
				sql="select a.id as id,a.name as name,a.fullname as fullname,a.ename as ename,a.pyname as pyname,a.shortname as shortname,p.name as pnameStr,p.ename as penameStr,e.name as stypeStr,e.ename as stypeeStr "
						+"from area a "
						+"left join area p on  a.pid = p.id "
						+"left join enums e on a.stype = e.id "
						+"where "
						+"1=1 and a.stype = 'city' "
						+"and (a.name like '"+pString+"%' or a.fullname like '"+pString+"%' or a.ename like '"+pString+"%' or a.pyname like '"+pString+"%' or a.shortname like '"+pString+"%')";
			}
	        if(stype!=null&&"all".equals(stype)){
				sql="select a.id as id,a.name as name,a.fullname as fullname,a.ename as ename,a.pyname as pyname,a.shortname as shortname,p.name as pnameStr,p.ename as penameStr,e.name as stypeStr,e.ename as stypeeStr "
						+"from area a "
						+"left join area p on  a.pid = p.id "
						+"left join enums e on a.stype = e.id "
						+"where "
						+"1=1 "
						+"and (a.name like '"+pString+"%' or a.fullname like '"+pString+"%' or a.ename like '"+pString+"%' or a.pyname like '"+pString+"%' or a.shortname like '"+pString+"%')";
			}
			
			
			List<AreaModel> list=new ArrayList<AreaModel>();
			try {
				list = bo.customListModel(new String[]{sql});
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InstantiationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			List<AreaJsonModel> jslist=new ArrayList<AreaJsonModel>();
			String xml="<?xml version=\"1.0\" encoding=\"utf-8\" ?><results>";
			for(AreaModel area:list){
				xml+="<rs id=\""+area.getId()+"\" latlng=\""+(area.getLat()+","+area.getLng())+"\" info=\"\">"+(area.getName()+","+area.getPnameStr())+"</rs>";
			}
			xml+="</results>";
				
			PrintWriter pw = response.getWriter();  
			pw.print(xml);
			pw.close();
			
	}

	 public void doPost(HttpServletRequest req, HttpServletResponse resp)  
	            throws ServletException, IOException {  
	        doGet(req, resp);  
	    
	}
	
}
