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
            <form method="POST" action="/AuthorController" 
                  id="authorList" name="authorList">
                <input type="submit" name="submit" value="View Authors"/>
            </form>
        </div>
        
        <p><a href="/AuthorController">Get Author List(controller)</a></p>
        <p><a href="/BookWebApp/AuthorList.jsp">Get Author List</a></p>
        
    </body>
</html>
