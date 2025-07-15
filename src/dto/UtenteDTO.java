package dto;

public class UtenteDTO {
	private String nome;
	private String cognome;
	private String email;
	private String ruolo;
	private float mediaPunti;
	
	public UtenteDTO(String nome, String cognome, String email, String ruolo) {
		this.nome =nome;
		this.cognome = cognome;
		this.email = email;
		this.ruolo=ruolo;
		this.mediaPunti=0;
	}
	
	public UtenteDTO(String nome, String cognome, String email, String ruolo,float mediaPunti) {
		this.nome =nome;
		this.cognome = cognome;
		this.email = email;
		this.ruolo=ruolo;
		this.mediaPunti=mediaPunti;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String campo1) {
		this.nome = campo1;
	}

	public String getCognome() {
		return cognome;
	}

	public void setCognome(String campo2) {
		this.cognome = campo2;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String campo3) {
		this.email = campo3;
	}

	public String getRuolo() {
		return ruolo;
	}

	public void setRuolo(String ruolo) {
		this.ruolo = ruolo;
	}

	
	
	public float getMediaPunti() {
		return mediaPunti;
	}

	public void setMediaPunti(float mediaPunti) {
		this.mediaPunti = mediaPunti;
	}

	@Override
	public String toString() {
		if(this.mediaPunti==0) {
			return "nome=" + nome + ", cognome=" + cognome + ", email=" + email + ", ruolo=" + ruolo;
		}
		else {
			return "nome=" + nome + ", cognome=" + cognome + ", email=" + email + ", ruolo=" + ruolo+",media="+this.mediaPunti;
		}
		
	}

	
}
