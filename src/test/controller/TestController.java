package test.controller;

import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import test.aa.TestBo;
import test.aa.TestModel;
import test.usr.UserModel;

import com.firefly.annotation.Controller;
import com.firefly.annotation.RequestMapping;
import com.firefly.mvc.web.HttpMethod;
import com.firefly.mvc.web.View;
import com.firefly.mvc.web.view.TemplateView;
import com.firefly.mvc.web.view.TextView;

@Controller
public class TestController {

	@RequestMapping(value = "/test",method = {HttpMethod.GET, HttpMethod.POST})
	public View file(HttpServletRequest request, HttpServletResponse response) throws SQLException {
		TestBo bo=new TestBo();
		TestModel testModel=new TestModel();
		testModel.setName("test");
		
		//System.out.println("ddddddddddddddddddddddddd");
		String idString=bo.addModel(testModel);
		
		//return null;
		//return new TemplateView()
		return new TextView(idString);
		//return new TemplateView("/notest/read.html");
		
	}
}
