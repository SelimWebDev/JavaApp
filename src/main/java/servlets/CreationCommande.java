package servlets;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.Client;
import beans.Commande;
import forms.CreationCommandeForm;

public class CreationCommande extends HttpServlet {
    public static final String ATT_COMMANDE = "commande";
    public static final String ATT_FORM     = "form";

    public static final String VUE_SUCCES   = "/WEB-INF/afficherCommande.jsp";
    public static final String VUE_FORM     = "/WEB-INF/creationCommande.jsp";

    public void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException {
        /* À la réception d'une requête GET, simple affichage du formulaire */
        this.getServletContext().getRequestDispatcher( VUE_FORM ).forward( request, response );
    }

    public void doPost( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException {

        CreationCommandeForm form = new CreationCommandeForm();
        Commande commande = form.creerCommande( request );

        request.setAttribute( ATT_COMMANDE, commande );
        request.setAttribute( ATT_FORM, form );
        
        if ( form.getErreurs().isEmpty() ) {
            /* Si aucune erreur,*/
        	
        	//si le client créé avec commande n'éxiste pas en session
        	HttpSession session = request.getSession();
        	Map<String, Client> clients = (Map<String, Client>) session.getAttribute("clientSave");
        	if(session.getAttribute("clientSave") == null) {
        		clients = new HashMap<String, Client>();
        		clients.put(commande.getClient().getNom(),commande.getClient());
        	} else if( !clients.containsKey(commande.getClient().getNom())) {		//
        		clients.put(commande.getClient().getNom(),commande.getClient());
        		
        	}
        	session.setAttribute("clientSave", clients);
        	
            if (session.getAttribute("commandeSave") == null) {
            	Map<String, Commande> commandeMap = new HashMap<String, Commande>();
            	commandeMap.put(commande.getDate(),commande);
            	session.setAttribute("commandeSave", commandeMap);
            } else {
    			Map <String, Commande> commandeMap = (Map<String, Commande>) session.getAttribute("commandeSave");
    			commandeMap.put(commande.getDate(),commande);
    			session.setAttribute("commandeSave", commandeMap);
            }
            
            /* alors affichage de la fiche récapitulative */
            this.getServletContext().getRequestDispatcher( VUE_SUCCES ).forward( request, response );
        } else {
            /* Sinon, ré-affichage du formulaire de création avec les erreurs */
        	System.out.println(form.getErreurs());
            this.getServletContext().getRequestDispatcher( VUE_FORM ).forward( request, response );
        }
    }
}