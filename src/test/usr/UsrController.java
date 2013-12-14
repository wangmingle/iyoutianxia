package test.usr;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import com.firefly.annotation.Controller;
import com.firefly.annotation.HttpParam;
import com.firefly.annotation.RequestMapping;
import com.firefly.mvc.web.HttpMethod;
import com.firefly.mvc.web.View;
import com.firefly.mvc.web.view.JsonView;
import com.firefly.mvc.web.view.JspView;
import com.firefly.mvc.web.view.TemplateView;
import com.firefly.utils.json.Json;
import com.firefly.utils.json.JsonReader;
import com.firefly.utils.json.Parser;

@Controller
public class UsrController {
	@RequestMapping(value = "/user")
	public View index(HttpServletRequest request, HttpServletResponse response){
		request.setAttribute("info", new String[]{"hello firefly", "test"});
		//request.getRequestDispatcher("/test.jsp").forward(request, response);
		//return new JspView("/test.jsp");
		//return "user/index.jsp";
		return new TemplateView("/user/index.html");
	}
	
	@RequestMapping(value = "/user/value")
	public View bookValue(HttpServletRequest request, @HttpParam UserModel um) {
		request.setAttribute("user", um);
		return new JspView("/index.jsp");
	}
	
	@RequestMapping(value = "/user/list")
	public View list(HttpServletRequest request, HttpServletResponse response) {
		//request.setAttribute("info", new String[]{"hello firefly", "test"});
		UserBo bo=new UserBo();
		
			//List<UserModel>list = bo.userList("",new Object[]{},2,8);
			//request.setAttribute("ulist", list);
		
		return new TemplateView("/user/list.html");
	}
	
	@RequestMapping(value = "/user/add", method = HttpMethod.POST)
	public View add(@HttpParam("user") UserModel um) {
		
		//System.out.println(name+"---"+u.getName());
		Integer gender=1;
		Integer flag=1;
		Date bir=new Date();
		//UserModel um=new UserModel();
		//um.setName(name);
		um.setGender(gender);
		um.setBirthday(bir.getTime());
		
		UserBo bo=new UserBo();
		bo.addUser(um);
		
		//new JspView("");
		return new TemplateView("/user/success.html");
	}
	
	@RequestMapping(value="/user/sel")
	public View sel(@HttpParam("user") UserModel um, HttpServletResponse response) throws IOException{
		UserBo bo=new UserBo();
		List<UserModel> list=new ArrayList<UserModel>();
		
		
		response.setHeader("cache-control", "no-cache");
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		net.sf.json.JSONArray array = new JSONArray(); 
		
		System.out.println(um.getName());
		/*try {
			//list = bo.userList("name like ? limit ?,?",new Object[]{"%"+um.getName()+"%",2,8},-1,-1);
			
			//out.print(array.fromObject(list));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			out.flush();
			out.close();
		}*/
		
		return null;
		
		//return new JsonView(list);
		/*String msg = "dkjfdkjfkdjfkdjfkdjfk";
		response.setCharacterEncoding("UTF-8");
		response.setHeader("Content-Type", "text/html; charset=UTF-8");
		response.setHeader("Content-Length", String.valueOf(msg.getBytes("UTF-8").length));
		PrintWriter writer = response.getWriter();
		try {
			writer.print(msg);
		} finally {
			writer.close();
		}
		return null;*/
	}
}
