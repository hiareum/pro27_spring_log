<%@ page language="java" contentType="text/html; charset=EUC-KR"
	pageEncoding="EUC-KR"%>

<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
  request.setCharacterEncoding("UTF-8");
%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>���</title>
<style type="text/css">
a {
	text-decoration: none;
	color: black;
}
</style>
</head>
<body>
	<table border=0 width="100%">
		<tr>
			<td width="15%"><a href="${contextPath}/main.do">
					<h1>a-reum</h1>
			</a></td>
			 <td width="75%">
				<!-- <h1>
					<font color="#999999">�������ǽ� Ȩ������ �Դϴ�</font>
				</h1>
 -->
			</td>
 
			<td>
				<!-- <a href="#"><h3>�α���</h3></a> --> <c:choose>
					<c:when test="${isLogOn == true  && member!= null}">
						<h3>ȯ���մϴ�. ${member.name }��!</h3>
						<a href="${contextPath}/member/logout.do"><h3>�α׾ƿ�</h3></a>
					</c:when>
					<c:otherwise>
						<a href="${contextPath}/member/loginForm.do"><h3>�α���</h3></a>
					</c:otherwise>
				</c:choose>
			</td>
		</tr>
	</table>


</body>
</html>