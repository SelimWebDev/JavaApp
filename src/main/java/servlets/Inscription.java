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
import beans.Utilisateur;
import dao.UtilisateurDao;
import dao.DAOFactory;
import forms.CreationClientForm;
import forms.CreationUtilisateurForm;

public class Inscription extends HttpServlet {
	public static final String CONF_DAO_FACTORY = "daofactory";

    public static final String VUE_SUCCES = "/connexion.jsp";
    public static final String VUE_FORM   = "/WEB-INF/inscription.jsp";
	private static final String ATT_UTILISATEUR = "utilisateur";
	private static final String ATT_FORM = "form";
    
    private UtilisateurDao     					utilisateurDao;

    public void init() throws ServletException {
        /* Récupération d'une instance de notre DAO Utilisateur */
        this.utilisateurDao = ( (DAOFactory) getServletContext().getAttribute( CONF_DAO_FACTORY ) ).getUtilisateurDao();
    }
    
    public void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException {
        /* À la réception d'une requête GET, simple affichage du formulaire */
        this.getServletContext().getRequestDispatcher( VUE_FORM ).forward( request, response );
    }
    
    public void doPost( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException {
   	
       /* Préparation de l'objet formulaire */
       CreationUtilisateurForm form = new CreationUtilisateurForm( utilisateurDao );

       /* Traitement de la requête et récupération du bean en résultant */
       Utilisateur utilisateur = form.creerUtilisateur( request );

       /* Ajout du bean et de l'objet métier à l'objet requête */
       request.setAttribute( ATT_UTILISATEUR, utilisateur );
       request.setAttribute( ATT_FORM, form );

       if ( form.getErreurs().isEmpty() ) {      	 
          /* Si aucune erreur, alors affichage de la fiche récapitulative */
           this.getServletContext().getRequestDispatcher( VUE_SUCCES ).forward( request, response );
       } else {
           /* Sinon, ré-affichage du formulaire de création avec les erreurs */
           this.getServletContext().getRequestDispatcher( VUE_FORM ).forward( request, response );
       }
   }
}