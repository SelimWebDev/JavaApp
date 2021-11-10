package beans;

public class Utilisateur {
	private Long id;
	private String nom;
	private String email;
	private String mdp;
	
	public Long getId() {
		return this.id;
	}
	
	public String getNom() {
		return this.nom;
	}
	
	public String getEmail() {
		return this.email;
	}
	
	public String getMdp() {
		return this.mdp;
	}

	/* ******************************************** */ 
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public void setNom( String nom ) {
		this.nom = nom;
	}


	public void setEmail( String email ) {
		this.email = email;
	}
	
	public void setMdp(String mdp) {
		this.mdp = mdp;
	}
	

}
