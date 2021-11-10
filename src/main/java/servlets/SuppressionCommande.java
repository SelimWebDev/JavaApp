package servlets;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import beans.Commande;

public class SuppressionCommande extends HttpServlet {

    public static final String VUE_LISTE   = "/WEB-INF/listerCommandes.jsp";
	
	 public void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException {
        /* À la réception d'une requête GET, simple affichage du formulaire */
	 	HttpSession session = request.getSession();
	 	if (session.getAttribute("commandeSave") == null) {
	 		this.getServletContext().getRequestDispatcher( VUE_LISTE ).forward( request, response );
	 	} else {
	 		HashMap<String, Commande> commandeMap = (HashMap<String, Commande>) session.getAttribute("commandeSave");
	 		commandeMap.remove(request.getParameter("dateCommande"));
	 		session.setAttribute("commandetSave", commandeMap);
	 		this.getServletContext().getRequestDispatcher( VUE_LISTE ).forward( request, response );
	 	}	
    }
}
