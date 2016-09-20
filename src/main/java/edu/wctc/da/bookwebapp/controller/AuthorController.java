/*
 * 
 */
package edu.wctc.da.bookwebapp.controller;

import edu.wctc.da.bookwebapp.model.Author;
import edu.wctc.da.bookwebapp.model.AuthorService;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
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
public class AuthorController extends HttpServlet {
    
    final private String DESTINATIONPAGE = "/AuthorList.jsp";
    final private String AUTHORLIST = "authorList";
    
    final private String ERRORMESSAGE = "Author list not found";
    
    final private String ACTION = "action";

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
        
        // creates a new AuthorService object to handle the Author objects
        AuthorService authorService = new AuthorService();
        
        
        
        try {
            
            // retrieve the list of authors from the AuthorService Class
        List<Author> authorList = authorService.getAuthors();
        
        //System.out.println("test1");
        
        //set the author list as a retrievable attribute
        request.setAttribute(AUTHORLIST, authorList);
        
        // sets and error message if the author list is not found
        request.setAttribute("errorMessage", ERRORMESSAGE);
        
        RequestDispatcher view =
                request.getRequestDispatcher(DESTINATIONPAGE);
        view.forward(request, response);
        
        } catch (Exception e){
            
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

}
