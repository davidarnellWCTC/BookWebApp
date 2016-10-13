<%-- 
    Document   : AddAuthor
    Author     : David Arnell
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<form action="AuthorController?action=newAuthorRecord" method="post">
    <input type="text"  name="authorName" placeholder="Author Name"/>
    <input value="Create Author" type="submit" name="authorCreateButton" id="authorCreateButton" >
</form>
