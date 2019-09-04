<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>LogOut</title>
</head>
<body>
<c:set var="memberName" value="${ LoginOK.name }" />

<c:remove var="LoginOK" scope="session" />
<c:remove var="ShoppingCart" scope="session" />

<c:set var="funcName" value="OUT" scope="session"/>

<jsp:include page="/fragment/top2.jsp" />

<c:set var="logoutMessage" scope="request"/>
<font color='blue' ><BR>
訪客${ memberName }，感謝您使用本系統。<BR>
您已經登出<BR>
</font>
<jsp:useBean id='logoutBean' class='_02_login.model.LogoutBean' scope='page' />
    
<c:set target='${logoutBean}' 
   property='session'    value='${pageContext.session}'/>
   
${ logoutBean.logout }

<c:redirect url="/index_p1.jsp"/>
</body>
</html>