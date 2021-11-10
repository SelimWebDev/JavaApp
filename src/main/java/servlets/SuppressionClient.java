package servlets;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import beans.Client;
import dao.ClientDao;
import dao.DAOException;
import dao.DAOFactory;

public class SuppressionClient extends HttpServlet {
    
    public static final String VUE_LISTE   = "/WEB-INF/listerClients.jsp";
    
	public static final String CONF_DAO_FACTORY = "daofactory";
	private ClientDao     					clientDao;
	public void init() throws ServletException {
        /* Récupération d'une instance de notre DAO Utilisateur */
        this.clientDao = ( (DAOFactory) getServletContext().getAttribute( CONF_DAO_FACTORY ) ).getClientDao();
    }
	
	private String resultat;
	
	 public void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException {
        /* À la réception d'une requête GET,  */
	 	HttpSession session = request.getSession();
	 	if (session.getAttribute("clientSave") == null) {
	 		this.getServletContext().getRequestDispatcher( VUE_LISTE ).forward( request, response );
	 	} else {
	 		String strIdClient = request.getParameter("idClient");
	 		Long idClient = Long.parseLong(strIdClient);
	 		HashMap<Long, Client> clientMap = (HashMap<Long, Client>) session.getAttribute("clientSave");
	 		
	 		
	 		try {
	 			clientDao.supprimmer(idClient);
	 			clientMap.remove(idClient);
	 			session.setAttribute("clientSave", clientMap);
	 		} catch (DAOException e) {
	        	resultat = "Echec de la suppression";
	        	e.printStackTrace();
	        }
	 		this.getServletContext().getRequestDispatcher( VUE_LISTE ).forward( request, response );
	 	}	
    }
}
