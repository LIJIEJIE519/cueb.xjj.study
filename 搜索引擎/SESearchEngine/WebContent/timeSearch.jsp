<%@page import="java.util.List"%>
<%@page import="cn.edu.cueb.search.util.TimeSearch"%>
<%@page import="cn.edu.cueb.search.util.Info"%>
<%@page import="java.util.Map.Entry"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.ArrayList"%>
<%@page import="cn.edu.cueb.search.util.QueryIndex"%>
<%@page import="cn.edu.cueb.search.util.CharacterHandle"%>
<%@page import="cn.edu.cueb.search.util.SimilarityWord" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>首都经济贸易大学搜索引擎</title>
	
	<script type="text/javascript" src="<%=basePath%>js/jquery-1.10.2.min.js"></script>
    <script type="text/javascript" src="<%=basePath%>js/kkpager/kkpager.js"></script>
    <script type="text/javascript" src="<%=basePath%>js/index.js"></script>
    
    <link rel="stylesheet" type="text/css" href="<%=basePath%>js/kkpager/kkpager_blue.css">
    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/search.css">
    
</head>
<body>
	<div id="inf" class="inf" style="visibility: visible">Loading...</div>
	<div class="inf">
		<%
			// 请求的参数
			String parm = request.getParameter("keys");
			// 转换格式
			parm = new String(parm.getBytes("utf-8"), "utf-8");
		%>
		<a href="/SESearchEngine"><img class="logo" alt="" src="<%=basePath%>images/logo01.png"/></a>
		<form action="indexSearch.jsp" method="GET">
			<input class="keys" value="<%out.print(parm); %>" type="text" name="keys" size=36 class=kw maxlength="1000">&nbsp;&nbsp;
			<input type="submit" value="搜  索" class="seaBtn">
		</form>
		<hr class="line"/>
	</div>
	
	<div class="navi">
		<ul class="web">
			<li>
				<a href="/SESearchEngine/indexSearch.jsp?keys=<% out.print(parm); %>" style="color:#6E6E6E;"><h4>网页搜索</h4></a>
			</li>
			<li>
				<a href="#" style="color:#6E6E6E;"><h4>时间搜索</h4></a>
			</li>
		</ul>
				
	</div>
	<!-- 结果 -->
	<div id="result" class="result">
		<%
			// 提取关键字
			String keys = CharacterHandle.trans(parm);
			String[] results = new QueryIndex().getQueryResult(keys);
			Map<String, Info> hMap = new HashMap<>();
			
			for(int i = 0; i < results.length; i++) {
				int position = results[i].indexOf("|");
				String title = results[i].substring(0, position);
				
				position = results[i].indexOf("mirror");
				String url = results[i].substring(position+7, results[i].indexOf("|", position+1));
				
				position = results[i].indexOf("|", position+1);
				String context = results[i].substring(position+1);
				
				context = context.replace(" ", "");
				context = context.replace("&nbsp;", " ");
				String summary = "";
				String[] k = keys.split(" ");
				int maxChar = 100;
				int l = k.length;
				int summaryStart = 0;
				int summaryEnd = 0;
				/* 单关键字 */
				if(l == 1) {
					summaryStart = context.indexOf(k[0])+k[0].length();
					summaryEnd = summaryStart+maxChar;
					if(summaryEnd > context.length()) {
						summaryEnd = context.length();
					}
					summary = context.substring(summaryStart, summaryEnd);
					summary = "<font color=\"#FF0000\">"+k[0]+"</font>"+summary;
				}
				position = results[i].indexOf("发布日期");
				String time = "2009-06-01";
				if(position > 1) {
					time = results[i].substring(position+5, position+15);
					time = time.trim();
					//System.out.println(time);
				}
				
				Info info = new Info(url, summary, time);
				hMap.put(title, info);
			}
			
			List<Map.Entry<String, Info>> list = TimeSearch.sort(hMap);
			
			int length = hMap.size();
			// 显示记录的起始序列
			int start = 1;
			int end = 0;
			// 获得参数页面
			if(request.getParameter("page") != null) {
				start = Integer.parseInt(request.getParameter("page"));
			}
			start = (start-1)*10;
			end = start+10;
			if(end >= length) {
				end = length;
			}
			int i = start;
			int j = 0;
			//for(Entry<String, Info> info : hMap.entrySet()) {
			for(Map.Entry<String,Info> info : list) {
				
				/* 为了遍历 */
				if(j < i) {
					j++;
					continue;
				}else{
					if(i == end)break;
					out.print("<a href=\"http://"+info.getValue().getUrl()+"\">"+"<font color=\"#003c00\">"+info.getKey()+"</a>"+"</font><br/>");
					out.print("<font color=\"#008000\">"+info.getValue().getSummary()+"<a href=\"http://"+info.getValue().getUrl()+"\">"+"<font color=\"#003c00\">more</a>"+"</font>...</font><br/>");
					out.print("<font color=\"#cccccc\">"+info.getValue().getUrl()+"</font><font class=\"time\" color=\"#9F79EE\">"+info.getValue().getTime()+"</font><br/><br/><br/>");
					i++;
				}
			}
		%>
	</div>
	
	<div class="simWord">
		<h4>相似推荐</h4>
		<%
			// 相识关键字
			ArrayList<String> simWords = new SimilarityWord().findSimilarity(keys.split("")[0]+keys.split("")[1]);
			
			if(simWords.size() > 0) {
				// 遍历相识字
				for(String simWord : simWords) {
					// 获得搜索结果
					results = new QueryIndex().getQueryResult(simWord);
					// 至少2条
					if(results.length > 1) {
						Map<String, String> hashMap = new HashMap<String, String>();
						for(String simInfo : results) {
							int position = simInfo.indexOf("|");
							String title = simInfo.substring(0, position);
							position = simInfo.indexOf("mirror");
							String url = simInfo.substring(position+7, simInfo.indexOf("|", position+1));
							
							if(title.length() > 0) {
								hashMap.put(title, url);
							}
							
						}
						out.print("<div class=\"simkey\"><h5>key:		"+simWord+"</h5></div>");
						out.print("<ul class=\"simTab\">");
						i = 0;
						for(Entry<String, String> entry : hashMap.entrySet()) {
							if(i == 4)break;
							
							out.print("<li><a href=\"http://"+entry.getValue()+"\">"+entry.getKey()+"</a></li>");
							i++;
						}
						out.print("</ul><br></br>");
					}
				}
			}
		%>
	</div>

	<!-- 竖隔线 -->
	<table class="Vline">
    </table>

	<!-- 页面页数容器 -->
	<div class="pageConter">
		<div id="kkpager"></div>
	</div>
	
	 <!-- 显示结果 -->
	<script type="text/javascript">
		document.getElementById("inf").style.visibility="hidden";
		document.getElementById("result").style.visibility="visible";
		
	</script>
	
	<!-- 分页函数 -->
	<script type="text/javascript">
	/* 除了数字必须加上引号 */
		page(<% out.print(length/10 + 1); %>, 
		<% out.print(length); %>,"<% out.print(parm); %>")
	</script>
    
</body>
</html>