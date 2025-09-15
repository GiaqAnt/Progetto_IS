package control;

import java.sql.SQLException;

import eccezioni.DataBaseException;
import eccezioni.NotFoundException;
import eccezioni.UtenteGiaRegistratoException;
import entity.Piattaforma;
import entity.Utente;

public class ControllerUtente {
	private static ControllerUtente instance;
	private Utente utenteCorrente;
	private	static Piattaforma piattaforma;
	
	public ControllerUtente() {
		piattaforma=Piattaforma.getInstance();
	}
	
	public static ControllerUtente getInstance() {
		if(instance==null) {
			instance=new ControllerUtente();
		}
		return instance;
	}
	
	public void setUtenteCorrente(Utente utente) {
		this.utenteCorrente=utente;
	}
	
	public Utente getUtenteCorrente() {
		return utenteCorrente;
	}


	public void autenticazione(String email, String password,String ruolo) throws DataBaseException, NotFoundException {
		Utente utente=null;
		try {
			utente = piattaforma.autenticazione(email,password,ruolo);
		} 
		catch (ClassNotFoundException | SQLException e) {
			throw new DataBaseException("Errore durante l'accesso al Database"+e);
		}
		this.utenteCorrente=utente;
		if(ruolo.equalsIgnoreCase("Docente")) {
			ControllerDocente controller_d=ControllerDocente.getInstance();
			controller_d.setUtenteCorrente(utente);
		}
		
		else {
			ControllerStudente controller_s=ControllerStudente.getInstance();
			controller_s.setUtenteCorrente(utente);
		}
	}
	
	
	
	public void registrazione(String nome, String cognome, String email, String password, String ruolo) throws DataBaseException, UtenteGiaRegistratoException, IllegalArgumentException{
		try {
			piattaforma.CheckCredentials(email, password);
			piattaforma.registrazione(nome, cognome, email,password, ruolo);
		} catch (ClassNotFoundException | SQLException e) {
			throw new DataBaseException("Errore durante l'accesso al Database: "+e);
		}
	}
	
	
}
