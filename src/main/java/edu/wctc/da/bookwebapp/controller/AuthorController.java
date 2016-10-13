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
import java.sql.SQLException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.naming.NamingException;
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

    
    @Inject
    private AuthorService authorService;

    final private String AUTHOR_LIST_PAGE = "/AuthorList.jsp";
    final private String HOME_PAGE = "/Home.jsp";
    
    final private String AUTHORLIST = "authorList";
    private List<Author> authorList;

    final private String ERROR_MESSAGE = "Author list not found";

    final private String ACTION = "action";

    final private String GO_TO_HOME_PAGE ="homePage";
    final private String DELETE_AUTHOR_RECORD = "deleteAuthorRecord";
    final private String EDIT_AUTHOR_RECORD = "editAuthorRecord";
    final private String CREATE_NEW_AUTHOR_RECORD = "newAuthorRecord";
    final private String GET_ALL_AUTHOR_RECORDS = "getAllAuthorRecords";

    final private String AUTHOR_ID = "authorId";
    final private String AUTHOR_NAME = "authorName";
    final private String AUTHOR_CREATED_DATE = "dateCreated";
    
    private String webmasterEmail;    

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

        String destinationPage = "";
        
        String authorId = null;
        String authorName = null;
        String dateCreated = null;

        //List<Author> authorList = authorService.getAuthors();

        RequestDispatcher view = null;

        String errorMessage = ERROR_MESSAGE;
        
        try {

            configDbConnection();

            // get the action requested from the JSP page
            String action = request.getParameter(ACTION);
            
            switch (action) {
                case GO_TO_HOME_PAGE:
                    destinationPage = HOME_PAGE;                    
                    break;
                    
                case GET_ALL_AUTHOR_RECORDS:
                    
                    destinationPage = AUTHOR_LIST_PAGE;
                    //view = request.getRequestDispatcher(AUTHOR_LIST_PAGE);
                    // retrieve the list of authors from the AuthorService Class
                    //refreshAuthorList(request);
                    
                    // add code to checkf for duplicate records
                    
                    // authorList is ending up null
                    authorList = authorService.getAuthors();
                    
                    request.setAttribute(AUTHORLIST, authorList);
                    
                    //destinationPage = AUTHOR_LIST_PAGE;
                    break;

                case DELETE_AUTHOR_RECORD:
                    authorId = request.getParameter(AUTHOR_ID);
                    authorService.deleteAuthorById(authorId);
                    
                    refreshAuthorList(request);
                    
                    destinationPage = AUTHOR_LIST_PAGE;
                    break;

                case EDIT_AUTHOR_RECORD:
                    authorId = request.getParameter(AUTHOR_ID);
                    authorName = request.getParameter(AUTHOR_NAME);
                    dateCreated = request.getParameter(AUTHOR_CREATED_DATE);
                    authorService.updateAuthorById(authorId, authorName, dateCreated);

                    refreshAuthorList(request);
                    
                    destinationPage = AUTHOR_LIST_PAGE;
                    break;

                case CREATE_NEW_AUTHOR_RECORD:
                    authorName = request.getParameter(AUTHOR_NAME);

                    authorService.createNewAuthor(authorName);
                    
                    refreshAuthorList(request);
                    
                    destinationPage = AUTHOR_LIST_PAGE;
                    break;

                default:
                    refreshAuthorList(request);
                    destinationPage = AUTHOR_LIST_PAGE;
                    view = request.getRequestDispatcher(destinationPage);
            }
            
            request.setAttribute("errorMessage", errorMessage);
        } catch (Exception e) {

        }

//        dispatcher = getServletContext().getRequestDispatcher(response.encodeURL(destinationPage));
//        view = request.getRequestDispatcher(destinationPage);

        view = getServletContext().getRequestDispatcher(response.encodeURL(destinationPage));
        view.forward(request, response);
    }
    
    /**
     * This method refreshes the List<Author> list and sets the attribute to the
     * authorList accessible in the JSP page
     * @param request - HttpServletRequest
     * @throws ClassNotFoundException
     * @throws SQLException 
     */
    private void refreshAuthorList(HttpServletRequest request)//, HttpServletResponse response)
            throws ClassNotFoundException, SQLException {

        List<Author> authorList = authorService.getAuthors();

        request.setAttribute(AUTHORLIST, authorList);
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
        
        driverClass = getServletContext().getInitParameter("db.driver.class"); //driver to use
        url = getServletContext().getInitParameter("db.url"); // url of the database
        userName = getServletContext().getInitParameter("db.userName");
        password = getServletContext().getInitParameter("db.password");
        
    }

    private void configDbConnection() throws NamingException {
        authorService.getDao().initDao(driverClass, url, userName, password);
    }
}
