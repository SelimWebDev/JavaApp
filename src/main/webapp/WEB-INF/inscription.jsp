<%@ page pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8" />
        <title>Inscription</title>
        <link type="text/css" rel="stylesheet" href="inc/style.css" />
    </head>
    <body>
    
    	<c:import url="/inc/header.jsp"></c:import>
    	
        <div>       
            <form method="post" action="inscription">
                  
				<fieldset>
					<legend>Inscription</legend>
					
					<label for="nomInscr">Nom et prénom</label>
					<input type="text" id="nomInscr" name="nomInscr" value="" size="20" maxlength="60" />
					<span class="erreur"><c:out value="${ form.erreurs['nomInscr'] }"/></span>
					<br />
					
					<label for="emailInscr">Adresse email</label>
					<input type="email" id="emailInscr" name="emailInscr" value="" size="20" maxlength="60" />
					<span class="erreur"><c:out value="${ form.erreurs['emailInscr'] }"/></span>
					<br />
					
					<label for="mdp">Mot de passe</label>
					<input type="password" id="mdpInscr" name="mdpInscr" value="", minlength="8">
					<span class="erreur"><c:out value="${ form.erreurs['mdpInscr'] }"></c:out></span>
					<br />
				</fieldset>

                <input type="submit" value="Valider"  />
                <a href='<c:url value="/connexion"/>'>Se connecter</a>
            </form>
        </div>
    </body>
</html>