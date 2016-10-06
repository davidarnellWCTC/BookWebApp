/*
 * 
 */
package edu.wctc.da.bookwebapp.controller;

import edu.wctc.da.bookwebapp.model.Author;
import edu.wctc.da.bookwebapp.model.AuthorDao;
import edu.wctc.da.bookwebapp.model.AuthorDaoStrategy;
import edu.wctc.da.bookwebapp.model.AuthorService;
import edu.wctc.da.bookwebapp.model.MySqlDbStrategy;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author David Arnell
 */
@WebServlet(name = "AuthorController", urlPatterns = {"/AuthorController"})
//@SessionScoped
public class AuthorController extends HttpServlet {

    private String driverClass;
    private String url;
    private String userName;
    private String password;

    private String dbJindiName;

    @Inject
    private AuthorService authorService;

    final private String DESTINATIONPAGE = "/AuthorList.jsp";
    final private String AUTHORLIST = "authorList";

    final private String ERRORMESSAGE = "Author list not found";

    final private String ACTION = "action";

    final private String DELETE_AUTHOR_RECORD = "deleteAuthorRecord";
    final private String EDIT_AUTHOR_RECORD = "editAuthorRecord";
    final private String CREATE_NEW_AUTHOR_RECORD = "newAuthorRecord";
    final private String GET_ALL_AUTHOR_RECORDS = "getAllAuthorRecords";

    final private String AUTHOR_ID = "authorId";
    final private String AUTHOR_NAME = "authorName";
    final private String AUTHOR_CREATED_DATE = "dateCreated";

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        String authorId = null;
        String authorName = null;
        String dateCreated = null;

        List<Author> authorList = null;

        // create a new dao
//        AuthorDaoStrategy dao = new AuthorDao();
//        AuthorDaoStrategy dao = new AuthorDao(new MySqlDbStrategy(), // db strategy passed into the AuthorDao constructor
//                "com.mysql.jdbc.Driver", //driver to use
//                "jdbc:mysql://localhost:3306/book", // url of the database
//                "root", "admin");
        // creates a new AuthorService object to handle the Author objects
        //AuthorService authorService = new AuthorService();
        try {

            configDbConnection();

            // get the action requested from the JSP page
            String action = request.getParameter(ACTION);

            String errorMessage = ERRORMESSAGE;
            RequestDispatcher view = null;

            switch (action) {
                case GET_ALL_AUTHOR_RECORDS:
                    // retrieve the list of authors from the AuthorService Class
                    authorList = authorService.getAuthors();

                    //set the author list as a retrievable attribute
                    request.setAttribute(AUTHORLIST, authorList);
                    break;

                case DELETE_AUTHOR_RECORD:
                    authorId = request.getParameter(AUTHOR_ID);
                    authorService.deleteAuthorById(authorId);

                    authorList = authorService.getAuthors();

                    request.setAttribute(AUTHORLIST, authorList);
                    break;

                case EDIT_AUTHOR_RECORD:
                    authorId = request.getParameter(AUTHOR_ID);
                    authorName = request.getParameter(AUTHOR_NAME);
                    dateCreated = request.getParameter(AUTHOR_CREATED_DATE);
                    authorService.updateAuthorById(authorId, authorName, dateCreated);

                    authorList = authorService.getAuthors();

                    request.setAttribute(AUTHORLIST, authorList);
                    break;

                case CREATE_NEW_AUTHOR_RECORD:
                    authorId = request.getParameter(AUTHOR_ID);
                    authorName = request.getParameter(AUTHOR_NAME);

                    authorService.createNewAuthor(authorName, dateCreated);

                    authorList = authorService.getAuthors();

                    request.setAttribute(AUTHORLIST, authorList);
                    break;

                default:
                    view = request.getRequestDispatcher(GET_ALL_AUTHOR_RECORDS);
            }

            request.setAttribute("errorMessage", errorMessage);
        } catch (Exception e) {

        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    @Override
    public void init() throws ServletException {
        driverClass = "com.mysql.jdbc.Driver"; //driver to use
        url = "jdbc:mysql://localhost:3306/book"; // url of the database
        userName = "root";
        password = "admin";
    }

    private void configDbConnection() {
        authorService.getDao().initDao(driverClass, url, url, password);
    }
}
