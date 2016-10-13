<%-- 
    Document   : EditAuthor
    Author     : David Arnell
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>

<th class="text-center"><td colspan = 2>Enter new Name:</td></th>
<th class="text-center"><td colspan = 3></td></th>
<th class="text-center">
    <form action="AuthorController?action=editAuthorRecord" method="post">
        <input hidden name="authorId" value="${author.authorId}"/>
        <input hidden name="dateAdded" value="${author.dateAdded}"/>
        <input type="text"  name="authorName" placeholder="New Author Name"/>
        <input value="Edit Author" type="submit" name="authorEditButton" id="authorEditButton" >
    </form>
</th>

