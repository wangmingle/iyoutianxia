package test.controller;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.PasswordAuthentication;
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

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import test.usr.UserBo;
import test.usr.UserModel;

import com.firefly.annotation.Controller;
import com.firefly.annotation.HttpParam;
import com.firefly.annotation.PathVariable;
import com.firefly.annotation.RequestMapping;
import com.firefly.mvc.web.HttpMethod;
import com.firefly.mvc.web.View;
import com.firefly.mvc.web.view.JspView;
import com.firefly.mvc.web.view.RedirectView;
import com.firefly.mvc.web.view.TemplateView;
import com.firefly.mvc.web.view.TextView;
import com.firefly.profile.Common;
import com.firefly.server.http.PartImpl;

@Controller
public class IndexController {
	@RequestMapping(value = "/login",method = {HttpMethod.GET, HttpMethod.POST})
	public View login(HttpServletRequest request, HttpServletResponse response, @HttpParam UserModel u) throws SQLException {
		HttpSession session = request.getSession();
		String login=request.getParameter("login");
		//System.out.println("last uri:"+request.getRequestURI());
		if(login == null){
			request.setAttribute("account", "");
			request.setAttribute("mess", "请输入用户名、密码");
			return new TemplateView("/login.html");
		}else{
			UserModel user=UserBo.singleModel(u);
			if(user!=null){
				session.setAttribute("user",user);
				return new RedirectView("/");
			}else{
				request.setAttribute("account", u.getAccount());
				request.setAttribute("mess", "<span color='red'>用户名或密码不正确</span>");
				return new TemplateView("/login.html");
			}
			
		}
		
	}
	@RequestMapping(value = "/ajaxlogin",method = {HttpMethod.GET, HttpMethod.POST})
	public View ajaxlogin(HttpServletRequest request, HttpServletResponse response, @HttpParam UserModel u) throws SQLException, IOException {
		HttpSession session = request.getSession();
		String login=request.getParameter("login");
		
		String account=request.getParameter("account");
		String pwd=request.getParameter("password");
		
		System.out.println(account+"---------------"+pwd);
		JSONObject jObject=new JSONObject();
		
		if(account == null || pwd==null){
			jObject.put("flag", "-1");
		}else{
			UserModel uModel=new UserModel();
			uModel.setAccount(account);
			uModel.setPassword(pwd);
			
			UserModel user=UserBo.singleModel(uModel);
			if(user!=null){
				session.setAttribute("user",user);
				jObject.put("flag", "1");
			}else{
				jObject.put("flag", "0");
			}
			
		}
		
		response.setContentType("application/x-json");  
        PrintWriter pw = response.getWriter();  
        pw.print(jObject);
        pw.close();
        return null;
		
		
	}
	@RequestMapping(value = "/register",method = {HttpMethod.GET, HttpMethod.POST})
	public View toregister(HttpServletRequest request, HttpServletResponse response, @HttpParam UserModel u,@PathVariable String[] args) throws IOException, ServletException {
		//只是进入注册页面
		return new TemplateView("/register.html");
	}
	
	
	@RequestMapping(value = "/register/?",method = {HttpMethod.GET, HttpMethod.POST})
	public View register(HttpServletRequest request, HttpServletResponse response, @HttpParam UserModel u,@PathVariable String[] args) throws IOException, ServletException, SQLException {
		HttpSession session=request.getSession();
		if(args!=null){
			if(args[0]!=null&&"1".equals(args[0])){//简单信息
				String guideFlag=request.getParameter("guideflag");
				//System.out.println(guideFlag);
				if(guideFlag!=null){
					if("2".equals(guideFlag)){//普通用户注册
						String id=UserBo.addUser(u);
						
						UserModel user=new UserModel();
						user.setId(id);
						UserModel um=UserBo.singleModel(user);
						
						session.setAttribute("user", um);
						
						return new RedirectView("/");
					}else{//导游用户注册
						String id=UserBo.addUser(u);
						request.setAttribute("id", id);
						return new TemplateView("/register2.html");
					}
				}else{
					return new TemplateView("/register.html");
				}
			}else{
			//if(args[0]!=null&&"2".equals(args[0])){//上传附件信息
				for(Part part : request.getParts()) {
					if(part.getName().endsWith("pic")) {
						String fname= ((PartImpl)part).getFileName();
						fname=fname.substring(fname.lastIndexOf("."));
						
							
						if("idpic".equals(part.getName())){
							fname="idpic_"+ args[0] +fname;
							//u.setIdpic(((PartImpl)part).getFileName());
							u.setIdpic(fname);
						}
						if("guidecertpic".equals(part.getName())){
							fname="guidecertpic_"+ args[0] +fname;
							//u.setGuidecertpic(((PartImpl)part).getFileName());
							u.setGuidecertpic(fname);
						}
						part.write( Common.uploadUsrFilePath + "/" + fname );
					}
					
				}
				
				UserModel userquery=new UserModel();
				userquery.setId(args[0]);
				
				UserBo.updateUser(u, userquery);
				
				UserModel user=new UserModel();
				user.setId(args[0]);
				UserModel um=UserBo.singleModel(user);
				session.setAttribute("user", um);
				System.out.println(session.getAttribute("user"));
				System.out.println(um);
				
				return new  RedirectView("/");
			}
		}else{
			//只是进入注册页面
			return new TemplateView("/register.html");
		}
	}
	
	@RequestMapping(value = "/",method = {HttpMethod.GET,HttpMethod.POST})
	public View main(HttpServletRequest request, HttpServletResponse response) throws SQLException {
		request.setAttribute("login", "false");
		
		HttpSession session=request.getSession();
		UserModel user=(UserModel) session.getAttribute("user");
		session.getAttribute("user");
		
		if(user!=null){
			user.getId();
			request.setAttribute("login", "true");
			request.setAttribute("user", user);
		}
		//加载首页的导游信息
		String page=request.getParameter("page");
		if(page==null){
			page="1";
			request.setAttribute("page", Integer.parseInt(page)+1);
		}else{
			request.setAttribute("page", Integer.parseInt(page)+1);
		}
		
		UserModel queryObj=new UserModel();
		queryObj.setGuideflag(1);
		
		List<UserModel> guideList=UserBo.userList("*", queryObj, null, null, null, null, Integer.parseInt(page), Common.showNums);
		
		request.setAttribute("guideList", guideList);
		return new TemplateView("/main.html");
		
	}
	
	@RequestMapping(value = "/out",method = {HttpMethod.GET, HttpMethod.POST})
	public View out(HttpServletRequest request, HttpServletResponse response) throws SQLException {
		
		HttpSession session=request.getSession();
		
		session.removeAttribute("user");
		session.invalidate();
		
		return new RedirectView("/");
		
	}
	@RequestMapping(value = "/p",method = {HttpMethod.GET, HttpMethod.POST})
	public View profile(HttpServletRequest request, HttpServletResponse response) throws SQLException {
		
		HttpSession session=request.getSession();
		UserModel user=(UserModel) session.getAttribute("user");
		session.getAttribute("user");
		
		if(user!=null){
			user.getId();
			request.setAttribute("user", user);
		}
		
		return new TemplateView("/per.html");
		
	}
	
	@RequestMapping(value = "/pp",method = {HttpMethod.GET, HttpMethod.POST})
	public View photo(HttpServletRequest request, HttpServletResponse response) throws SQLException {
		
		HttpSession session=request.getSession();
		UserModel user=(UserModel) session.getAttribute("user");
		session.getAttribute("user");
		
		if(user!=null){
			user.getId();
			request.setAttribute("user", user);
		}
		
		return new TemplateView("/perimg.html");
		
	}
	
	@RequestMapping(value = "/gp",method = {HttpMethod.GET, HttpMethod.POST})
	public View gp(HttpServletRequest request, HttpServletResponse response) throws SQLException {
		
		HttpSession session=request.getSession();
		UserModel user=(UserModel) session.getAttribute("user");
		session.getAttribute("user");
		
		if(user!=null){
			user.getId();
			request.setAttribute("user", user);
		}
		
		return new TemplateView("/gper.html");
		
	}
	@RequestMapping(value = "/gpp",method = {HttpMethod.GET, HttpMethod.POST})
	public View gpp(HttpServletRequest request, HttpServletResponse response) throws SQLException {
		
		HttpSession session=request.getSession();
		UserModel user=(UserModel) session.getAttribute("user");
		session.getAttribute("user");
		
		if(user!=null){
			user.getId();
			request.setAttribute("user", user);
		}
		
		return new TemplateView("/gperimg.html");
		
	}
	
	@RequestMapping(value = "/ppup",method = {HttpMethod.POST})
	public View photoupload(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException {
		
		HttpSession session=request.getSession();
		UserModel user=(UserModel) session.getAttribute("user");
		session.getAttribute("user");
		System.out.print(user);
		if(user!=null){
			user.getId();
			System.out.print(user);
			String photoname="";
			System.out.print(request.getPart("photo"));
			for(Part part : request.getParts()) {
				System.out.print(part.getName());
				if(part.getName().equals("photo")) {
					String fname= ((PartImpl)part).getFileName();
					fname=fname.substring(fname.lastIndexOf("."));
					fname="header_"+user.getId()+fname;
					
					//part.write( "D:/MyWorkspace/workspace/fftest/page/photo/" + ((PartImpl)part).getFileName() );
					System.out.print(request.getServletContext().getRealPath("/")+"upload/usr/photo/"+fname);
					part.write(request.getServletContext().getRealPath("/")+"upload/usr/photo/"+fname);
					photoname=fname;
				}
			}
			if(!"".equals(photoname)){
				UserModel model=new UserModel();
				System.out.println("ceshi user:"+user);
				if(user!=null){
					//model.setId(user.getId());
					model.setPhoto(photoname);
					
					UserModel query=new UserModel();
					//query.setPhoto(photoname);
					query.setId(user.getId());
					UserBo.updateUser(model, query);
					
					user.setPhoto(photoname);
					session.setAttribute("user", user);
					session.getAttribute("user");
				}
			}
		}
		
		
		return new RedirectView("/pp");
		
	}
	
	@RequestMapping(value = "/pped",method = {HttpMethod.GET, HttpMethod.POST})
	public View photoupdate(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException {
		String x=request.getParameter("x");
		String y=request.getParameter("y");
		String w=request.getParameter("w");
		String h=request.getParameter("h");
		
		String imgname=request.getParameter("imgname");
		
		// ===源图片路径名称如:c:\1.jpg     
	    //String srcpath="D:/MyWorkspace/workspace/fftest/page/photo/"+imgname ;   
		String srcpath=Common.uploadUsrPhotoPath+"/"+imgname;
	           
	    // ===剪切图片存放路径名称.如:c:\2.jpg    
	    //String subpath="D:/MyWorkspace/workspace/fftest/page/photo/"+("header_"+imgname) ;
		String subpath=Common.uploadUsrPhotoPath+"/"+("small_"+imgname);
	    
	    //后缀 如 jpg png gif
	    String lastdir=imgname.substring(imgname.lastIndexOf(".")+1);
	      
	    // ===剪切点x坐标    
	    Double dxx=Double.parseDouble(x);
	    int xx= dxx.intValue();   
	    
	    Double dyy=Double.parseDouble(y);
	    int yy= dyy.intValue() ;       
	        
	    // ===剪切点宽度    
	    Double dw=Double.parseDouble(w);
	    int width= dw.intValue() ;   
	    
	    Double dh=Double.parseDouble(h);
	    int height=dh.intValue() ; 
		
		FileInputStream is =   null ;   
	    ImageInputStream iis = null ;   
	       
	    try {      
	            // 读取图片文件    
	           is =new FileInputStream(srcpath);    
	              
	              
	            //* 返回包含所有当前已注册 ImageReader 的 Iterator，这些 ImageReader   
	            //* 声称能够解码指定格式。 参数：formatName - 包含非正式格式名称 . 
	            //*（例如 "jpeg" 或 "tiff"）等 。   
	            
	           Iterator < ImageReader > it=ImageIO.getImageReadersByFormatName(lastdir);     
	           ImageReader reader = it.next();    
	            // 获取图片流     
	           iis = ImageIO.createImageInputStream(is);   
	                 
	               
	            //* <p>iis:读取源.true:只向前搜索 </p>.将它标记为 ‘只向前搜索’。 
	            //* 此设置意味着包含在输入源中的图像将只按顺序读取，可能允许 reader 
	            //* 避免缓存包含与以前已经读取的图像关联的数据的那些输入部分。 
	            
	           reader.setInput(iis, true ) ;   
	              
	               
	            //* <p>描述如何对流进行解码的类<p>.用于指定如何在输入时从 Java Image I/O   
	            //* 框架的上下文中的流转换一幅图像或一组图像。用于特定图像格式的插件 
	            //* 将从其 ImageReader 实现的 getDefaultReadParam 方法中返回   
	            //* ImageReadParam 的实例。    
	           
	           ImageReadParam param = reader.getDefaultReadParam();    
	               
	              
	           // * 图片裁剪区域。Rectangle 指定了坐标空间中的一个区域，通过 Rectangle 对象 
	           // * 的左上顶点的坐标（x，y）、宽度和高度可以定义这个区域。   
	            
	           Rectangle rect =   new Rectangle(xx, yy, width, height);    
	              
	                
	            // 提供一个 BufferedImage，将其用作解码像素数据的目标。     
	           param.setSourceRegion(rect);   

	              
	            //* 使用所提供的 ImageReadParam 读取通过索引 imageIndex 指定的对象，并将 
	            //* 它作为一个完整的 BufferedImage 返回。 
	                 
	           BufferedImage bi=reader.read(0,param);                   
	        
	            // 保存新图片     
	           ImageIO.write(bi,lastdir,new File(subpath));        
	       } finally {   
	            if (is != null )   
	              is.close() ;          
	            if (iis != null )   
	              iis.close();     
	       }     
		
		
		HttpSession session=request.getSession();
		UserModel user=(UserModel) session.getAttribute("user");
		session.getAttribute("user");
		
		if(user!=null){
			user.getId();
			request.setAttribute("user", user);
		}
		return new RedirectView("/pp");
		
	}
	
	
	
	@RequestMapping(value = "/document")
	public View document(HttpServletRequest request, @PathVariable String[] args) {
		//request.setAttribute("info", args);
		return new TemplateView("/document/index.html");
	}
	
	@RequestMapping(value = "/document/?")
	public View download(HttpServletRequest request, HttpServletResponse response,@PathVariable String[] args) throws IOException{
		String filename1 = new String("apache-tomcat-7.0.34.tar.gz".getBytes("ISO8859-1"),"UTF-8");
		if(args[0]!=null && "1".equals(args[0])){
			
		}
		if(args[0]!=null && "2".equals(args[0])){
			filename1 = new String("filename.txt".getBytes("ISO8859-1"),"UTF-8");
			
		}
		if(args[0]!=null && "3".equals(args[0])){
			filename1 = new String("t0001.xls".getBytes("ISO8859-1"),"UTF-8");
			
		}
		
		//String filename2 = new String(request.getParameter("fileName2").getBytes("ISO8859-1"),"UTF-8");
		//String filepath = request.getServletContext().getRealPath("D:/");
		//System.out.println(filepath);
		   File f = new File("D:\\"+filename1);
		   Long filelength = f.length();
		   int cacheTime = 10;
		
		
		response.setContentType("application/octet-stream");
		byte[] b = filename1.getBytes("GBK");   
		filename1 = new String(b,"8859_1");   
		response.setHeader("Content-Disposition", "attachment;filename=" + filename1); 
		response.setContentLength(filelength.intValue());
		OutputStream outputStream = response.getOutputStream();
		InputStream inputStream = new FileInputStream(f);
		   byte[] buffer = new byte[1024];
		   int i = -1;
		   while ((i = inputStream.read(buffer)) != -1) {
			   outputStream.write(buffer, 0, i);
		   }
		   
		   outputStream.flush();
		   outputStream.close();
		   inputStream.close();
		  
		   return null ;
	}
	
	@RequestMapping(value = "/add", method = HttpMethod.POST)
	public View add(HttpServletRequest request, HttpServletResponse response) {
		System.out.println("into /add");
		
		
		return new TextView(request.getParameter("content"));
	}
	
	@RequestMapping(value = "/upload", method=HttpMethod.POST)
	public View upload(HttpServletRequest request) throws IOException, ServletException {
		System.out.println(">>>>>>>>> upload start");
		
		for(Part part : request.getParts()) {
			
			System.out.println(part.getName() + "|" + part.getSize());
			if(part.getName().startsWith("content")) {
				part.write( "D:/" + ((PartImpl)part).getFileName() );
			} else {
				part.write( "D:/" + part.getName() + ".txt" );
			}
		}
//		throw new RuntimeException("upload error");
		return new TextView("upload ok!");
	}
	
	@RequestMapping(value = "/tojsp", method=HttpMethod.GET)
	public View tojsp(HttpServletRequest request) throws IOException, ServletException {
		request.setAttribute("msg", "i'm here");
		return new JspView("/jsp/test.jsp");
	}
	
	public static void main(String[] args) {
		String n="DSC_0377.JPG";
		String a="200.20";
		Double d=Double.parseDouble(a);
		
		//System.out.println(n.substring(n.lastIndexOf(".")));
		System.out.println(d.intValue());
	}
}
