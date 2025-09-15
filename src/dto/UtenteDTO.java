package dto;

public class UtenteDTO {
	private String nome;
	private String cognome;
	private String email;
	private String ruolo;
	private float mediaPunti;
	private int taskCompletati;
	
	public UtenteDTO(String nome, String cognome, String email, String ruolo) {
		this.nome =nome;
		this.cognome = cognome;
		this.email = email;
		this.ruolo=ruolo;
		this.mediaPunti=0;
		this.taskCompletati=0;
	}
	
	public UtenteDTO(String nome, String cognome, String email, String ruolo,float mediaPunti) {
		this.nome =nome;
		this.cognome = cognome;
		this.email = email;
		this.ruolo=ruolo;
		this.mediaPunti=mediaPunti;
		this.taskCompletati=0;
	}
	
	public UtenteDTO(String nome, String cognome, String email, String ruolo,float mediaPunti,int taskCompletati) {
		this.nome =nome;
		this.cognome = cognome;
		this.email = email;
		this.ruolo=ruolo;
		this.mediaPunti=0;
		this.taskCompletati=taskCompletati;
	}
	
	public UtenteDTO(String nome, String cognome, String email, String ruolo,int taskCompletati) {
		this.nome =nome;
		this.cognome = cognome;
		this.email = email;
		this.ruolo=ruolo;
		this.mediaPunti=0;
		this.taskCompletati=taskCompletati;
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

	
	
	public int getTaskCompletati() {
		return taskCompletati;
	}

	public void setTaskCompletati(int taskCompletati) {
		this.taskCompletati = taskCompletati;
	}

	public float getMediaPunti() {
		return mediaPunti;
	}

	public void setMediaPunti(float mediaPunti) {
		this.mediaPunti = mediaPunti;
	}

	@Override
	public String toString() {
		if(this.mediaPunti!=0) {
			return "nome=" + nome + ", cognome=" + cognome + ", email=" + email + ", ruolo=" + ruolo+",Media="+this.mediaPunti;
		}
		else if(this.taskCompletati!=0) {
			return "nome=" + nome + ", cognome=" + cognome + ", email=" + email + ", ruolo=" + ruolo+",Task completati="+this.taskCompletati;
		}
		else {
			return "nome=" + nome + ", cognome=" + cognome + ", email=" + email + ", ruolo=" + ruolo;
		}
		
	}

	
}
