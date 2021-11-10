package dao;

import java.util.List;

import beans.Utilisateur;

public interface UtilisateurDao {

    void creer( Utilisateur utilisateur ) throws DAOException;

    //void supprimmer( Long idUtilisateur ) throws DAOException;
    
    //Utilisateur trouver(String email, String mdp) throws DAOException;

}