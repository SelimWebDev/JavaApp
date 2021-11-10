
		<fieldset>
			<legend>Informations client</legend>
			
			<label for="nomClient">Nom <span class="requis">*</span></label>
			<input type="text" id="nomClient" name="nomClient" value="" size="20" maxlength="20" />
			<span class="erreur"><c:out value="${ form.erreurs['nomClient'] }"/></span>
			<br />
			
			<label for="prenomClient">Prénom </label>
			<input type="text" id="prenomClient" name="prenomClient" value="" size="20" maxlength="20" />
			<span class="erreur"><c:out value="${ form.erreurs['prenomClient'] }"/></span>
			<br />
			
			<label for="adresseClient">Adresse de livraison <span class="requis">*</span></label>
			<input type="text" id="adresseClient" name="adresseClient" value="" size="20" maxlength="20" />
			<span class="erreur"><c:out value="${ form.erreurs['adresseClient'] }"/></span>

			<br />
			
			<label for="telephoneClient">Numéro de téléphone <span class="requis">*</span></label>
			<input type="text" id="telephoneClient" name="telephoneClient" value="" size="20" maxlength="20" />
			<span class="erreur"><c:out value="${ form.erreurs['telephoneClient'] }"/></span>
			<br />
			
			<label for="emailClient">Adresse email</label>
			<input type="email" id="emailClient" name="emailClient" value="" size="20" maxlength="60" />
			<span class="erreur"><c:out value="${ form.erreurs['mailClient'] }"/></span>
			<br />
			
			<label for="imgPorfil">Choisissez une image de profil</label>
			<input type="file" id="imgProfil" name="imgProfile" accept="image/png, image/jpeg">
			<br />
		</fieldset>
