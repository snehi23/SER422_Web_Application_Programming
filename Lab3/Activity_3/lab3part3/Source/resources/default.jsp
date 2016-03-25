<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Booktown Search</title>
</head>
<body>
<a href="author.jsp">to author page</a>&nbsp; &nbsp;
<a href="book.jsp">to book page</a>
<H2>Search Book Town DB</H2>
<form action="./booktown" method="get">
Author by ID: <input type="number" size="13" name="author_by_id"/>
<button type="submit">Search</button><br/></form><br/>
<form action="./booktown" method="get">
Book by ID: <input type="text" size="13" name="book_by_id"/>
<button type="submit">Search</button><br/></form><br/>
<form action="./booktown" method="get">
Books by Title: <input type="text" size="13" name="book_by_title"/>
<button type="submit">Search</button><br/></form><br/>

<p><c:out value="${requestScope.response_message}"/></p>

<H2>Results:</H2>
<H4>Author</H4>
<p><c:out value="${requestScope.authorEntry.firstName} ${requestScope.authorEntry.lastName}"/></p>

<H4>Book</H4>
<p><c:out value="${requestScope.bookEntry.title}"/></p>

<H4>Books by Title</H4>
<table>
<thead>
<tr>
   <th>Books</th>
   <th>Authors</th>
</tr>
</thead>
<tr>
<c:forEach items="${requestScope.book_with_authors}" var="bookmap">
 <tr> <td>${bookmap.key.title} </td>
 <td>
 <c:forEach items="${bookmap.value}" var="author">
 	<td>${author.firstName}  ${author.lastName} </td>
 </c:forEach>
 </td>
</c:forEach>
</table>

</body>
</html>