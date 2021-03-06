package forms;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;

import beans.Utilisateur;
import dao.UtilisateurDao;
import dao.DAOException;

public final class CreationUtilisateurForm {
    private static final String CHAMP_NOM       		= "nomInscr";
    private static final String CHAMP_EMAIL     		= "emailInscr";
    private static final String CHAMP_MOT_DE_PASSE     	= "mdpInscr";
    
    private static final int    TAILLE_TAMPON   = 10240;                        // 10ko
    
    private UtilisateurDao							utilisateurDao;
    public CreationUtilisateurForm(UtilisateurDao utilisateurDao) {
    	this.utilisateurDao = utilisateurDao;
    }
    
    private String resultat;
    private Map<String, String> erreurs = new HashMap<String, String>();

    public Map<String, String> getErreurs() {
        return erreurs;
    }

    public String getResultat() {
        return resultat;
    }

    public Utilisateur creerUtilisateur( HttpServletRequest request ) {
    	

    	
        String nom = getValeurChamp( request, CHAMP_NOM );
        String email = getValeurChamp( request, CHAMP_EMAIL );
        String mdp = getValeurChamp(request, CHAMP_MOT_DE_PASSE);
        Utilisateur utilisateur = new Utilisateur();
        
        

        try {
            validationNom( nom );
        } catch ( Exception e ) {
            setErreur( CHAMP_NOM, e.getMessage() );
        }
        utilisateur.setNom( nom );

        try {
            validationMdp( mdp );
        } catch ( Exception e ) {
            setErreur( CHAMP_MOT_DE_PASSE, e.getMessage() );
        }
        utilisateur.setMdp( mdp );

        try {
            validationEmail( email );
        } catch ( Exception e ) {
            setErreur( CHAMP_EMAIL, e.getMessage() );
        }
        utilisateur.setEmail( email );
                
        try {
        	if ( erreurs.isEmpty() ) {
            	utilisateurDao.creer(utilisateur);
                resultat = "Succ??s de la cr??ation du compte utilisateur.";
            } else {
                resultat = "??chec de la cr??ation du compte utilisateur.";
            }
        }	catch (DAOException e) {
        	resultat = "Echec de l'inscription";
        	e.printStackTrace();
        }
        

        return utilisateur;
    }
    
    
    private void validationNom( String nom ) throws Exception {
        if ( nom != null ) {
            if ( nom.length() < 2 ) {
                throw new Exception( "Le nom d'utilisateur doit contenir au moins 2 caract??res." );
            }
        } else {
            throw new Exception( "Merci d'entrer un nom d'utilisateur." );
        }
    }

    private void validationMdp( String mdp ) throws Exception {
        if ( mdp != null && mdp.length() < 4 ) {
            throw new Exception( "Le pr??nom d'utilisateur doit contenir au moins 4 caract??res." );
        }
    }

    private void validationEmail( String email ) throws Exception {
        if ( email != null && !email.matches( "([^.@]+)(\\.[^.@]+)*@([^.@]+\\.)+([^.@]+)" ) ) {
            throw new Exception( "Merci de saisir une adresse mail valide." );
        }
    }
    
    /*
     * Ajoute un message correspondant au champ sp??cifi?? ?? la map des erreurs.
     */
    private void setErreur( String champ, String message ) {
        erreurs.put( champ, message );
    }

    /*
     * M??thode utilitaire qui retourne null si un champ est vide, et son contenu
     * sinon.
     */
    private static String getValeurChamp( HttpServletRequest request, String nomChamp ) {
        String valeur = request.getParameter( nomChamp );
        if ( valeur == null || valeur.trim().length() == 0 ) {
            return null;
        } else {
            return valeur;
        }
    }
    
    /*
     * M??thode utilitaire qui a pour unique but d'analyser l'en-t??te
     * "content-disposition", et de v??rifier si le param??tre "filename" y est
     * pr??sent. Si oui, alors le champ trait?? est de type File et la m??thode
     * retourne son nom, sinon il s'agit d'un champ de formulaire classique et
     * la m??thode retourne null.
     */
    private static String getNomFichier( Part part ) {
        /* Boucle sur chacun des param??tres de l'en-t??te "content-disposition". */
        for ( String contentDisposition : part.getHeader( "content-disposition" ).split( ";" ) ) {
            /* Recherche de l'??ventuelle pr??sence du param??tre "filename". */
            if ( contentDisposition.trim().startsWith( "filename" ) ) {
                /*
                 * Si "filename" est pr??sent, alors renvoi de sa valeur,
                 * c'est-??-dire du nom de fichier sans guillemets.
                 */
                return contentDisposition.substring( contentDisposition.indexOf( '=' ) + 1 ).trim().replace( "\"", "" );
            }
        }
        /* Et pour terminer, si rien n'a ??t?? trouv??... */
        return null;
    }
    
    private void ecrireFichier( InputStream contenuFichier, String nomFichier, String chemin )
            throws Exception {
        /* Pr??pare les flux. */
        BufferedInputStream entree = null;
        BufferedOutputStream sortie = null;
        try {
            /* Ouvre les flux. */
            entree = new BufferedInputStream( contenuFichier, TAILLE_TAMPON );
            sortie = new BufferedOutputStream( new FileOutputStream( new File( chemin + nomFichier ) ),
                    TAILLE_TAMPON );

            /*
             * Lit le fichier re??u et ??crit son contenu dans un fichier sur le
             * disque.
             */
            byte[] tampon = new byte[TAILLE_TAMPON];
            int longueur = 0;
            while ( ( longueur = entree.read( tampon ) ) > 0 ) {
                sortie.write( tampon, 0, longueur );
            }
        } catch ( Exception e ) {
            throw new Exception( "Erreur lors de l'??criture du fichier sur le disque." );
        } finally {
            try {
                sortie.close();
            } catch ( IOException ignore ) {
            }
            try {
                entree.close();
            } catch ( IOException ignore ) {
            }
        }
    }

}