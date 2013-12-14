<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ page import = "test.album.*"%>
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
<base target="_self"/>
<%
response.setHeader("Pragma","No-Cache");
response.setHeader("Cache-Control","No-Cache");
response.setDateHeader("Expires", 0);
%>
<script type="text/javascript" src="lazyload/jquery.js"></script>
<script type="text/javascript" src="lazyload/jquery.lazyload.js"></script>
<title>Upload</title>
<style type="text/css">
	body {
		width:100px;height;100px;font: 13px Arial, Helvetica, Sans-serif;
	}
	
	#queue1 {
		background-color: #FFF;
		border-radius: 3px;
		box-shadow: 0 1px 3px rgba(0, 0, 0, 0.25);
		height: 300px;
		margin-bottom: 10px;
		overflow: auto;
		padding: 5px 10px;
		width: 480px;
	}
	#queue2 {
		background-color: #FFF;
		border-radius: 3px;
		box-shadow: 0 1px 3px rgba(0, 0, 0, 0.25);
		height: 300px;
		margin-bottom: 10px;
		overflow: auto;
		padding: 5px 10px;
		width: 100px;
	}
	.selected {
      background: #0088cc; }
</style>
<style>

.lazy{overflow:scroll;border:1px solid #ccc;}
</style>

</head>

<body>
	<table>
		<tr>
			<td>
			相册：
				<form id="albumform" action="<%=path%>/iytx/albumsel" method="post" target="_self">
				<input type="hidden" name="uid" value="${uid }">
				<select id="albumid" name="albumid" onchange="changealbum(this)">
				<%
					List<AlbumModel> albumlist=(List<AlbumModel>)request.getAttribute("albumlist");
					if(albumlist!=null){
						for(AlbumModel am:albumlist){
				%>
							<option value="<%=am.getId() %>" selected="selected"><%=am.getName() %></option>
				<%
						}
					}
				%>
				
				</select>
				</form>
				<script type="text/javascript">
					function changealbum(v){
						document.getElementById("albumform").submit();
					}
					var aid="${albumid }";
					
					//document.getElementById("albumid").value="${albumid }";
				</script>
			</td>
			<td>
				<a href="">上移</a> <a href="">下移</a> <a href="">删除</a>
			</td>
		</tr>
		<tr>
			<td>
			<div id="queue1">
				<table>
					<tr>
					<%
						List<AlbumDetailModel> albumdetaillist=(List<AlbumDetailModel>)request.getAttribute("albumdetaillist");
						if(albumdetaillist!=null && albumdetaillist.size()>0){
							for(int i =0; i<albumdetaillist.size() ;i++){
								AlbumDetailModel adm=albumdetaillist.get(i);
								if(adm!=null){
					%>
								<td  style="width: 75px;height: 75px;" align="center" valign="top"><img id="<%=adm.getId() %>" name="<%=adm.getName() %>" style="border-width: thick;cursor:hand;" onclick="selected(this)" class="lazy" src="<%=basePath%>uploads/small/<%=adm.getName() %>"></td>
					<%
								if(i!=0 && adm.getRownum()==4){
								
					%>
									</tr><tr>
					<%
								
								}
								}
							}
						}
					%>
					</tr>
				</table>
				<script type="text/javascript">
					function selected(v){
						//alert("d"+v.style.borderColor+"f");
						if(v.style.borderColor==""){
							v.style.borderColor="red";
							
							addselected(v);
						}else{
							v.style.borderColor="";
							
						}
					}
					function reselected(vv){
						alert(vv.style.borderColor);
						if(vv.style.borderColor==""){
							vv.style.borderColor="red";
							//v.selected="true";
						}else{
							vv.style.borderColor="";
							//v.selected="";
						}
					}
					
					function addselected(v){
						//alert(v.src)
						var imgsrc='<img id="'+v.id+'" name="'+v.name+'"　style="border-width: thick;cursor:hand;" onclick="reselected(this)" src="'+v.src+'">';
						//alert(imgsrc);
						//document.getElementById("queue2").innerHTML.append("<img src='"+v.src+"'>")
						
						var tbl=document.getElementById("restable");
						var rn=tbl.rows.length;
															
						vTr=tbl.insertRow(rn);
						//vTr.id=arr[0]+"_"+rn;
						
						vTd=document.createElement("td");		
						vTd.height="75px";
						vTd.width="75px";
						vTd.align="center";
						//vTd.style.backgroundColor="#FFFFFF";
						//vTd.className="xxy_znbx";
						vTd.innerHTML=imgsrc;
						vTr.appendChild(vTd);
					}
				</script>
			</div>
			</td>
			<td>
				<div id="queue2">
					<table id="restable">
					
					</table>
				</div>
			</td>
		</tr>
		<tr>
			<td colspan="2" align="right">
				<input type="button" value="确定选择" onclick="selectok()">
			</td>
		</tr>
		
	</table>
	<script type="text/javascript">
		$(function() {
		    $("img.lazy").lazyload();
		});
		
		function selectok(){
			var tbl=document.getElementById("restable");
			var arr="";
			//alert(tbl.rows.length);
			for(var i=0;i<tbl.rows.length;i++){
				var id = tbl.rows[i].cells[0].children[0].id;
				var name = tbl.rows[i].cells[0].children[0].name;
				
				arr+='<img id="'+id+'" name="'+name+'" src="<%=basePath%>uploads/medium/'+name+'" /></br></br>';
			}
			
			returnValue=arr;
			window.close();
		}
	</script>
</body>
</html>