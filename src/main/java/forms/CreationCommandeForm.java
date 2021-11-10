package forms;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import beans.Client;
import beans.Commande;

public final class CreationCommandeForm {
    private static final String CHAMP_DATE             = "dateCommande";
    private static final String CHAMP_MONTANT          = "montantCommande";
    private static final String CHAMP_MODE_PAIEMENT    = "modePaiementCommande";
    private static final String CHAMP_STATUT_PAIEMENT  = "statutPaiementCommande";
    private static final String CHAMP_MODE_LIVRAISON   = "modeLivraisonCommande";
    private static final String CHAMP_STATUT_LIVRAISON = "statutLivraisonCommande";
    private static final String CHAMP_SELECTION_CLIENT = "client-select";
    private static final String FORMAT_DATE            = "dd/MM/yyyy HH:mm:ss";
    private static final String CLIENTS_EN_SESSION	   = "clientSave";

    private String              resultat;
    private Map<String, String> erreurs                = new HashMap<String, String>();

    public Commande creerCommande( HttpServletRequest request ) {
    	
        Commande commande = new Commande();
    	
    	//si utiliateur a choisie un client 
    	if (request.getParameter(CHAMP_SELECTION_CLIENT) != null) {
    		HttpSession session = request.getSession();
    		Map<String, Client> clientMap = (Map<String, Client>) session.getAttribute(CLIENTS_EN_SESSION);	// on recupère les clients en sessions ds clientMap
    		Client client = clientMap.get(request.getParameter(CHAMP_SELECTION_CLIENT));						//	on cherche ds clientMap le client envoyé en requete
    		commande.setClient( client );
    	} else {	
    		setErreur( CHAMP_SELECTION_CLIENT, "Veuillez choisir un client" );
    	}
    	
        /*
         * Récupération et conversion de la date en String selon le format
         * choisi.
         */
        DateTime dt = new DateTime();
        DateTimeFormatter formatter = DateTimeFormat.forPattern( FORMAT_DATE );
        String date = dt.toString( formatter );
        
        String montant = getValeurChamp( request, CHAMP_MONTANT );
        String modePaiement = getValeurChamp( request, CHAMP_MODE_PAIEMENT );
        String statutPaiement = getValeurChamp( request, CHAMP_STATUT_PAIEMENT );
        String modeLivraison = getValeurChamp( request, CHAMP_MODE_LIVRAISON );
        String statutLivraison = getValeurChamp( request, CHAMP_STATUT_LIVRAISON );


        commande.setDate( date );

        double valeurMontant = -1;
        try {
            valeurMontant = validationMontant( montant );
        } catch ( Exception e ) {
            setErreur( CHAMP_MONTANT, e.getMessage() );
        }
        commande.setMontant( valeurMontant );

        try {
            validationModePaiement( modePaiement );
        } catch ( Exception e ) {
            setErreur( CHAMP_MODE_PAIEMENT, e.getMessage() );
        }
        commande.setModePay( modePaiement );

        try {
            validationStatutPaiement( statutPaiement );
        } catch ( Exception e ) {
            setErreur( CHAMP_STATUT_PAIEMENT, e.getMessage() );
        }
        commande.setStatutPay( statutPaiement );

        try {
            validationModeLivraison( modeLivraison );
        } catch ( Exception e ) {
            setErreur( CHAMP_MODE_LIVRAISON, e.getMessage() );
        }
        commande.setModeLivr( modeLivraison );

        try {
            validationStatutLivraison( statutLivraison );
        } catch ( Exception e ) {
            setErreur( CHAMP_STATUT_LIVRAISON, e.getMessage() );
        }
        commande.setStatutLivr( statutLivraison );

        if ( erreurs.isEmpty() ) {
            resultat = "Succès de la création de la commande.";
        } else {
            resultat = "Échec de la création de la commande.";
        }
        return commande;
    }

    private double validationMontant( String montant ) throws Exception {
        double temp;
        if ( montant != null ) {
            try {
                temp = Double.parseDouble( montant );
                if ( temp < 0 ) {
                    throw new Exception( "Le montant doit être un nombre positif." );
                }
            } catch ( NumberFormatException e ) {
                temp = -1;
                throw new Exception( "Le montant doit être un nombre." );
            }
        } else {
            temp = -1;
            throw new Exception( "Merci d'entrer un montant." );
        }
        return temp;
    }

    private void validationModePaiement( String modePaiement ) throws Exception {
        if ( modePaiement != null ) {
            if ( modePaiement.length() < 2 ) {
                throw new Exception( "Le mode de paiement doit contenir au moins 2 caractères." );
            }
        } else {
            throw new Exception( "Merci d'entrer un mode de paiement." );
        }
    }

    private void validationStatutPaiement( String statutPaiement ) throws Exception {
        if ( statutPaiement != null && statutPaiement.length() < 2 ) {
            throw new Exception( "Le statut de paiement doit contenir au moins 2 caractères." );
        }
    }

    private void validationModeLivraison( String modeLivraison ) throws Exception {
        if ( modeLivraison != null ) {
            if ( modeLivraison.length() < 2 ) {
                throw new Exception( "Le mode de livraison doit contenir au moins 2 caractères." );
            }
        } else {
            throw new Exception( "Merci d'entrer un mode de livraison." );
        }
    }

    private void validationStatutLivraison( String statutLivraison ) throws Exception {
        if ( statutLivraison != null && statutLivraison.length() < 2 ) {
            throw new Exception( "Le statut de livraison doit contenir au moins 2 caractères." );
        }
    }

    /*
     * Ajoute un message correspondant au champ spécifié à la map des erreurs.
     */
    private void setErreur( String champ, String message ) {
        erreurs.put( champ, message );
    }

    /*
     * Méthode utilitaire qui retourne null si un champ est vide, et son contenu
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
    
    public Map<String, String> getErreurs() {
        return erreurs;
    }

    public String getResultat() {
        return resultat;
    }
}