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

import beans.Client;
import dao.ClientDao;
import dao.DAOException;
import eu.medsea.mimeutil.MimeUtil;




public final class CreationClientForm {
    private static final String CHAMP_NOM       = "nomClient";
    private static final String CHAMP_PRENOM    = "prenomClient";
    private static final String CHAMP_ADRESSE   = "adresseClient";
    private static final String CHAMP_TELEPHONE = "telephoneClient";
    private static final String CHAMP_EMAIL     = "emailClient";
    private static final String CHAMP_IMAGE     = "imgProfile";
    
    private static final int    TAILLE_TAMPON   = 10240;                        // 10ko
    
    private ClientDao							clientDao;
    public CreationClientForm(ClientDao clientDao) {
    	this.clientDao = clientDao;
    }
    
    private String resultat;
    private Map<String, String> erreurs = new HashMap<String, String>();

    public Map<String, String> getErreurs() {
        return erreurs;
    }

    public String getResultat() {
        return resultat;
    }

    public Client creerClient( HttpServletRequest request, String chemin ) {
    	

    	
        String nom = getValeurChamp( request, CHAMP_NOM );
        String prenom = getValeurChamp( request, CHAMP_PRENOM );
        String adresse = getValeurChamp( request, CHAMP_ADRESSE );
        String telephone = getValeurChamp( request, CHAMP_TELEPHONE );
        String email = getValeurChamp( request, CHAMP_EMAIL );
        String image = null;
        Client client = new Client();
        
        

        try {
            validationNom( nom );
        } catch ( Exception e ) {
            setErreur( CHAMP_NOM, e.getMessage() );
        }
        client.setNom( nom );

        try {
            validationPrenom( prenom );
        } catch ( Exception e ) {
            setErreur( CHAMP_PRENOM, e.getMessage() );
        }
        client.setPrenom( prenom );

        try {
            validationAdresse( adresse );
        } catch ( Exception e ) {
            setErreur( CHAMP_ADRESSE, e.getMessage() );
        }
        client.setAdresse( adresse );

        try {
            validationTelephone( telephone );
        } catch ( Exception e ) {
            setErreur( CHAMP_TELEPHONE, e.getMessage() );
        }
        client.setTelephone( telephone );

        try {
            validationEmail( email );
        } catch ( Exception e ) {
            setErreur( CHAMP_EMAIL, e.getMessage() );
        }
        client.setEmail( email );
        
        try {
            image = validationImage( request, chemin );
        } catch ( Exception e ) {
            setErreur( CHAMP_IMAGE, e.getMessage() );
        }
        client.setImage( image );
        
        try {
        	if ( erreurs.isEmpty() ) {
            	clientDao.creer(client);
                resultat = "Succ??s de la cr??ation du client.";
            } else {
                resultat = "??chec de la cr??ation du client.";
            }
        }	catch (DAOException e) {
        	resultat = "Echec de l'inscription";
        	e.printStackTrace();
        }
        

        return client;
    }
    
    private String validationImage( HttpServletRequest request, String chemin ) throws Exception { // A MODIFIER
        /*
         * R??cup??ration du contenu du champ image du formulaire. Il faut ici
         * utiliser la m??thode getPart().
         */
        String nomFichier = null;
        InputStream contenuFichier = null;
        try {
            Part part = request.getPart( CHAMP_IMAGE );
            nomFichier = getNomFichier( part );

            /*
             * Si la m??thode getNomFichier() a renvoy?? quelque chose, il s'agit
             * donc d'un champ de type fichier (input type="file").
             */
            if ( nomFichier != null && !nomFichier.isEmpty() ) {
                /*
                 * Antibug pour Internet Explorer, qui transmet pour une raison
                 * mystique le chemin du fichier local ?? la machine du client...
                 * 
                 * Ex : C:/dossier/sous-dossier/fichier.ext
                 * 
                 * On doit donc faire en sorte de ne s??lectionner que le nom et
                 * l'extension du fichier, et de se d??barrasser du superflu.
                 */
                nomFichier = nomFichier.substring( nomFichier.lastIndexOf( '/' ) + 1 )
                        .substring( nomFichier.lastIndexOf( '\\' ) + 1 );

                /* R??cup??ration du contenu du fichier */
                contenuFichier = part.getInputStream();

                /* Extraction du type MIME du fichier depuis l'InputStream */
                MimeUtil.registerMimeDetector( "eu.medsea.mimeutil.detector.MagicMimeMimeDetector" );
                Collection<?> mimeTypes = MimeUtil.getMimeTypes( contenuFichier );

                /*
                 * Si le fichier est bien une image, alors son en-t??te MIME
                 * commence par la cha??ne "image"
                 */
                if ( mimeTypes.toString().startsWith( "image" ) ) {
                    /* Ecriture du fichier sur le disque */
                    ecrireFichier( contenuFichier, nomFichier, chemin );
                } else {
                    throw new  Exception( "Le fichier envoy?? doit ??tre une image." );
                }
            }
        } catch ( IllegalStateException e ) {
            /*
             * Exception retourn??e si la taille des donn??es d??passe les limites
             * d??finies dans la section <multipart-config> de la d??claration de
             * notre servlet d'upload dans le fichier web.xml
             */
            e.printStackTrace();
            throw new Exception( "Le fichier envoy?? ne doit pas d??passer 1Mo." );
        } catch ( IOException e ) {
            /*
             * Exception retourn??e si une erreur au niveau des r??pertoires de
             * stockage survient (r??pertoire inexistant, droits d'acc??s
             * insuffisants, etc.)
             */
            e.printStackTrace();
            throw new Exception( "Erreur de configuration du serveur." );
        } catch ( ServletException e ) {
            /*
             * Exception retourn??e si la requ??te n'est pas de type
             * multipart/form-data.
             */
            e.printStackTrace();
            throw new Exception(
                    "Ce type de requ??te n'est pas support??, merci d'utiliser le formulaire pr??vu pour envoyer votre fichier." );
        }

        return nomFichier;
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

    private void validationPrenom( String prenom ) throws Exception {
        if ( prenom != null && prenom.length() < 2 ) {
            throw new Exception( "Le pr??nom d'utilisateur doit contenir au moins 2 caract??res." );
        }
    }

    private void validationAdresse( String adresse ) throws Exception {
        if ( adresse != null ) {
            if ( adresse.length() < 10 ) {
                throw new Exception( "L'adresse de livraison doit contenir au moins 10 caract??res." );
            }
        } else {
            throw new Exception( "Merci d'entrer une adresse de livraison." );
        }
    }

    private void validationTelephone( String telephone ) throws Exception {
        if ( telephone != null ) {
            if ( !telephone.matches( "^\\d+$" ) ) {
                throw new Exception( "Le num??ro de t??l??phone doit uniquement contenir des chiffres." );
            } else if ( telephone.length() < 4 ) {
                throw new Exception( "Le num??ro de t??l??phone doit contenir au moins 4 chiffres." );
            }
        } else {
            throw new Exception( "Merci d'entrer un num??ro de t??l??phone." );
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