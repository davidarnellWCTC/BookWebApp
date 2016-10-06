<%-- 
    Document   : EditAuthor
    Author     : David Arnell
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<form action="AuthorController?action=addAuthorRecord" method="post">
    <table>
        <c:set var="a" items="${author}">
            <tr>                        
                <td>Author ID</td>
                <td width="35%" >Author Name</td>
                <td>Author Add Date</td>
            </tr>
            <tr>                        
                <td>${a.authorId}</td>
                <td id="authorName" ><input type="hidden" name="authorName" value="${a.authorName}"><input type="text"  name="authorName" placeholder="${a.authorName}"/></td>
                <td>${a.dateAdded}</td>
            </tr>  
            <!--/c:-->
        </table>
        <input value="Edit Author" type="submit" name="authorEditButton" id="authorEditButton" >
    </form>
