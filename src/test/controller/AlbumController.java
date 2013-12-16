package test.controller;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.coobird.thumbnailator.Thumbnails;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import test.album.AlbumBo;
import test.album.AlbumDetailBo;
import test.album.AlbumDetailModel;
import test.album.AlbumModel;
import test.usr.UserModel;

import com.firefly.annotation.Controller;
import com.firefly.annotation.PathVariable;
import com.firefly.annotation.RequestMapping;
import com.firefly.mvc.web.HttpMethod;
import com.firefly.mvc.web.View;
import com.firefly.mvc.web.view.JsonView;
import com.firefly.mvc.web.view.JspView;
import com.firefly.mvc.web.view.RedirectView;
import com.firefly.mvc.web.view.TemplateView;
import com.firefly.mvc.web.view.TextView;
import com.firefly.oid.Oid;
import com.firefly.profile.Common;

@Controller
public class AlbumController {
	@RequestMapping(value = "/file/",method = {HttpMethod.GET, HttpMethod.POST})
	public View file(HttpServletRequest request, HttpServletResponse response) throws SQLException {
		
		HttpSession session=request.getSession();
		UserModel user=(UserModel) session.getAttribute("user");
		session.getAttribute("user");
		
		if(user!=null){
			user.getId();
			request.setAttribute("user", user);
		}
		
		
		
		return new TemplateView("/upload.html");
		
	}
	
	@RequestMapping(value = "/file/upload",method = {HttpMethod.GET, HttpMethod.POST})
	public View fileupload(HttpServletRequest request, HttpServletResponse response) throws SQLException {
		
		HttpSession session=request.getSession();
		UserModel user=(UserModel) session.getAttribute("user");
		session.getAttribute("user");
		
		if(user!=null){
			user.getId();
			request.setAttribute("user", user);
		}
		
		
		String savePath = Common.uploadPath +"/";
		File f1 = new File(savePath);
		//System.out.println(savePath);
		if (!f1.exists()) {
		    f1.mkdirs();
		}
		DiskFileItemFactory fac = new DiskFileItemFactory();
		ServletFileUpload upload = new ServletFileUpload(fac);
		upload.setHeaderEncoding("utf-8");
		List fileList = null;
		try {
		    fileList = upload.parseRequest(request);
		} catch (FileUploadException ex) {
		    //return;
		}
		Iterator<FileItem> it = fileList.iterator();
		String name = "";
		String extName = "";
		while (it.hasNext()) {
		    FileItem item = it.next();
		    if (!item.isFormField()) {
		        name = item.getName();
		        long size = item.getSize();
		        String type = item.getContentType();
		        //System.out.println(size + " " + type);
		        if (name == null || name.trim().equals("")) {
		            continue;
		        }
		        //扩展名格式：  
		        if (name.lastIndexOf(".") >= 0) {
		            extName = name.substring(name.lastIndexOf("."));
		        }
		        File file = null;
		        do {
		            //生成文件名：
		            name = UUID.randomUUID().toString();
		            file = new File(savePath + name + extName);
		        } while (file.exists());
		        File saveFile = new File(savePath + name + extName);
		        try {
		            item.write(saveFile);
		        } catch (Exception e) {
		            e.printStackTrace();
		        }
		    }
		}
		//response.getWriter().print(name + extName);
		
		return new TemplateView("/route/routelist.html");
		
	}
	
	@RequestMapping(value = "/album",method = {HttpMethod.GET, HttpMethod.POST})
	public View album(HttpServletRequest request, HttpServletResponse response) throws SQLException {
		
		HttpSession session=request.getSession();
		UserModel user=(UserModel) session.getAttribute("user");
		session.getAttribute("user");
		
		if(user!=null){
			user.getId();
			request.setAttribute("user", user);
			
			AlbumModel album=new AlbumModel();
			album.setUid(user.getId());
			
			String page="1";
			
			List<AlbumModel> albumList=AlbumBo.albumList("*", album, null, null, null, null, Integer.parseInt(page), Common.showNums);
			
			request.setAttribute("albumList", albumList);
		}
		
		
		request.setAttribute("imgPath", Common.imgServerPath);
		
		return new TemplateView("/album.html");
		
	}
	
	@RequestMapping(value = "/albumshow",method = {HttpMethod.GET, HttpMethod.POST})
	public View albumshow(final HttpServletRequest request, HttpServletResponse response,@PathVariable String[] args) throws SQLException {
		
		HttpSession session=request.getSession();
		UserModel user=(UserModel) session.getAttribute("user");
		session.getAttribute("user");
		String albumid=request.getParameter("albumid");
		
		if(user!=null){
			user.getId();
			request.setAttribute("user", user);
			
			AlbumDetailModel album=new AlbumDetailModel();
			album.setAlbumid(albumid);
			
			String page="1";
			
			List<AlbumDetailModel> albumList=AlbumDetailBo.albumList("*", album, null, null, null, null, -1, -1);
			
			request.setAttribute("albumList", albumList);
		}
		
		
		request.setAttribute("imgPath", Common.imgServerPath);
		
		
		return new TemplateView("/albumshow.html");
		
	}
	
	@RequestMapping(value = "/albumjson",method = {HttpMethod.GET, HttpMethod.POST})
	public View albumjson(final HttpServletRequest request, HttpServletResponse response,@PathVariable String[] args) throws SQLException, IOException {
		
		HttpSession session=request.getSession();
		UserModel user=(UserModel) session.getAttribute("user");
		session.getAttribute("user");
		String albumid=request.getParameter("albumid");
		
		if(user!=null){
			user.getId();
			request.setAttribute("user", user);
			AlbumModel album=new AlbumModel();
			album.setUid(user.getId());
			
			List<AlbumModel> albumList=AlbumBo.albumList("*", album, null, null, null, null, -1, -1);
			if(albumList!=null&&albumList.size()>0){
				AlbumModel model=albumList.get(0);
				if(albumid==null){
					albumid=model.getId();
				}
			}
			
			AlbumDetailModel albumdetail=new AlbumDetailModel();
			albumdetail.setAlbumid(albumid);
			
			String page="1";
			
			List<AlbumDetailModel> albumdetailList=AlbumDetailBo.albumList("*", albumdetail, null, null, null, null, -1, -1);
			
			request.setAttribute("albumList", albumList);
			
			JSONArray array=JSONArray.fromObject(albumList);
			
			
			response.setContentType("application/x-json");  
	        PrintWriter pw = response.getWriter();  
	        pw.print(array.toString());
	        pw.close();
	        return null;
		}
		return null;
		
		
		//request.setAttribute("imgPath", Common.imgServerPath);
		
		
		//return new TemplateView("/albumshow.html");
		
	}
	@RequestMapping(value = "/albumdetailjson",method = {HttpMethod.GET, HttpMethod.POST})
	public View albumdetailjson(final HttpServletRequest request, HttpServletResponse response,@PathVariable String[] args) throws SQLException, IOException {
		
		HttpSession session=request.getSession();
		UserModel user=(UserModel) session.getAttribute("user");
		session.getAttribute("user");
		String albumid=request.getParameter("albumid");
		
		if(user!=null){
			user.getId();
			request.setAttribute("user", user);
			
			AlbumDetailModel albumdetail=new AlbumDetailModel();
			albumdetail.setAlbumid(albumid);
			
			String page="1";
			
			List<AlbumDetailModel> albumdetailList=AlbumDetailBo.albumList("*", albumdetail, null, null, null, null, -1, -1);
			
			JSONArray array=JSONArray.fromObject(albumdetailList);
			
			
			response.setContentType("application/x-json");  
	        PrintWriter pw = response.getWriter();  
	        pw.print(array.toString());
	        pw.close();
	        return null;
		}
		return null;
		
		
		//request.setAttribute("imgPath", Common.imgServerPath);
		
		
		//return new TemplateView("/albumshow.html");
		
	}
	
	@RequestMapping(value = "/albumsel",method = {HttpMethod.GET, HttpMethod.POST})
	public View albumselect(final HttpServletRequest request, HttpServletResponse response,@PathVariable String[] args) throws SQLException {
		
		HttpSession session=request.getSession();
		UserModel user=(UserModel) session.getAttribute("user");
		session.getAttribute("user");
		
	    String albumid=(String) request.getParameter("albumid");
	    
        if(user!=null){
        	try {
            	AlbumModel albumquery=new AlbumModel();
            	albumquery.setUid(user.getId());
            	
    			List<AlbumModel> albumlist=AlbumBo.albumList("*", albumquery, null, null, null, null, -1, -1);
    			if(albumlist!=null&&albumlist.size()>0){
    				AlbumModel album=albumlist.get(0);
    				if(albumid==null || "".equals(albumid)){
    					albumid=album.getId();
    				}
    				
    				request.setAttribute("albumlist", albumlist);
    				
    				AlbumDetailModel detailquery=new AlbumDetailModel();
    				detailquery.setAlbumid(albumid);
    				
    		       
    		        List<AlbumDetailModel> detaillist=AlbumDetailBo.albumList("*", detailquery, null, null, null, null, -1, -1);
    		        	
    		        	
    		        request.setAttribute("albumdetaillist", detaillist);
    			}
    		} catch (SQLException e1) {
    			// TODO Auto-generated catch block
    			e1.printStackTrace();
    		}
        }
        
		
		
		return new TemplateView("/selectAlbum.html");
		
	}
	
	@RequestMapping(value = "/prealbumup",method = {HttpMethod.GET, HttpMethod.POST})
	public View prealbumup(final HttpServletRequest request, HttpServletResponse response,@PathVariable String[] args) throws SQLException {
		
		HttpSession session=request.getSession();
		UserModel user=(UserModel) session.getAttribute("user");
		session.getAttribute("user");
		
		request.setAttribute("user", user);
		if(user!=null){
			System.out.println("uid="+user.getId());
			request.setAttribute("uid", user.getId());
			try {
				AlbumModel queryObj=new AlbumModel();
				queryObj.setUid(user.getId());
				
				List<AlbumModel> albumlist=AlbumBo.albumList("*", queryObj, null, null, null, null, -1, -1);
				request.setAttribute("albumlist", albumlist);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		//return new JspView("/jsp/upload.jsp");
		return new TemplateView("/albumupload.html");
		
	}
	
	@RequestMapping(value = "/prejs",method = {HttpMethod.GET, HttpMethod.POST})
	public View prejs(final HttpServletRequest request, HttpServletResponse response,@PathVariable String[] args) throws SQLException {
		
		
		return new JspView("/jsp/album.jsp");
		
	}
	
	@RequestMapping(value = "/albumup",method = {HttpMethod.GET, HttpMethod.POST})
	public View albumup(final HttpServletRequest request, HttpServletResponse response,@PathVariable String[] args) throws SQLException, IOException {
		
		String albumid=request.getParameter("albumid");
        HttpSession session=request.getSession();
        session.getAttribute("user");
        UserModel user=(UserModel)session.getAttribute("user");
        
        if(user!=null){
        	String savePath = request.getServletContext().getRealPath("/")+"uploads\\";

        	//System.out.println(" 路径"+savePath);

        	//DiskFileUpload upload = new DiskFileUpload();

        	PrintWriter out = response.getWriter();

        	DiskFileItemFactory fac = new DiskFileItemFactory();   

        	ServletFileUpload upload = new ServletFileUpload(fac);

        	//对于向上传文件大小控制等fac.setSizeThreshold(4096)最多允许在内存中存放4096个字节 这类请查apache 的 fileupload例子

        	       

        	//获取多个上传文件

        	List fileList = null;   

        	try {   
        	      //fileList = upload.parseRequest(request);  
        	      fileList = upload.parseRequest(request);

        	} catch (FileUploadException ex) {   

        	      System.out.println("没有上传文件");   

        	} 

        	if(fileList!=null){
        		//遍历上传文件写入磁盘

            	Iterator<FileItem> it = fileList.iterator();    

            	while(it.hasNext()){   

            	       FileItem item =  it.next();   

            	       if(!item.isFormField()){   

            	            String name = item.getName(); 

            	            if(name == null || name.trim().equals("") || item.getSize()==0.0)  { 

            	                continue;
            	            }else{
            	            	
            	            	name=name.substring(name.lastIndexOf("."));
            	            	name=user.getId()+"_"+Oid.getOid()+name;
            	            	
            	            	File hdf = new File(savePath +"hd\\");
            	        		//System.out.println(savePath);
            	        		if (!hdf.exists()) {
            	        		    hdf.mkdirs();
            	        		}
            	                File saveFile = new File(savePath +"hd\\"+name);   

            	                
        	        	        try {   
        	        	        	File bf = new File(savePath +"big\\");
                	        		//System.out.println(savePath);
                	        		if (!bf.exists()) {
                	        		    bf.mkdirs();
                	        		}
                	        		
                	        		File mf = new File(savePath +"medium\\");
                	        		//System.out.println(savePath);
                	        		if (!mf.exists()) {
                	        		    mf.mkdirs();
                	        		}
                	        		File sf = new File(savePath +"small\\");
                	        		//System.out.println(savePath);
                	        		if (!sf.exists()) {
                	        		    sf.mkdirs();
                	        		}
                	        		
        	        	              item.write(saveFile);  
        	        	              out.print(name);
        	        	              Thumbnails.of(savePath +"hd\\"+name)   
        	        	              .scale(0.25f)  
        	        	              .toFile(savePath +"big\\"+name);
        	        	              
        	        	              
        	        	              Thumbnails.of(savePath +"big\\"+name)   
        	        	              .size(500, 500)  
        	        	              .toFile(savePath +"medium\\"+name);
        	        	              
        	        	              Thumbnails.of(savePath +"medium\\"+name)   
        	        	              .size(75, 75)   
        	        	              //.keepAspectRatio(false)   
        	        	              .toFile(savePath +"small\\"+name);
        	        	              /*DwindlePic mypic = new DwindlePic();
        		      	              
        		      	                ImageUtils util=new ImageUtils();
        		      	                //if(b){
        		      	                	boolean testOut = util.zoomOutImage(savePath +"big\\"+name, savePath +"medium\\"+name, 2);
        		      	                	
        		      	                	boolean b= mypic.s_pic(savePath +"medium\\",
        			      	                		savePath +"small\\",
        			      	                        name, name, 75, 75, true);
        		      	                //}
        */	        	              String sql="insert into albumdetail (id,uid,albumid,name) values ('"+Oid.getOid()+"','"+user.getId()+"','"+albumid+"','"+name+"')";
        	        	              com.firefly.db.QueryHelper.update(sql);
        	
        	        	              response.getWriter().write("1");   
        	        	         } catch (Exception e) {   
        	
        	        	              e.printStackTrace();   
        	
        	        	         } 
            	            }
            	                  

            	       }   

            	}   
        	}
        }

    	
    	
		//return null;
        return new TextView("1");
		
	}
	
	@RequestMapping(value = "/albumsel",method = {HttpMethod.GET, HttpMethod.POST})
	public View albumsel(final HttpServletRequest request, HttpServletResponse response,@PathVariable String[] args) throws SQLException, IOException {
		
		request.setCharacterEncoding("UTF-8");
        
        String albumid=(String) request.getParameter("albumid");
        HttpSession session=request.getSession();
        session.getAttribute("user");
        UserModel user=(UserModel)session.getAttribute("user");
        
        if(user!=null){
	        request.setAttribute("uid", user.getId());
	        
	        //String name=request.getParameter("albumname");
	        //name=new String(name.getBytes("UTF-8"),"ISO-8859-1");  
	        //String uid=Oid.getOid();
	        String albumsql="select id,uid,name from album where uid = '"+user.getId()+"'";
	        System.out.println(albumsql);
	        try {
				List<AlbumModel> albumlist=com.firefly.db.QueryHelper.query(AlbumModel.class, albumsql);
				if(albumlist!=null&&albumlist.size()>0){
					AlbumModel album=albumlist.get(0);
					if(albumid==null || "".equals(albumid)){
						albumid=album.getId();
					}
					
					request.setAttribute("albumlist", albumlist);
					
					com.firefly.db.QueryHelper.update("set @x =0");
					
			        String albumdetailsql="select ((@x:=ifnull(@x,0)+1)-1)%5 as rownum,id,uid,albumid,name from albumdetail where albumid = '"+albumid+"'";
			        List<AlbumDetailModel> detaillist=com.firefly.db.QueryHelper.query(AlbumDetailModel.class, albumdetailsql);
			        request.setAttribute("albumdetaillist", detaillist);
				}
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} 
        }
		return new JspView("/jsp/selectphoto.jsp");
		
	}
	
	@RequestMapping(value = "/albumqadd",method = {HttpMethod.GET, HttpMethod.POST})
	public View albumqadd(final HttpServletRequest request, HttpServletResponse response,@PathVariable String[] args) throws SQLException, IOException {
		
		request.setCharacterEncoding("UTF-8");
        HttpSession session=request.getSession();
        
        UserModel user=(UserModel) session.getAttribute("user");
        session.getAttribute("user");
        String name=request.getParameter("albumname");
        String repath=request.getParameter("repath");
        if(user!=null){
        	user.getId();
        	//name=new String(name.getBytes("UTF-8"),"ISO-8859-1");  
            String uid=Oid.getOid();
            String sql="insert into album (id,name,uid) values ('"+uid+"','"+name+"','"+user.getId()+"')";
            try {
            	  if(name!=null&&!"".equals(name)&&name.trim().length()>0){
            		  com.firefly.db.QueryHelper.update(sql);
            	  }
    			
    		} catch (SQLException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		}
    		request.setAttribute("mess", uid);
        }
        
		//return new TemplateView("/"+repath);
        return new RedirectView("/"+repath);
	}
}
