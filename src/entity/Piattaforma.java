package entity;

import database.*;
import dto.UtenteDTO;
import eccezioni.NotFoundException;
import eccezioni.UtenteGiaRegistratoException;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.SQLException;


import java.util.ArrayList;

public class Piattaforma {
	private static Piattaforma instance;
	private ArrayList<Utente> utenti; 
	
	public Piattaforma() {
		utenti=new ArrayList<>();
		//caricaUtentiDaDB();
	}
	
	public static Piattaforma getInstance()  {
		if(instance==null) {
			instance=new Piattaforma();
		}
		return instance;
	}
	
	public void registrazione(String nome, String cognome, String email, String password, String ruolo) throws ClassNotFoundException, SQLException, UtenteGiaRegistratoException, IllegalArgumentException {
		if(getUtente(email)==null) {
			if(ruolo.equalsIgnoreCase("Docente")){
				Docente docente=new Docente(nome,cognome,email,password);
				docente.salvaInDB();
			}
			else if(ruolo.equalsIgnoreCase("Studente"))  {
				Studente studente=new Studente(nome,cognome,email,password);
				studente.salvaInDB();
			}
		}
		else {
			throw new UtenteGiaRegistratoException("Questo utente è già registrato");
		}
	}
	
	public void caricaUtentiDaDB() throws ClassNotFoundException, SQLException {
		if(utenti.isEmpty()) {
			DaoDocente docenteDB=new DaoDocente();
			ArrayList<DaoDocente> listaDocenti=docenteDB.getListaDocenti();
			DaoStudente studenteDB=new DaoStudente();
			ArrayList<DaoStudente> listaStudenti=studenteDB.getListaStudenti();
			for(DaoDocente d: listaDocenti) {
				Docente docente_temp=new Docente(d);
				utenti.add(docente_temp);
			}
			for(DaoStudente s: listaStudenti) {
				Studente studente_temp=new Studente(s);
				utenti.add(studente_temp);
			}
		}
	}
	
	
	public Utente autenticazione(String email, String password, String ruolo) throws ClassNotFoundException, SQLException, NotFoundException {
	    Utente utenteCorrente = null;
	    if(ruolo.equalsIgnoreCase("Docente")) {
	    	utenteCorrente=new Docente(email);
	    }
	    else {
	    	utenteCorrente=new Studente(email);
	    }
	    
	    if (utenteCorrente.getEmail()==null) {
	        throw new NotFoundException("L'utente non è presente nel database");
	    }
	    if (!BCrypt.checkpw(password, utenteCorrente.getPassword())) {
	        throw new IllegalArgumentException("Nessun utente registrato con questa password");
	    }
	    return utenteCorrente;
	}
	
	public ArrayList<UtenteDTO> getListaUtentiPiattaforma() throws ClassNotFoundException, SQLException{
		ArrayList<UtenteDTO> lista_utenti_dto=new ArrayList<>();
		for(int i=0;i<utenti.size();i++) {
			String nome=utenti.get(i).getNome();
			String cognome=utenti.get(i).getCognome();
			String email=utenti.get(i).getEmail();
			String ruolo=utenti.get(i).getRuolo();
			lista_utenti_dto.add(new UtenteDTO(nome,cognome,email,ruolo));
		}
		return lista_utenti_dto;
	}
	
	public ArrayList<UtenteDTO> getListaStudentiPiattaforma() throws ClassNotFoundException, SQLException{
		ArrayList<UtenteDTO> lista_studenti_dto=new ArrayList<>();
		for(Utente u: utenti) {
			if(u.getRuolo().equalsIgnoreCase("Studente")) 
				lista_studenti_dto.add(new UtenteDTO(u.getNome(),u.getCognome(),u.getEmail(),u.getRuolo()));
		}
		return lista_studenti_dto;
	}
	
	
	public Utente getUtente(String email) throws ClassNotFoundException, SQLException {
		caricaUtentiDaDB();
		boolean trovato=false;
		int i=0;
		while(i<utenti.size() && !trovato) {
			if(utenti.get(i).getEmail().equalsIgnoreCase(email)) {
				trovato=true;
			}
			else {
				i++;
			}
		}
		if(trovato)
			return utenti.get(i);
		else
			return null;
	}
	
	public void CheckCredentials(String email,String password) throws IllegalArgumentException {
		boolean trovato=false;
		int i=0;
		while(i<utenti.size() && !trovato) {
			if(utenti.get(i).getEmail().equalsIgnoreCase(email)) 
				throw new IllegalArgumentException("Questa email è già registrata a un altro utente");
			else if(utenti.get(i).getPassword().equalsIgnoreCase(password))
				throw new IllegalArgumentException("Questa password è già stata registrata a un altro utente");
			else 
				i++;
		}
	}

	public  ArrayList<Utente> getUtenti() {
		return utenti;
	}

	public  void setUtenti(ArrayList<Utente> utenti) {
		this.utenti = utenti;
	}
	
}
