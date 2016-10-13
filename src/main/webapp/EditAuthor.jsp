<%-- 
    Document   : EditAuthor
    Author     : David Arnell
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>

<td class="text-center" colspan = "2">Enter new Name:</td>

<td class="text-center" colspan = "3">
    <form action="AuthorController?action=editAuthorRecord" method="post">
        <input hidden name="authorId" value="${author.authorId}"/>
        <input hidden name="dateCreated" value="${author.dateAdded}"/>
        <input type="text"  name="authorName" placeholder="${author.authorName}"/>
        <input value="Confirm Edit" type="submit" name="authorEditButton" id="authorEditButton" >
    </form>
</td>


