<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Author CRUD</title>
</head>
<body>
<a href="default.jsp">to search page</a>&nbsp; &nbsp;
<a href="book.jsp">to book page</a> <br> <br>
<form action="./author_crud" method="get">
ID: <input type="number" size="5" name="author_id"/>&nbsp;
First name: <input type="text" size="12" name="firstname"/>&nbsp;
Last name: <input type="text" size="13" name="lastname"/>&nbsp;
<select name="action">
    	<option value="add" selected="selected">add</option>
    	<option value="update">update</option>
    	<option value="delete">delete</option>
</select><br><br>
Hard delete associated book(s)?
<input type="radio" name="deleteBook" value="yes" /> Yes
<input type="radio" name="deleteBook" value="no" checked="checked" /> No

<br><br><button type="submit">submit</button><br/></form><br>

<p><c:out value="${requestScope.response_message}"/></p>
</body>
</html>