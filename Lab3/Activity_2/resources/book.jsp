<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Book CRUD</title>
</head>
<body>
<a href="default.jsp">to search page</a>&nbsp; &nbsp;
<a href="author.jsp">to author page</a> <br> <br>
<form action="./enlist_authors" method="get">
ISBN: <input type="text" size="5" name="book_id"/>&nbsp;
<button type="submit">get authors</button>
</form><br>
<form action="./book_crud" method="get">
ISBN: <input type="text" size="5" name="book_id" value="<c:out value="${requestScope.book_info.isbn}"/>"/>&nbsp;
PUBLISHER: <input type="text" size="12" name="publisher" value="<c:out value="${requestScope.book_info.publisher}"/>"/>&nbsp;
TITLE: <input type="text" size="13" name="title" value="<c:out value="${requestScope.book_info.title}"/>"/>&nbsp;
PUBLISH YEAR: <input type="text" size="13" name="publish_year" value="<c:out value="${requestScope.book_info.publishYear}"/>"/>&nbsp;
<select name="action">
    	<option value="add" selected="selected">add</option>
    	<option value="update">update</option>
    	<option value="delete">delete</option>
</select><br><br>
Choose Author(s) of book:&nbsp;
<select name="authors" multiple>
	<c:forEach items="${requestScope.author_list}" var="author1">
		<c:set var="contains" value="no" />
		<c:forEach items="${requestScope.authors_of_book}" var="author2">
			<c:if test="${author1.id == author2.id}">
					<c:set var="contains" value="yes" />
  			</c:if>
        </c:forEach>
        <c:choose>
        <c:when test="${contains == 'yes'}">
        	<option value="${author1.id}" selected="selected">${author1.firstName} ${author1.lastName}</option>
        </c:when>
        <c:otherwise>
  				<option value="${author1.id}">${author1.firstName} ${author1.lastName}</option>
  		</c:otherwise>
  		</c:choose>
    </c:forEach>
</select>&nbsp;&nbsp;<br><br>
Author Association:
<input type="radio" name="assoAuthor" value="create" checked="checked" /> Create
<input type="radio" name="assoAuthor" value="remove" /> Remove
<br><br>
Hard delete associated author(s)?
<input type="radio" name="deleteAuthor" value="yes" /> Yes
<input type="radio" name="deleteAuthor" value="no" checked="checked" /> No
<br><br><button type="submit">submit</button><br/></form><br>

<p><c:out value="${requestScope.response_message}"/></p>
</body>
</html>