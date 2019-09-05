<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix='c' uri='http://java.sun.com/jsp/jstl/core' %>
<!DOCTYPE html>
<html>
<head>
<link rel='stylesheet' href='${pageContext.request.contextPath}/css/style.css' />
<meta charset="UTF-8">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">

<title>會員資料</title>
</head>
<body>
<jsp:include page="/fragment/top2.jsp" />
<p>&nbsp;</p>
<hr>
<div style="text-align:center" class="jumbotron" >
	<h2>員工資料</h2>
	<c:if test='${empty allMembers}'>
		查無員工資料<br>
	</c:if>
	<c:if test='${not empty allMembers}'>
	
		<c:forEach var='member' varStatus='vs' items='${allMembers}'>
		
			<c:if test ='${vs.first }'>
				<c:out value="<div class='jumbotron' ><table border='1' style='margin:auto'>" escapeXml='false'/>
				<c:out value="<tr><td>帳號</td><td>員工名稱</td><td>address</td><td>email</td><td>電話</td><td></td></tr>" escapeXml='false'/>
				
			</c:if>
			
			<tr>
				<td>${member.memberId}</td>
				<td>${member.name}</td>
				<td>${member.address}</td>
				<td>${member.email}</td>
				<td>${member.tel}</td>
				
				<td><a href='findMember.do?key=${member.pkey}'>更改員工資料</a></td>
			</tr>
			<c:if test ='${vs.last }'>
				<c:out value="</table></div>" escapeXml='false'/>
			</c:if>
		</c:forEach>
	</c:if>
<p/>
<hr>
<a href='<c:url value='/index_p1.jsp' />'>回到首頁</a>
</div>
</body>
</html>