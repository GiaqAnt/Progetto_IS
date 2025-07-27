package entity;

import java.sql.SQLException;

import database.DaoDocente;
import database.DaoStudente;

public class Utente {
	protected String nome;
	protected String cognome;
	protected String email;
	protected String ruolo;
	
	
	public Utente(String nome, String cognome, String email, String ruolo) {
		this.nome = nome;
		this.cognome = cognome;
		this.email = email;
		this.ruolo=ruolo;
	}
	
	public Utente(DaoDocente docenteDB) {
		this.nome=docenteDB.getNome();
		this.cognome=docenteDB.getCognome();
		this.email=docenteDB.getEmail();
	}
	
	public Utente(DaoStudente studenteDB) {
		this.nome=studenteDB.getNome();
		this.cognome=studenteDB.getCognome();
		this.email=studenteDB.getEmail();
	}
	
	public Utente(String email, String ruolo) throws ClassNotFoundException, SQLException {
		this.ruolo=ruolo;
		if(ruolo.equalsIgnoreCase("Docente")) {
			DaoDocente docenteDB=new DaoDocente(email);
			this.email=docenteDB.getEmail();
			this.nome=docenteDB.getNome();
			this.cognome=docenteDB.getCognome();
		}
		else {
			DaoStudente studenteDB=new DaoStudente(email);
			this.email=studenteDB.getEmail();
			this.nome=studenteDB.getNome();
			this.cognome=studenteDB.getCognome();
		}
	}

	public void caricaDaDB() throws ClassNotFoundException, SQLException {
		if(this.ruolo.equalsIgnoreCase("Docente"))  {
			DaoDocente docenteDB=new DaoDocente(this.email);
			this.nome=docenteDB.getNome();
			this.cognome=docenteDB.getCognome();
			this.email=docenteDB.getEmail();
		}
		else {
			DaoStudente studenteDB=new DaoStudente(this.email);
			this.nome=studenteDB.getNome();
			this.cognome=studenteDB.getCognome();
			this.email=studenteDB.getEmail();
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

	@Override
	public String toString() {
		return "Utente [nome=" + nome + ", cognome=" + cognome + ", email=" + email + ", ruolo=" + ruolo + "]";
	}
	
	
}
