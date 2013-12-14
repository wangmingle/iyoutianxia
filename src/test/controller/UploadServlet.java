package test.controller;

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
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import com.firefly.utils.StringUtils;

import net.sf.json.JSONArray;
import test.area.AreaBo;
import test.area.AreaJsonModel;
import test.area.AreaModel;
import test.usr.UserBo;
import test.usr.UserModel;


@WebServlet(name="upload",urlPatterns={"/ppups"})
@MultipartConfig
public class UploadServlet extends HttpServlet{

	 public void doGet(HttpServletRequest request,  
	            HttpServletResponse response) throws ServletException, IOException {  
		 response.setContentType("text/html;charset=GBK");
			PrintWriter out = response.getWriter();
			HttpSession session=request.getSession();
			UserModel user=(UserModel) session.getAttribute("user");
			if(user!=null){
				Part part = request.getPart("photo");
				//获取该文件的上传域
				//Collection<String> headerNames = part.getHeaderNames();
				//for(String headerName : headerNames){
				//	out.println(headerName+"--->"+part.getHeader(headerName)+"<br/>");
				//}
				//将上传的文件写入服务器
				//part.write(getServletContext().getRealPath("/uploadFiles"+File.separator+fileName));
				String contentDisposition = part.getHeader("content-disposition");
				String[] t = StringUtils.split(contentDisposition, ';');
				String name="";
				String fileName="";
				String _name = t[1].trim();
				name = _name.substring(6, _name.length() - 1);
				
				if(t.length == 3) {
					String _filename = t[2].trim();
					fileName = _filename.substring(10, _filename.length() - 1);
				}
				System.out.println(fileName);
				String fname=fileName;
				fname=fname.substring(fname.lastIndexOf("."));
				fname="header_"+user.getId()+fname;
				fname=fname.toLowerCase();
				
				//part.write( "D:/MyWorkspace/workspace/fftest/page/photo/" + ((PartImpl)part).getFileName() );
				System.out.print(request.getServletContext().getRealPath("/")+"upload/usr/photo/"+fname);
				part.write(request.getServletContext().getRealPath("/")+"upload/usr/photo/"+fname);
				
				if(!"".equals(fname)){
					UserModel model=new UserModel();
					//System.out.println("ceshi user:"+user);
					if(user!=null){
						//model.setId(user.getId());
						model.setPhoto(fname);
						
						UserModel query=new UserModel();
						//query.setPhoto(photoname);
						query.setId(user.getId());
						UserBo.updateUser(model, query);
						
						user.setPhoto(fname);
						session.setAttribute("user", user);
						session.getAttribute("user");
						
						request.setAttribute("user", user);
					}
				}
			}
			response.sendRedirect("pp");
			//request.getRequestDispatcher("/perimg.html").forward(request,response);
		 	
	}

	 public void doPost(HttpServletRequest req, HttpServletResponse resp)  
	            throws ServletException, IOException {  
	        doGet(req, resp);  
	    
	}
	
}
