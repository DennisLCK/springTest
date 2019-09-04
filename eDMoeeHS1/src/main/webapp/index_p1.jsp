<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:useBean id="SYSTEM" class="_00_init.util.GlobalService" scope="application"/>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">

<c:set var="AppName2" value="${SYSTEM.systemName2}" scope="application"/>
<title>${AppName2}</title>
</head>
<body>
<c:set var="funcName" value="IND" scope="session"/>
<jsp:include page="/fragment/top2.jsp" />
<div style="text-align:center" class="jumbotron">
 <h1>
 EEIT108 後台<BR><FONT COLOR='RED'>${AppName2}</FONT>
 </h1> 
 <h2>
 管理後臺介面
 </h2>
 <HR>
   ${logoutMessage}${MsgOK.InsertOK}<BR>
</div>    
</body>
</html>