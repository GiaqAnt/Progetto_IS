package entity;

import java.sql.SQLException;

import database.DaoDocente;
import database.DaoStudente;

public abstract class Utente {
	protected String nome;
	protected String cognome;
	protected String email;
	protected String password;
	protected String ruolo;
	
	
	public Utente(String nome, String cognome, String email, String password, String ruolo) {
		this.nome = nome;
		this.cognome = cognome;
		this.email = email;
		this.password=password;
		this.ruolo=ruolo;
	}
	
	public Utente(DaoDocente docenteDB) {
		this.nome=docenteDB.getNome();
		this.cognome=docenteDB.getCognome();
		this.email=docenteDB.getEmail();
		this.ruolo="Docente";
	}
	
	public Utente(DaoStudente studenteDB) {
		this.nome=studenteDB.getNome();
		this.cognome=studenteDB.getCognome();
		this.email=studenteDB.getEmail();
		this.ruolo="Studente";
	}
	
	public Utente(String email, String ruolo) throws ClassNotFoundException, SQLException {
		this.ruolo=ruolo;
		if(ruolo.equalsIgnoreCase("Docente")) {
			DaoDocente docenteDB=new DaoDocente(email);
			this.email=docenteDB.getEmail();
			this.nome=docenteDB.getNome();
			this.cognome=docenteDB.getCognome();
			this.password=docenteDB.getPassword();
		}
		else {
			DaoStudente studenteDB=new DaoStudente(email);
			this.email=studenteDB.getEmail();
			this.nome=studenteDB.getNome();
			this.cognome=studenteDB.getCognome();
			this.password=studenteDB.getPassword();
		}
	}
	
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCognome() {
		return cognome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getRuolo() {
		return ruolo;
	}

	public void setRuolo(String ruolo) {
		this.ruolo = ruolo;
	}

	
	
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "Utente [nome=" + nome + ", cognome=" + cognome + ", email=" + email + ", ruolo=" + ruolo + "]";
	}
	
	
}
