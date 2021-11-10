package beans;

public class Commande {
	private Client client;
	private double montant;
	private String modePay;
	private String statutPay;
	private String modeLivr;
	private String statutLivr;
	private String date;

	// ****************************		GETTER		*********************************** //
	
	public Client getClient() {
		return this.client;
	}
	
	
	public double getMontant() {
		return this.montant;
	}
	
	public String getModePay() {
		return this.modePay;
	}
	
	public String getStatutPay() {
		return this.statutPay;
	}
	
	public String getModeLivr() {
		return this.modeLivr;
	}
	
	public String getStatutLivr() {
		return this.statutLivr;
	}
	
	public String getDate() {
		return this.date;
	}

	// ****************************		SETTER		*********************************** //
	
	public void setClient ( Client client ) {
		this.client = client;
	}
	
	public void setMontant( double montant ) {
		this.montant = montant;
	}
	
	public void setModePay( String modePay ) {
		this.modePay = modePay;
	}
	
	public void setStatutPay( String statutPay ) {
		this.statutPay = statutPay;
	}
	
	public void setModeLivr( String modeLivr ) {
		this.modeLivr = modeLivr;
	}
	
	public void setStatutLivr( String statutLivr ) {
		this.statutLivr = statutLivr;
	}
	
	public void setDate( String date ) {
		this.date = date;
	}
}
