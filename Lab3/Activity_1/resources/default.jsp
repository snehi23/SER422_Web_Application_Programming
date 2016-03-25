<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Booktown Servlet</title>
</head>
<body>
<H2>Authors:</H2>
<p><c:out value="${requestScope.response_message}"/> &nbsp; <c:out value="${requestScope.author_id}"/></p><br>
<table>
<c:forEach items="${requestScope.authorsList}" var="author">
 <tr> <td>${author.__firstName} </td>
 <td> ${author.__lastName} &nbsp; </td>
 <td><a href="./booktown?action=delete&authorid=${author.__id}">delete</a> </td></tr>
</c:forEach>
</table>

<p><form action="./booktown" method="get">
<input type="hidden" name="action" value="create"/>
Last name: <input type="text" size="13" name="lastname"/><br/>
First name: <input type="text" size="12" name="firstname"/><br/>
<button type="submit">Create author</button><br/></form><br/>
</body>
</html>