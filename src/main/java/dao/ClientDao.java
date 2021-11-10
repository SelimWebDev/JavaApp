package dao;

import java.util.List;

import beans.Client;

public interface ClientDao {

    void creer( Client client ) throws DAOException;

    void supprimmer( Long idClient ) throws DAOException;
    
    List<Client> lister() throws DAOException;

}