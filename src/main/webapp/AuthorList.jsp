<%-- 
    Document   : AuthorList
    Author     : David Arnell
--%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>

        <!-- Latest compiled and minified CSS -->
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">

        <!-- Optional theme -->
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap-theme.min.css" >
        <title>Author List</title>
    </head>
    <body>


        <div id="content">
            <h1>Complete Author List</h1>

            <h3>Authors in the Author List</h3>

            <%
                //checks to see if there is a valid author list and prints the list
                Object authorList = request.getAttribute("authorList");

                if (authorList == null) {
                    //out.println(request.getAttribute("errorMessage"));
                    out.println("Author List not found.");
                }
            %>

            <table style="width:75%">
                <tr>
                    <th>Author Name</th>
                    <th>Author ID</th>
                    <th>Add Date</th>
                </tr>
                <!-- Getting error that there is no "author id" -->
                <!-- Fixed error, misspelled authorId -->
                <c:forEach var="author" items="${authorList}">
                    <tr>
                        <th>${author.authorName}</th>
                        <th>${author.authorId}</th>
                        <th>${author.dateAdded}</th>
                    </tr>
                </c:forEach>            
            </table>
        </div>

        <!-- Latest compiled and minified JavaScript -->
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    </body>
</html>
