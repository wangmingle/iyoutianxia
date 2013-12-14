package test.chat;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.LinkedBlockingQueue;

import javax.servlet.AsyncContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import net.sf.json.JSONObject;

import org.apache.commons.lang.time.DateFormatUtils;

public class NewUserListenser implements ServletContextListener {

	public static final BlockingQueue BLOG_QUEUE = new LinkedBlockingQueue();
	public static final Queue ASYNC_AJAX_QUEUE = new ConcurrentLinkedQueue();
	 
	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		System.out.println("context is destroyed!");
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		System.out.println("context is initialized!");
		  // 启动一个线程处理线程队列
		new Thread(runnable).start();
	}
	
	private Runnable runnable = new Runnable() {
		 public void run() {
			 boolean isDone = true;
			 while (isDone) {
				 if (!BLOG_QUEUE.isEmpty()) {
					 try {
						 //log.info("ASYNC_AJAX_QUEUE size : "
						//		 + ASYNC_AJAX_QUEUE.size());
						 MicUser blog = BLOG_QUEUE.take();
						 if (ASYNC_AJAX_QUEUE.isEmpty()) {
							 continue;
						 }
						 String targetJSON = buildJsonString(blog);

						 for (AsyncContext context : ASYNC_AJAX_QUEUE) {
							 if (context == null) {
								 //log.info("the current ASYNC_AJAX_QUEUE is null now !");
								 continue;
							 }
							 //log.info(context.toString());
							 PrintWriter out = context.getResponse().getWriter();

							 if (out == null) {
								// log.info("the current ASYNC_AJAX_QUEUE's PrintWriter is null !");
								 continue;
							 }

							 //out.println(context.getRequest().getParameter(
							//		 "callback")
							//		 + "(" + targetJSON + ");");
							 out.flush();

							 // 通知，执行完成函数
							 context.complete();
						 }
					 } catch (Exception e) {
						 e.printStackTrace();
						 isDone = false;
					 }
				 }
			 }
		 }};
	
		private static String buildJsonString(MicBlog blog) {
			Map info = new HashMap();
			info.put("state", 1);
			info.put("content", blog.getContent());
			info.put("date",
			DateFormatUtils.format(blog.getPubDate(), "HH:mm:ss SSS"));
	
			JSONObject jsonObject = JSONObject.fromObject(info);
	
			return jsonObject.toString();
		}
}
