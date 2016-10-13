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


        <div id="content" class="container">
            <h1>Complete Author List</h1>

            <h3>Authors in the Author List</h3>
            
            <div>
                <%@include file='AddAuthor.jsp'%>
            </div>
            
            <p><a href="${pageContext.request.contextPath}/AuthorController?action=homePage">Home Page</a></p>

            <%
                //checks to see if there is a valid author list and prints the list
                Object authorList = request.getAttribute("authorList");

                if (authorList == null) {
                    //out.println(request.getAttribute("errorMessage"));
                    out.println("Author List not found.");
                }
            %>

            <table style="width:50%" class="table table-striped table-bordered" >
                <tr>
                    <th class="text-center">Author Name</th>
                    <th class="text-center">Author ID</th>
                    <th class="text-center">Add Date</th>
                    <th class="text-center">Edit</th>
                    <th class="text-center">Delete</th>
                </tr>
                <!-- Getting error that there is no "author id" -->
                <!-- Fixed error, misspelled authorId -->
                <c:forEach var="author" items="${authorList}">
                    <tr>
                        <th class="text-center">${author.authorName}</th>
                        <th class="text-center">${author.authorId}</th>
                        <th class="text-center"><fmt:formatDate value="${author.dateAdded}" pattern="M/d/yyyy"/></th>
                        <th class="text-center">
                            <!--button>Edit?</button-->
                            <form method="POST" action="${pageContext.request.contextPath}/AuthorController?action=editAuthorRecord" 
                                  id="authorList" name="authorList">
                                <!--input hidden name="authorId" value="${author.authorId}"/-->
                                <!--input hidden name="authorId" value="${author.authorName}"/-->
                                <!--input hidden name="authorId" value="${author.dateAdded}"/-->
                                <input type="submit" name="submit" value="Edit"/>
                            </form>
                        </th>
                        <th class="text-center">
                            <!--input value="Delete?"  name="deleteButton" id="deleteButton" action="AuthorController?action=deleteAuthorRecord" method="post" -->
                            <!--button>Delete?</button-->
                            <form method="POST" action="${pageContext.request.contextPath}/AuthorController?action=deleteAuthorRecord" 
                                  id="deleteButton" name="deleteButton">
                                <input hidden name="authorId" value="${author.authorId}"/>
                                <input type="submit" name="submit" value="Delete"/>
                            </form>
                        </th>
                    </tr>
                    
                    <tr>
                    <div>
                        <!--%@include file='EditAuthor.jsp'%-->
                    </div>
                </tr>
                </c:forEach>            
            </table>
        </div>

        <!--div>
            <form method="POST" action="${pageContext.request.contextPath}/AuthorController?action=addNewAuthorRecord" 
                  id="authorList" name="authorList">
                <input type="submit" name="submit" value="Add New Author"/>
            </form>
        </div-->
                  <!--div hidden-->
                  
                  
                  <!--div hidden-->
                  <div>
                      <!--%@include file='EditAuthor.jsp'%-->
                  </div>
                  
                  
        <!-- Latest compiled and minified JavaScript -->
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    </body>
</html>
