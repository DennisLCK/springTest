<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">

<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/eDM.css" type="text/css" />
<script
	src="${pageContext.request.contextPath}/javascript/jquery-1.9.1.js"></script>
<script src="${pageContext.request.contextPath}/javascript/eDM.js"></script>
<c:set var='debug' value='true' scope='application' />
<c:set var="memberName" value="${ LoginOK.name }" />
<table class='menuOuter'>
	<tr height="48px">
		<td width="60px" rowspan='2'><img width="60px" height="40px"
			src="${pageContext.request.contextPath}/images/vscinemasbaby.png">
		</td>
		<td>
			<table class='menuInner'>
				<tr>
					<td class='menuData'>
						<div class='menu'>
							<c:if test="${empty LoginOK}">
								<a href="<c:url value='/_02_login/login2.jsp' />">員工登入 </a>
							</c:if>
							<c:if test="${!empty LoginOK}">
								${ memberName } 歡迎
							</c:if>
						</div>
					</td>
					<td class='menuData'>
						<div class='menu'>
							<c:if test="${ funcName != 'REG' }">
								<a href="<c:url value='/_01_register/register2.jsp' />">增加員工
								</a>
							</c:if>
							<c:if test="${ funcName == 'REG' }"> 
                        	增加員工
            			</c:if>
						</div>
					</td>
					<td class='menuData'>
						<div class='menu'>
							<c:if test="${ funcName != 'EMP' }">
								<a href="<c:url value='/_01_register/queryMember.do' />">員工清單 </a>
							</c:if>
							<c:if test="${ funcName == 'EMP' }">員工清單
              				</c:if>
						</div>
					</td>
					<td class='menuData'>
						<div class='menu' style='width: 50px;'>
							<c:if test="${ funcName != 'IND' }">
								<a href="<c:url value='/index_p1.jsp' />"> 回首頁 </a>
							</c:if>
						</div>
					</td>
					<td class='menuData'>
						<div class='menu'>
							<c:if test="${ ! empty LoginOK }">
								<a href="<c:url value='/_02_login/logout2.jsp' />"> 登出 </a>
							</c:if>
						</div>
					</td>
				</tr>
			</table>
		</td>
	</tr>
</table>