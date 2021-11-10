<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<c:import url="/inc/header.jsp"></c:import>
	<c:if test="${ !empty sessionScope.commandeSave }">
		<table>
		    <tr>
		        <th>Client</th>
		        <th>Date</th>
		        <th>Montant</th>
		        <th>Mode de paiement</th>
		        <th>Statut de paiement</th>
		        <th>Mode de livraison</th>
		        <th>Statut de livraison</th>
		        <th class="action">Action</th>                    
		    </tr>
			<c:forEach items="${sessionScope.commandeSave}" var="commande" varStatus="boucle">
				<tr class="${boucle.index % 2 == 0 ? 'pair' : 'impair'}">
			        <%-- Affichage des propriétés du bean Commande, qui est stocké en tant que valeur de l'entrée courante de la map --%>
			        <td><c:out value="${ commande.value.client.prenom } ${ commande.value.client.nom }"/></td>
			        <td><c:out value="${ commande.value.date }"/></td>
			        <td><c:out value="${ commande.value.montant }"/></td>
			        <td><c:out value="${ commande.value.modePay }"/></td>
			        <td><c:out value="${ commande.value.statutPay }"/></td>
			        <td><c:out value="${ commande.value.modeLivr }"/></td>
			        <td><c:out value="${ commande.value.statutLivr }"/></td>
			        <%-- Lien vers la servlet de suppression, avec passage de la date de la commande - c'est-à-dire la clé de la Map - en paramètre grâce à la balise <c:param/>. --%>
			        <td class="action">
			            <a href="<c:url value="/suppressionCommande"><c:param name="dateCommande" value="${ commande.key }" /></c:url>">
			                <img src="<c:url value="/inc/supprimer.png"/>" alt="Supprimer" />
			            </a>
			        </td>
			    </tr>
		    </c:forEach>
		</table>
	</c:if>
</body>
</html>