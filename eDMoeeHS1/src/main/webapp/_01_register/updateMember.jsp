<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>	
<!DOCTYPE HTML>
<html>
<head>
<style>
</style>
<link rel='stylesheet' href='${pageContext.request.contextPath}/css/style.css'  type="text/css" />
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">

<meta charset="UTF-8">
<title>MVC</title>
<script type="text/javascript">
  function confirmDelete(userId){
	  var result = confirm("確定刪除此筆記錄(帳號:" + userId + ")?");
	  if (result) {
		  document.forms[0].finalDecision.value = "DELETE";
	      return true;
	  }
	  return false;
  }
  function confirmUpdate(userId){
	  var result = confirm("確定送出此筆記錄(帳號:" + userId + ")?");
	  if (result) {
		  document.forms[0].finalDecision.value = "UPDATE";
	      return true;
	  }
	  return false;
  }
</script>
</head>
<body>
<jsp:include page="/fragment/top2.jsp" />
<p>&nbsp;</p>
<hr>
<div style="text-align:center" class="jumbotron"  >
<H1 class='center'>更新員工資料</H1>
<hr>
<p> 
<Form class='center' Action="updateMember.do" method="POST" >
    
    <input type="hidden" name="pk"     value="${mb.pkey}" >
    <input type="hidden" name="id"     value="${mb.memberId}${param.memberId}" >
    <input type="hidden" name="finalDecision" value="" > 
    <Table  style='margin:auto'>
         <TR>
             <TD align="RIGHT">帳號：</TD>
             <TD align="LEFT">${mb.memberId}${param.memberId}</TD>
         </TR>
         <TR>
             <TD align="RIGHT">姓名：</TD>
             <TD align="LEFT" >
                <input type="text" name="name" value="${mb.name}${param.name}"  size="30">
                <font color='red' size='-3'>${error.name}</font>
             </TD>
         </TR> 
                        
<!--          <TR> -->
<!--              <TD align="RIGHT">密碼：</TD> -->
<!--              <TD align="LEFT" >  -->
<%--                <input type="text" name="pswd" value="${mb.password}${param.password}"> --%>
<%--                <font color='red' size='-3'>${error.password}</font> --%>
<!--              </TD> -->
<!--          </TR>  -->
         
         <TR>
             <TD align="RIGHT">地址：</TD>
             <TD align="LEFT" > 
               <input type="text" name="address" value="${mb.address}${param.address}">
               <font color='red' size='-3'>${error.address}</font>
             </TD>
         </TR>             

        <TR>
            <TD colspan="2" align="center">     
            <input type="submit" value="更新" name='updateBtn' onclick="return confirmUpdate('${mb.memberId}');"> 
            <input type="submit" value="刪除" name='deleteBtn' onclick="return confirmDelete('${mb.memberId}');" >
            </TD>
            </TR>
         </Table>
         <c:if test="${not empty requestScope.modify}">   
           <c:remove var="member" scope="request" />       
         </c:if>
</Form>
<p/>
<small>&lt;&lt;<a href="queryMember.do">回上一頁</a>&gt;&gt;</small>
</div>
</body>

</html>
