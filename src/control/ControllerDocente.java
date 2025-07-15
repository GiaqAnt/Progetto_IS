package control;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

import dto.ClasseDTO;
import dto.SoluzioneDTO;
import dto.TaskDTO;
import eccezioni.DataBaseException;
import eccezioni.NotFoundException;
import eccezioni.PunteggioTroppoAltoException;
import eccezioni.StudenteGiaIscrittoException;
import entity.Classe;
import entity.Docente;
import entity.Piattaforma;
import entity.Soluzione;
import entity.Studente;
import entity.Task;
import entity.Utente;
import utilities.CodiceCasuale;

public class ControllerDocente {
	private static ControllerDocente instance;
	private Utente utenteCorrente;	
	private static Piattaforma piattaforma;
	
	
	public ControllerDocente() {
		//this.piattaforma=Piattaforma.getInstance();
	}
	
	public static ControllerDocente getInstance() {
		if(instance==null) {
			instance=new ControllerDocente();
		}
		return instance;
	}
	
	public void setUtenteCorrente(Utente utente) {
		this.utenteCorrente=utente;
	}
	
	public Utente getUtenteCorrente() {
		return utenteCorrente;
	}
	
	public void creaTask(String titolo, String descrizione, LocalDate data_pubblicazione, LocalDate data_scadenza, int punteggioMax, String codice_classe) throws DataBaseException {
		try {
			Classe classe=new Classe(codice_classe);
			classe.creaTask(titolo, descrizione,data_pubblicazione, data_scadenza, punteggioMax);
		} 
		catch (ClassNotFoundException | SQLException e) {
			throw new DataBaseException("Errore durante l'accesso al Database "+e);
		}
	}
	
	public void creaClasse(String nome) throws DataBaseException {
		String codice=CodiceCasuale.generaCodiceCasuale();
		Docente docente=(Docente) this.utenteCorrente;
		try {
			docente.creaClasse(nome, codice);
		} catch (ClassNotFoundException | SQLException e) {
			throw new DataBaseException("Errore durante l'accesso al Database "+e);
		}
	}

	public ArrayList<ClasseDTO> getListaClassi() throws DataBaseException, NotFoundException{
		ArrayList<ClasseDTO> lista_classi_dto=new ArrayList<>();
		Docente docente=(Docente) this.utenteCorrente;
		System.out.println(docente.getEmail());
		try {
			docente.caricaClassiDaDB();
			lista_classi_dto=docente.getListaClassiDTO();
			if(lista_classi_dto.isEmpty()) {
				throw new NotFoundException("Il docente non ha classi");
			}
		} catch (ClassNotFoundException | SQLException e) {
			throw new DataBaseException("Errore durante l'accesso al Database "+e);
		}
		return lista_classi_dto;
	}
	
	public void iscrizioneDaDocente(String email, String codice_classe) throws StudenteGiaIscrittoException, DataBaseException {
		try {
			piattaforma=Piattaforma.getInstance();
			Classe classe=new Classe(codice_classe);
			Studente stud=(Studente)piattaforma.getUtente(email);
			if(stud.getClasse()!=null) {
				throw new StudenteGiaIscrittoException("Lo studente è già iscritto a una classe");
			}
			else {
				classe.iscrizioneDaDocente(email);
			}
		} catch (ClassNotFoundException | SQLException e) {
			throw new DataBaseException("Errore durante l'accesso al Database "+e);
		} 
	}

	public ArrayList<TaskDTO> getListaTask(String codice_classe) throws DataBaseException, NotFoundException{
		ArrayList<TaskDTO> lista_task_dto=new ArrayList<>();
		try {
			Classe classe=new Classe(codice_classe);
			classe.caricaTaskDaDB();
			lista_task_dto=classe.getListaTask();
			if(lista_task_dto.isEmpty()) {
				throw new NotFoundException("Non ci sono task associati alla classe");
			}
		} catch (ClassNotFoundException | SQLException e) {
			throw new DataBaseException("Errore durante l'accesso al Database "+e);
		}
		return lista_task_dto;
	}
	
	public void assegnaPunteggio(int idSoluzione,int punteggio) throws PunteggioTroppoAltoException  {
		try {
			Soluzione soluzione=new Soluzione(idSoluzione);
			if(punteggio>soluzione.getTask().getPunteggioMax())
				throw new PunteggioTroppoAltoException("Il punteggio inserito supera il punteggio massimo");
			soluzione.assegnaPunteggio(punteggio);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		} 	
	}
	
	public ArrayList<SoluzioneDTO> getListaSoluzioni(int idTask) throws DataBaseException, NotFoundException{
		ArrayList<SoluzioneDTO> lista_soluzioni_dto=new ArrayList<>();
		try {
			Task task=new Task(idTask);
			task.caricaSoluzioniDaDB();
			lista_soluzioni_dto=task.getListaSoluzioni();
			if(lista_soluzioni_dto.isEmpty()) {
				throw new NotFoundException("Non ci sono soluzioni da correggere associate al task");
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			throw new DataBaseException("Errore durante l'accesso al Database "+e);
		}
		return lista_soluzioni_dto;
	}
}