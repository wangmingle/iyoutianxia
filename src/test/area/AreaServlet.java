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
			
			String sqlString="select a.id as id ,a.name as name,a.ename as ename,b.name as pnameStr "
					+"from area a "
					+"left join area b "
					+"on a.pid = b.id "
					+"where a.pid <> '-1'"
					+" and (lower(a.name) like '"+pString.toLowerCase()+"%' or lower(a.ename) like '"+pString.toLowerCase()+"%') ";
			
			
				List<AreaModel> list=new ArrayList<AreaModel>();
				try {
					list = bo.customListModel(sqlString);
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
					xml+="<rs id=\""+area.getId()+"\" info=\"\">"+(area.getName()+","+area.getPnameStr())+"</rs>";
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
