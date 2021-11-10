package dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import beans.Client;
import static dao.DAOUtilitaire.*;

public class ClientDaoImpl implements ClientDao {
	private DAOFactory  daoFactory;
	private static final String SQL_INSERT = "INSERT INTO Client (nom, prenom, adresse, telephone, email, image) VALUES ( ?,?,?,?,?,? )";
	private static final String SQL_DELETE = "DELETE FROM Client WHERE id = ?";
	private static final String SQL_SELECT = "SELECT id, nom, prenom, adresse, telephone, email, image FROM Client ORDER BY id";
	
	ClientDaoImpl( DAOFactory daoFactory){
		this.daoFactory = daoFactory;
	}
	
    /* Implémentation de la méthode creer() définie dans l'interface UtilisateurDao */
    @Override
    public void creer( Client client ) throws DAOException {
    	Connection connexion = null;
        PreparedStatement preparedStatement = null;
        ResultSet valeursAutoGenerees = null;
        
		try {
            /* Récupération d'une connexion depuis la Factory */
            connexion = daoFactory.getConnection();
            preparedStatement = initialisationRequetePreparee( connexion, SQL_INSERT, true, client.getNom(), client.getPrenom(), client.getAdresse(), client.getTelephone(), client.getEmail(), client.getImage()  );
            int statut = preparedStatement.executeUpdate();
            /* Analyse du statut retourné par la requête d'insertion */
            if ( statut == 0 ) {
                throw new DAOException( "Échec de la création de l'utilisateur, aucune ligne ajoutée dans la table." );
            }
            /* Récupération de l'id auto-généré par la requête d'insertion */
            valeursAutoGenerees = preparedStatement.getGeneratedKeys();
            if ( valeursAutoGenerees.next() ) {
                /* Puis initialisation de la propriété id du bean Utilisateur avec sa valeur */
                client.setId( valeursAutoGenerees.getLong( 1 ) );
            } else {
                throw new DAOException( "Échec de la création de l'utilisateur en base, aucun ID auto-généré retourné." );
            }
        } catch ( SQLException e ) {
            throw new DAOException( e );
        } finally {
            fermeturesSilencieuses( valeursAutoGenerees, preparedStatement, connexion );
        }
		
    }

    
    /* Implémentation de la méthode supprimmer() définie dans l'interface UtilisateurDao */
    @Override
    public void supprimmer( Long idClient ) throws IllegalArgumentException, DAOException {
    	Connection connexion = null;
        PreparedStatement preparedStatement = null;
        ResultSet valeursAutoGenerees = null;
        
        try {
            /* Récupération d'une connexion depuis la Factory */
            connexion = daoFactory.getConnection();
            preparedStatement = initialisationRequetePreparee( connexion, SQL_DELETE, true, idClient );
            int statut = preparedStatement.executeUpdate();
            if ( statut == 0 ) {
                throw new DAOException( "Échec de la suppression de l'utilisateur, aucune ligne supprimmé" );
            }
        } catch ( SQLException e ) {
            throw new DAOException( e );
        } finally {
            fermeturesSilencieuses( valeursAutoGenerees, preparedStatement, connexion );
        }
    }
    
    /* Implémentation de la méthode définie dans l'interface ClientDao */
    @Override
    public List<Client> lister() throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<Client> clients = new ArrayList<Client>();

        try {
            connection = daoFactory.getConnection();
            preparedStatement = connection.prepareStatement( SQL_SELECT );
            resultSet = preparedStatement.executeQuery();
            while ( resultSet.next() ) {
                clients.add( map( resultSet ) );
            }
        } catch ( SQLException e ) {
            throw new DAOException( e );
        } finally {
            fermeturesSilencieuses( resultSet, preparedStatement, connection );
        }

        return clients;
    }
}