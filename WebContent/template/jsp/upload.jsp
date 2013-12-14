<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ page import = "test.album.AlbumModel"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page import="java.util.List"%>
<html>
<head>
<base href="<%=basePath%>">
<title>Upload</title>

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
		width: 700px;
	}
</style>
</head>

<body>
	<form>
		<select id="albumid">
		<%
				List<AlbumModel> albumlist=(List<AlbumModel>)request.getAttribute("albumlist");
				if(albumlist!=null){
					for(AlbumModel am:albumlist){
			%>
						<option value="<%=am.getId() %>"><%=am.getName() %></option>
			<%
					}
				}
			%>
		</select>&nbsp;
		<a href="javascript:createAlbum();">新建相册</a></br>
		<a href="javascript:selectAlbum();">选择相册</a></br>
		<input id="file_upload" name="file_upload" type="file">
		<div id="queue"></div>
	</form>
        <script type="text/javascript">
		$(function() {
			$('#file_upload')
					.uploadify(
							{	
								'debug' 			: false,
								'auto' 				: false, //是否自动上传,   
								'buttonClass' 		: 'haha', //按钮辅助class   
								'buttonText' 		: '选择要上传的图片', //按钮文字   
								'height' 			: 20, //按钮高度   
								'width' 			: 120, //按钮宽度   
								//'checkExisting' 	: 'check-exists.php',//是否检测图片存在,不检测:false   
								'fileObjName'		: 'files', //默认 Filedata, $_FILES控件名称   
								'fileSizeLimit'		: '0', //文件大小限制 0为无限制 默认KB   
								'fileTypeDesc'		: '请选择图片文件', //图片选择描述   
								'fileTypeExts' 		: '*.gif; *.jpg; *.png',//文件后缀限制 默认：'*.*'   
								//'formData' 			: {
								//	'someKey' : 'someValue',
								//	'someOtherKey' : 1
								//},//传输数据JSON格式   
								//'formData'		    :{'albumid':'${albumid }' },                
								//'overrideEvents'	: ['onUploadProgress'],  // The progress will not be updated   
								//'progressData'	: 'speed',             //默认percentage 进度显示方式   
								'method'            : 'get',
								'queueID' 			: 'queue', //默认队列ID   
								'queueSizeLimit' 	: 20, //一个队列上传文件数限制   
								'removeCompleted'	: true, //完成时是否清除队列 默认true   
								'removeTimeout'		: 3, //完成时清除队列显示秒数,默认3秒   
								'requeueErrors'		: false, //队列上传出错，是否继续回滚队列   
								'successTimeout'	: 3, //上传超时   
								'uploadLimit' 		: 99, //允许上传的最多张数   
								'swf' 				: 'uploadify.swf', //swfUpload   
								'uploader' 			: '<%=path%>/iytx/albumup', //服务器端脚本   
								
								'multi'             : true,
								//修改formData数据   
								'onUploadStart' : function(file) {
									//$("#file_upload").uploadify("settings", "someOtherKey", 2);   
								},
								'onUploadStart' : function(file) {
									//alert("a:"+document.getElementById("albumid").value);
									$('#file_upload').uploadify("settings","formData",{"albumid":$("#albumid").val()});    
								},
								//删除时触发   
								'onCancel' : function(file) {
									//alert('The file ' + file.name + '--' + file.size + ' was cancelled.');   
								},
								//清除队列   
								'onClearQueue' : function(queueItemCount) {
									//alert(queueItemCount + ' file(s) were removed from the queue');   
								},
								//调用destroy是触发   
								'onDestroy' : function() {
									alert('我被销毁了');
								},
								//每次初始化一个队列是触发   
								'onInit' : function(instance) {
									//alert('The queue ID is ' + instance.settings.queueID);   
								},
								//上传成功   
								'onUploadSuccess' : function(file, data,
										response) {
									//alert(file.name + ' | ' + response + ':' + data);   
									if(""!=$('#queue').innerHTML){
										$('#file_upload').uploadify('upload');
									}
									
								},
								//上传错误   
								'onUploadError' : function(file, errorCode,
										errorMsg, errorString) {
									//alert('The file ' + file.name + ' could not be uploaded: ' + errorString);   
								},
								//上传汇总   
								'onUploadProgress' : function(file,
										bytesUploaded, bytesTotal,
										totalBytesUploaded, totalBytesTotal) {
									//$('#progress').html(
									//		totalBytesUploaded
									//				+ ' bytes uploaded of '
									//				+ totalBytesTotal
									//				+ ' bytes.');
								},
								//上传完成   
								'onUploadComplete' : function(file) {
									alert('上传完成');   
								},

							});
		});

		//变换按钮   
		function changeBtnText() {
			$('#file_upload').uploadify('settings', 'buttonText', '继续上传');
		}

		//返回按钮   
		function returnBtnText() {
			alert('The button says '
					+ $('#file_upload').uploadify('settings', 'buttonText'));
		}
        </script>
		<h4>操作:</h4>
		<a href="javascript:$('#file_upload').uploadify('upload');">开始上传</a>
		|
		<a href="javascript:$('#file_upload').uploadify('cancel', '*');">清除队列</a>
		|
		<!--<a href="javascript:$('#file_upload').uploadify('destroy');">销毁上传</a> |
		<a href="javascript:$('#file_upload').uploadify('disable', true);">禁用上传</a>
		|
		<a href="javascript:$('#file_upload').uploadify('disable', false);">激活上传</a>
		|
		--><a href="javascript:$('#file_upload').uploadify('stop');">停止上传</a><!-- |
		<a href="changeBtnText();">变换按钮</a> |
		-->
		<!--<h4>大小:</h4>
		<div id='progress'></div>
		
		--><script type="text/javascript">
			function createAlbum(){
				var url="${pageContext.request.contextPath}/iytx/prejs";
				var str=window.showModalDialog(url,'','dialogWidth =400px;dialogHeight =300px;status=no;resizable=no;');
				if(str!=null){
					//var uuid=str[0];
					//var company=str[1];
					//fcb(uuid,company);
					window.location.href.reload();
					document.getElementById("albumid").value=str;
				}
			}
			function selectAlbum(){
				var url="${pageContext.request.contextPath}/album/select?uid=${uid }";
				var str=window.showModalDialog(url,'','dialogWidth =670px;dialogHeight =550px;status=no;resizable=no;');
				if(str!=null){
					var arr=str;
					alert(arr);
					//var uuid=str[0];
					//var company=str[1];
					//fcb(uuid,company);
					//window.location.reload();
					//document.getElementById("albumid").value=str;
				}
			}	
			
			
		</script>
</body>
</html>