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
import dao.UtilisateurDao;
import dao.DAOFactory;
import forms.CreationClientForm;

public class Inscription extends HttpServlet {
	public static final String CONF_DAO_FACTORY = "daofactory";

    public static final String VUE_SUCCES = "/connexion.jsp";
    public static final String VUE_FORM   = "/WEB-INF/inscription.jsp";
    
    private UtilisateurDao     					utilisateurDao;

    public void init() throws ServletException {
        /* Récupération d'une instance de notre DAO Utilisateur */
        this.utilisateurDao = ( (DAOFactory) getServletContext().getAttribute( CONF_DAO_FACTORY ) ).getUtilisateurDao();
    }
    
    public void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException {
        /* À la réception d'une requête GET, simple affichage du formulaire */
        this.getServletContext().getRequestDispatcher( VUE_FORM ).forward( request, response );
    }
}