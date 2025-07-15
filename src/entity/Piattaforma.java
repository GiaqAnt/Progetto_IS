package entity;

import database.*;
import dto.UtenteDTO;
import eccezioni.NotFoundException;
import eccezioni.UtenteGiaRegistratoException;

import java.sql.SQLException;


import java.util.ArrayList;

public class Piattaforma {
	private static Piattaforma instance;
	private static ArrayList<Utente> utenti; 
	
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
	
	public void registrazione(String nome, String cognome, String email, String ruolo) throws ClassNotFoundException, SQLException, UtenteGiaRegistratoException {
		if(getUtente(email)==null) {
			if(ruolo.equalsIgnoreCase("Docente")){
				Docente docente=new Docente(nome,cognome,email);
				docente.salvaInDB();
			}
			else if(ruolo.equalsIgnoreCase("Studente"))  {
				Studente studente=new Studente(nome,cognome,email);
				studente.salvaInDB();
			}
		}
		else {
			throw new UtenteGiaRegistratoException("Questo utente è gi registrato");
		}
	}
	
	public void caricaUtentiDaDB() throws ClassNotFoundException, SQLException {
		if(utenti.isEmpty()) {
			DaoDocente docenteDB=new DaoDocente();
			ArrayList<DaoDocente> listaDocenti=docenteDB.getListaDocenti();
			DaoStudente studenteDB=new DaoStudente();
			ArrayList<DaoStudente> listaStudenti=studenteDB.getListaStudenti();
			for(int i=0;i<listaDocenti.size();i++) {
				Docente docente_temp=new Docente(listaDocenti.get(i));
				docente_temp.setRuolo("Docente");
				utenti.add(docente_temp);
			}
			for(int i=0;i<listaStudenti.size();i++) {
				Studente studente_temp=new Studente(listaStudenti.get(i));
				studente_temp.setRuolo("Studente");
				utenti.add(studente_temp);
			}
		}
	}
	
	
	public Utente autenticazione(String email, String ruolo) throws ClassNotFoundException, SQLException, NotFoundException {
		Utente utenteCorrente= null;
		if(ruolo.equalsIgnoreCase("Docente")) {
			utenteCorrente=new Docente(email);
			utenteCorrente.setRuolo(ruolo);
		}
		else {
			utenteCorrente=new Studente(email);
			utenteCorrente.setRuolo(ruolo);
		}
		if(utenteCorrente.getEmail()==null) {
			throw new NotFoundException("L'utente non è presente nel database");
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
		for(int i=0;i<utenti.size();i++) {
			String nome=utenti.get(i).getNome();
			String cognome=utenti.get(i).getCognome();
			String email=utenti.get(i).getEmail();
			String ruolo=utenti.get(i).getRuolo();
			if(ruolo.equalsIgnoreCase("Studente")) {
				lista_studenti_dto.add(new UtenteDTO(nome,cognome,email,ruolo));
			}
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

	public static ArrayList<Utente> getUtenti() {
		return utenti;
	}

	public static void setUtenti(ArrayList<Utente> utenti) {
		Piattaforma.utenti = utenti;
	}
	
}
