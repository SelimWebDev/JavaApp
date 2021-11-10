package beans;

public class Client {
	private Long id;
	private String nom;
	private String prenom;
	private String adresse;
	private String telephone;
	private String email;
	private String image;
	
	public Long getId() {
		return this.id;
	}
	
	public String getNom() {
		return this.nom;
	}
	
	public String getPrenom() {
		return this.prenom;
	}
	
	public String getAdresse() {
		return this.adresse;
	}
	
	public String getTelephone() {
		return this.telephone;
	}
	
	public String getEmail() {
		return this.email;
	}
	
	public String getImage() {
		return this.image;
	}

	/* ******************************************** */ 

	public void setNom( String nom ) {
		this.nom = nom;
	}
	
	public void setPrenom( String prenom ) {
		this.prenom = prenom;
	}
	
	public void setAdresse( String adresse ) {
		this.adresse = adresse;
	}
	
	public void setTelephone( String telephone ) {
		this.telephone = telephone;
	}
	
	public void setEmail( String email ) {
		this.email = email;
	}
	
	public void setImage(String image) {
		this.image = image;
	}
	
	public void setId(Long id) {
		this.id = id;
	}


}
