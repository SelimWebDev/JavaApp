package servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



public class ListerClients extends HttpServlet {
	public static final String VUE_LISTE = "/WEB-INF/listerClients.jsp";
    
	public void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException {
	        /* À la réception d'une requête GET*/
	        this.getServletContext().getRequestDispatcher( VUE_LISTE ).forward( request, response );
	    }
}
