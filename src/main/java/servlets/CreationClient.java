package servlets;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import beans.Client;
import dao.ClientDao;
import dao.DAOFactory;
import forms.CreationClientForm;

public class CreationClient extends HttpServlet {
	public static final String CONF_DAO_FACTORY = "daofactory";
	public static final String CHEMIN     = "chemin";
    public static final String ATT_CLIENT = "client";
    public static final String ATT_FORM   = "form";

    public static final String VUE_SUCCES = "/WEB-INF/afficherClient.jsp";
    public static final String VUE_FORM   = "/WEB-INF/creationClient.jsp";
    
    private ClientDao     					clientDao;

    public void init() throws ServletException {
        /* Récupération d'une instance de notre DAO Utilisateur */
        this.clientDao = ( (DAOFactory) getServletContext().getAttribute( CONF_DAO_FACTORY ) ).getClientDao();
    }
    
    public void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException {
        /* À la réception d'une requête GET, simple affichage du formulaire */
        this.getServletContext().getRequestDispatcher( VUE_FORM ).forward( request, response );
    }

    public void doPost( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException {

         // Lecture du paramètre 'chemin' passé à la servlet via la déclarationdans le web.xml
    	String chemin = this.getServletConfig().getInitParameter( CHEMIN );
    	
        /* Préparation de l'objet formulaire */
        CreationClientForm form = new CreationClientForm( clientDao );

        /* Traitement de la requête et récupération du bean en résultant */
        Client client = form.creerClient( request, chemin );

        /* Ajout du bean et de l'objet métier à l'objet requête */
        request.setAttribute( ATT_CLIENT, client );
        request.setAttribute( ATT_FORM, form );

        if ( form.getErreurs().isEmpty() ) {
           
        	// ajout du client à la session
        	 HttpSession session = request.getSession();
             if (session.getAttribute("clientSave") == null) {
             	Map<Long, Client> clientMap = new HashMap<Long, Client>();
             	clientMap.put(client.getId(), client);
             	session.setAttribute("clientSave", clientMap);
             } else {
     			Map <Long, Client>clientMap = (Map<Long, Client>) session.getAttribute("clientSave");
     			clientMap.put(client.getId(),client);
     			session.setAttribute("clientSave", clientMap);
             } 
             /* Si aucune erreur, alors affichage de la fiche récapitulative */
            this.getServletContext().getRequestDispatcher( VUE_SUCCES ).forward( request, response );
        } else {
            /* Sinon, ré-affichage du formulaire de création avec les erreurs */
            this.getServletContext().getRequestDispatcher( VUE_FORM ).forward( request, response );
        }
    }
}