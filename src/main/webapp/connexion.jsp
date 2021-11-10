<%@ page pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8" />
        <title>Connexion</title>
        <link type="text/css" rel="stylesheet" href="inc/style.css" />
    </head>
    <body>
    
    	<c:import url="/inc/header.jsp"></c:import>
    	
        <div>       
            <form method="post" action="connexion">
                  
				<fieldset>
					<legend>Connexion</legend>
					
					<label for="emailClient">Adresse email</label>
					<input type="email" id="emailCo" name="emailCo" value="" size="20" maxlength="60" />
					<span class="erreur"><c:out value="${ form.erreurs['mailCo'] }"/></span>
					<br />
					
					<label for="mdp">Mot de passe</label>
					<input type="password" id="mdp" name="mdp" value="", minlength="8">
					<span class="erreur"><c:out value="${ form.erreurs['mdp'] }"></c:out></span>
					<br />
				</fieldset>

                <input type="submit" value="Valider"  />
                <a href='<c:url value="/inscription"/>'>S'inscrire</a>
            </form>
        </div>
    </body>
</html>