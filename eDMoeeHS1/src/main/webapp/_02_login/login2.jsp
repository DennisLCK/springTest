<!DOCTYPE html>
<html>
<head>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<meta charset="UTF-8">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">

<title>Login</title>
<style type="text/css">
#main {
	position:relative;
	top: 50px;
    width:100%;
    text-align:center;
}
#content {
  width: 500px ;
  margin-left: auto ;
  margin-right: auto ;
}
</style>
<script type="text/javascript">
function setFocusToUserId(){   
	 document.forms[0].userId.focus();   // 將游標放在userId欄位內
}
</script>
</head>

<body onLoad="setFocusToUserId()" style="background:#EBFFEB;">
<c:set var="funcName" value="LOG" scope="session"/>
<c:set var="msg2" value="影城員工登入" />
<c:if test="${ ! empty sessionScope.timeOut }" > <!-- 表示使用逾時，重新登入 -->
   <c:set var="msg2" value="<font color='red'>${sessionScope.timeOut}</font>" />
</c:if>
<jsp:include page="/fragment/top2.jsp" />

<Form action="<c:url value='login2.do' />" method="POST" name="loginForm">
	<div id='content' >
		<Table  style="text-align:center" class="jumbotron">
        	<TR>
             	<TD colspan='2' align="CENTER" style="font-size:0.6cm;font-weight: 300;"> 
                <Font color="#006600" face="標楷體">
                    ${AppName2}
                </Font>
             	</TD>
         	</TR>
         	<TR>
             <TD height='50' colspan='2' align="CENTER" style="font-size:0.5cm;font-weight: 300;"> 
                <Font color="#006600"  face="標楷體">
                    ${msg2}
                </Font>
             </TD>
         	</TR>
         	<TR>
             <TD width="180" align="right">帳號：　</TD>
             <TD width="180" colspan='2' align="LEFT">
             <input  type="text" name="userId" size="10" 
             value="${requestScope.user}${param.userId}">
             &nbsp;<small><Font color='red' size="-3">${ErrorMsgKey.AccountEmptyError}
             </Font></small></TD>
         	</TR>
         	<TR>
             <TD width="180" align="right">密碼：　</TD>
             <TD width="180" colspan='2' align="LEFT" >
             <input  type="password" name="pswd"  size="10" 
             value="${requestScope.password}${param.pswd}"> 
             &nbsp;<small><Font color='red'  size="-3">${ErrorMsgKey.PasswordEmptyError}
             </Font></small></TD>
             </TR>
             <TR height='10'>
             <TD align="CENTER" colspan='2'>&nbsp;<Font color='red' size="-1">
             ${ErrorMsgKey.LoginError}&nbsp;</Font></TD>
         	</TR>
             <TR>
             	<TD colspan="2" align="center"><input type="submit" value="提交"> </TD>
         	 </TR>
        </Table>
	</div>
</Form>
</body>
</html>