<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>Liste des clients</title>
	     <link type="text/css" rel="stylesheet" href="inc/style.css" />
	</head>
	<body>
		<c:import url="/inc/header.jsp"></c:import>
		<c:if test="${!empty sessionScope.clientSave}">
			<table>
			    <tr>
			        <th>Nom</th>
			        <th>Prénom</th>
			        <th>Adresse</th>
			        <th>Téléphone</th>
			        <th>Email</th>
			        <th>Image</th>
			        <th>Supprimer</th>                
			    </tr>
				<c:forEach items="${sessionScope.clientSave}" var="client" varStatus="boucle">
					<tr class="${boucle.index % 2 == 0 ? 'pair' : 'impair'}">
					<%-- Affichage des propriétés du bean Client, qui est stocké en tant que valeur de l'entrée courante de la map --%>
					<td><c:out value="${ client.value.nom }"/></td>
					<td><c:out value="${ client.value.prenom }"/></td>
					<td><c:out value="${ client.value.adresse }"/></td>
					<td><c:out value="${ client.value.telephone }"/></td>
					<td><c:out value="${ client.value.email }"/></td>
					<c:if test="${!empty client.value.image}" >
						<td><div class="img_wrap"><img src="<c:url value="image/${ client.value.image }"/>"></div></td>
					</c:if>
					<%-- Lien vers la servlet de suppression, avec passage du nom du client - c'est-à-dire la clé de la Map - en paramètre grâce à la balise <c:param/>. --%>
					<td class="action">
					    <a href="<c:url value="/suppressionClient"><c:param name="idClient" value="${ client.value.id }" /></c:url>">
						<img src="<c:url value="/inc/supprimer.png"/>" alt="Supprimer" />
					     </a>
					 </td>
					</tr>
		    	</c:forEach>
		    <table>
		</c:if>
	</body>
</html>