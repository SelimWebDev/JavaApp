<%@ page pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8" />
        <title>Création d'une commande</title>
        <link type="text/css" rel="stylesheet" href="inc/style.css" />
    </head>
    <body>
    
    	<c:import url="/inc/header.jsp"></c:import>
    	
        <div>       
            <form method="post" action="creationCommande">
  
            	<c:choose>
  					<c:when test="${ empty sessionScope.clientSave}">
    					<c:import url="/inc/menu.jsp"></c:import>
  					</c:when>
  					<c:when test="${!empty sessionScope.clientSave}">
                 		<fieldset>
	    					<label for="client-select">Choisissez un client éxistant</label>
							<select name="client-select" id="client-select">
							 	<option value="">--Please choose an option--</option>
								<c:forEach items="${sessionScope.clientSave}" var="client" >
									<option value="${client.key}">${client.key}</option>
								</c:forEach>
							</select>
							
						<fieldset>
  					</c:when>
            	</c:choose>
         
                <fieldset>
                    <legend>Informations commande</legend>
                    
                    <label for="dateCommande">Date <span class="requis">*</span></label>
                    <input type="text" id="dateCommande" name="dateCommande" value="" size="20" maxlength="20" disabled />
                    <span class="erreur"><c:out value="${ form.erreurs['dateCommande'] }"/></span>
                    <br />
                    
                    <label for="montantCommande">Montant <span class="requis">*</span></label>
                    <input type="text" id="montantCommande" name="montantCommande" value="" size="20" maxlength="20" />
                    <span class="erreur"><c:out value="${ form.erreurs['montantCommande'] }"/></span>
                    <br />
                    
                    <label for="modePaiementCommande">Mode de paiement <span class="requis">*</span></label>
                    <input type="text" id="modePaiementCommande" name="modePaiementCommande" value="" size="20" maxlength="20" />
                    <span class="erreur"><c:out value="${ form.erreurs['modePaiementCommande'] }"/></span>
                    <br />
                    
                    <label for="statutPaiementCommande">Statut du paiement</label>
                    <input type="text" id="statutPaiementCommande" name="statutPaiementCommande" value="" size="20" maxlength="20" />
                    <span class="erreur"><c:out value="${ form.erreurs['statutPaiementCommande'] }"/></span>
                    <br />
                    
                    <label for="modeLivraisonCommande">Mode de livraison <span class="requis">*</span></label>
                    <input type="text" id="modeLivraisonCommande" name="modeLivraisonCommande" value="" size="20" maxlength="20" />
                    <span class="erreur"><c:out value="${ form.erreurs['modeLivraisonCommande'] }"/></span>
                    <br />
                    
                    <label for="statutLivraisonCommande">Statut de la livraison</label>
                    <input type="text" id="statutLivraisonCommande" name="statutLivraisonCommande" value="" size="20" maxlength="20" />
                    <span class="erreur"><c:out value="${ form.erreurs['statutLivraisonCommande'] }"/></span>
                    <br />
                </fieldset>
                <input type="submit" value="Valider"  />
                <input type="reset" value="Remettre à zéro" /> <br />
            </form>
        </div>
    </body>
</html>