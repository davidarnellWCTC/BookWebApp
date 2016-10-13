<%-- 
    Document   : Home
    Author     : David Arnell
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Home Page</title>
    </head>
    <body>
        <h1>Book Web App</h1>
        
        <p>View all the Authors</p>

        <div>
            <form method="POST" action="${pageContext.request.contextPath}/AuthorController?action=getAllAuthorRecords" 
                  id="authorList" name="authorList">
                <input type="submit" name="submit" value="View Authors"/>
            </form>
        </div>
        
        <p><a href="${pageContext.request.contextPath}/AuthorController?action=getAllAuthorRecords">Get Author List(controller)</a></p>

        <!--p><a href="mailto:${applicationScope['webmaster-email']}">Click to contact the Webmaster.</a></p-->
        
    </body>
</html>
