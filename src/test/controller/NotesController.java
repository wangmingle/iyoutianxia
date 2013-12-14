package test.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import test.notes.NotesBo;
import test.notes.NotesModel;
import test.usr.UserModel;

import com.firefly.annotation.Controller;
import com.firefly.annotation.HttpParam;
import com.firefly.annotation.RequestMapping;
import com.firefly.mvc.web.HttpMethod;
import com.firefly.mvc.web.View;
import com.firefly.mvc.web.view.JsonView;
import com.firefly.mvc.web.view.RedirectView;
import com.firefly.mvc.web.view.TemplateView;
import com.firefly.utils.json.Json;

@Controller
public class NotesController {
	@RequestMapping(value = "/notes")
	public View notes(HttpServletRequest request, HttpServletResponse response) throws SQLException {
		HttpSession session = request.getSession();
		session.getAttribute("user");
		UserModel user=(UserModel) session.getAttribute("user");
		
		request.setAttribute("user", user);
		NotesModel notes=new NotesModel();
		request.setAttribute("notelist", NotesBo.notesList("*",notes , null, null, null, null, -1, -1));
		
		
		return new TemplateView("/notes/notelist.html");
	}
	
	
	@RequestMapping(value = "/notepub",method = {HttpMethod.GET, HttpMethod.POST})
	public View pub(HttpServletRequest request, HttpServletResponse response,@HttpParam("notes") NotesModel note) {
		HttpSession session = request.getSession();
		session.getAttribute("user");
		UserModel user=(UserModel) session.getAttribute("user");
		
		
		request.setAttribute("user", user);
		
		String noteFlag = request.getParameter("notesFlag");
		if(noteFlag!=null){
			if(user!=null){
				note.setUid(user.getId());
				note.setCreatedate((new Date()).getTime());
				
				NotesBo.addNotes(note);
			}
			
			return new RedirectView("/notes");
		}else{
			
			
			return new TemplateView("/notes/addnotes.html");
		}
	}
	
	@RequestMapping(value = "/notes/test",method = {HttpMethod.GET, HttpMethod.POST})
	public View test(HttpServletRequest request, HttpServletResponse response,@HttpParam("notes") NotesModel note) {
		HttpSession session = request.getSession();
		session.getAttribute("user");
		UserModel user=(UserModel) session.getAttribute("user");
		
		
		request.setAttribute("user", user);
		
		String noteFlag = request.getParameter("notesFlag");
		if(noteFlag!=null){
			if(user!=null){
				note.setUid(user.getId());
				note.setCreatedate((new Date()).getTime());
				
				NotesBo.addNotes(note);
			}
			
			return new RedirectView("/notes");
		}else{
			
			
			return new TemplateView("/notes/addnotes.html");
		}
	}
	
	@RequestMapping(value = "/notered",method = {HttpMethod.GET, HttpMethod.POST})
	public View read(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		session.getAttribute("user");
		UserModel user=(UserModel) session.getAttribute("user");
		
		String id=request.getParameter("id");
		
		System.out.println("----"+id);
		NotesModel notes=new NotesModel();
		notes.setId(id);
		
		try {
			NotesModel note=NotesBo.singleModel(notes);
			String context=note.getContext();
			context=context.replace( "&quot;","\"").replace("&#039;","'");
			note.setContext(context);
			request.setAttribute("note", note);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		/*try {
			NotesModel note=NotesBo.singleModel(notes);
			
			request.setAttribute("user", user);
			
			response.setCharacterEncoding("UTF-8");
			response.setHeader("Content-Type", "application/json; charset=UTF-8");
			PrintWriter writer = response.getWriter();
			try{
				writer.print(Json.toJson(note));
			} finally {
				writer.close();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		return new TemplateView("/notes/read.html");
	}
}
