<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">
<base target="_self"/>
<%
response.setHeader("Pragma","No-Cache");
response.setHeader("Cache-Control","No-Cache");
response.setDateHeader("Expires", 0);
%>
<title>新建相册</title>

<!--装载文件-->
<link href="uploadify/uploadify.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="uploadify/jquery-1.4.2.min.js"></script>
<script type="text/javascript" src="uploadify/swfobject.js"></script>
<script type="text/javascript" src="uploadify/jquery.uploadify.js"></script>
<style type="text/css">
	body {
		font: 13px Arial, Helvetica, Sans-serif;
	}
	
	.haha {
		color: #FFFFFF;
	}
	
	#queue {
		background-color: #FFF;
		border-radius: 3px;
		box-shadow: 0 1px 3px rgba(0, 0, 0, 0.25);
		height: 500px;
		margin-bottom: 10px;
		overflow: auto;
		padding: 5px 10px;
		width: 500px;
	}
</style>
</head>

<body>
	<form target="_self" action="<%=basePath%>iytx/albumqadd" method="post">
		相册名称：<input type="text" name="albumname" id="albumname" />
		<input type="submit" value="确定">
	</form>
	<script type="text/javascript">
		var uid="${mess }"+"";
		if(uid!=""){
			returnValue=uid;
			//window.opener.location.reload();
			//window.dialogArguments.location.reload();
			window.close();
		}
	</script>
</body>
</html>