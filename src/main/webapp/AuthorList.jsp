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
        <!--meta http-equiv="Content-Type" content="text/html; charset=UTF-8"-->
        <!-- Latest compiled and minified CSS -->
        <!--link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css" integrity="sha384-1q8mTJOASx8j1Au+a5WDVnPi2lkFfwwEAa8hDDdjZlpLegxhjVME1fgjWPGmkzs7" crossorigin="anonymous"-->

        <!-- Optional theme -->
        <!--link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap-theme.min.css" integrity="sha384-fLW2N01lMqjakBkx3l/M9EahuwpSfeNvV63J5ezn3uZzapT0u7EYsXMjQV+0En5r" crossorigin="anonymous"-->

        <title>Author List</title>
    </head>
    <body>
        <h1>Hello World!</h1>
        
        <div id="content">
            <h1>Complete Author List</h1>

            <h3>Authors in the Author List</h3>

            <%
                //checks to see if there is a valid author list and prints the list
                Object authorList = request.getAttribute("authorList");

                if (authorList == null) {
                    out.println(request.getAttribute("errorMessage"));
                    //out.println("test");
                }
            %>

            <table style="width:75%">
                <tr>
                    <th>Author Name</th>
                    <th>Author ID</th>
                    <th>Add Date</th>
                </tr>
                <c:forEach var="author" items="${authorList}">
                    <tr>
                        <th>${author.authorName}</th>
                        <th>${author.authorId}</th>
                        <th>${author.dateAdded}</th>
                    </tr>
                </c:forEach>            
            </table>
        </div>
    </body>
</html>
