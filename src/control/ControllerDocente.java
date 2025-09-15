package control;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

import dto.ClasseDTO;
import dto.SoluzioneDTO;
import dto.TaskDTO;
import dto.UtenteDTO;
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
	
	public void creaTask(String titolo, String descrizione, LocalDate dataScadenza, int punteggioMax, String codice_classe) throws DataBaseException {
		Docente docente=(Docente) this.utenteCorrente;
		try {
			docente.caricaClassiDaDB();
			LocalDate dataPubblicazione=LocalDate.now();
			Classe classe=docente.getClasse(codice_classe);
			classe.creaTask(titolo, descrizione,dataPubblicazione, dataScadenza, punteggioMax);
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
		try {
			docente.caricaClassiDaDB();
			lista_classi_dto=docente.getListaClassiDTO();
			if(lista_classi_dto.isEmpty()) {
				throw new NotFoundException("Il docente non ha classi");
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			throw new DataBaseException("Errore durante l'accesso al Database "+e);
		}
		return lista_classi_dto;
	}
	
	public void iscrizioneDaDocente(String emailStudente, String codiceClasse) throws StudenteGiaIscrittoException, DataBaseException {
		Docente docente=(Docente) this.utenteCorrente;
		try {
			piattaforma=Piattaforma.getInstance();
			Classe classe=docente.getClasse(codiceClasse);
			Studente stud=(Studente)piattaforma.getUtente(emailStudente);
			stud.caricaClasseDaDB();
			if(stud.getClasse()!=null) {
				throw new StudenteGiaIscrittoException("Lo studente è già iscritto a una classe");
			}
			classe.iscrizioneDaDocente(emailStudente);
		} catch (ClassNotFoundException | SQLException e) {
			throw new DataBaseException("Errore durante l'accesso al Database "+e);
		} 
	}

	public ArrayList<TaskDTO> getListaTask(String codiceClasse) throws DataBaseException, NotFoundException{
		ArrayList<TaskDTO> lista_task_dto=new ArrayList<>();
		Docente docente =(Docente) this.utenteCorrente;
		try {
			Classe classe=docente.getClasse(codiceClasse);
			classe.caricaTaskDaDB();
			lista_task_dto=classe.getListaTaskDTO();
			if(lista_task_dto.isEmpty()) {
				throw new NotFoundException("Non ci sono task associati alla classe");
			}
		} catch (ClassNotFoundException | SQLException e) {
			throw new DataBaseException("Errore durante l'accesso al Database "+e);
		}
		return lista_task_dto;
	}
	
	public void assegnaPunteggio(String codiceClasse,int idTask,int idSoluzione,int punteggio) throws PunteggioTroppoAltoException  {
		Docente docente=(Docente) this.utenteCorrente;
		try {
			Classe classe=docente.getClasse(codiceClasse);
			Task task=classe.getTask(idTask);
			Soluzione soluzione=task.getSoluzione(idSoluzione);
			if(punteggio>task.getPunteggioMax())
				throw new PunteggioTroppoAltoException("Il punteggio inserito supera il punteggio massimo");
			soluzione.assegnaPunteggio(punteggio);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		} 	
	}
	
	public ArrayList<SoluzioneDTO> getListaSoluzioni(String codiceClasse,int idTask) throws DataBaseException, NotFoundException{
		ArrayList<SoluzioneDTO> lista_soluzioni_dto=new ArrayList<>();
		Docente docente=(Docente) this.utenteCorrente;
		try {
			Classe classe=docente.getClasse(codiceClasse);
			Task task=classe.getTask(idTask);
			task.caricaSoluzioniDaDB();
			lista_soluzioni_dto=task.getListaSoluzioniDTO();
			if(lista_soluzioni_dto.isEmpty()) {
				throw new NotFoundException("Non ci sono soluzioni da correggere associate al task");
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			throw new DataBaseException("Errore durante l'accesso al Database "+e);
		}
		return lista_soluzioni_dto;
	}
	
	public ArrayList<UtenteDTO> getListaStudentiClasse(String codiceClasse) throws DataBaseException, NotFoundException{
		ArrayList<UtenteDTO> lista_studenti_dto=new ArrayList<>();
		Docente docente=(Docente) this.utenteCorrente;
		try {
			docente.caricaClassiDaDB();
			Classe classe=docente.getClasse(codiceClasse);
			classe.caricaStudentiDaDB();
			lista_studenti_dto=classe.getListaStudentiDTO();
			if(lista_studenti_dto.isEmpty())
				throw new NotFoundException("Non ci sono studenti iscritti alla classe");
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			throw new DataBaseException("Errore durante l'accesso al Database "+e);
		}
		return lista_studenti_dto;
	}
	
	public ArrayList<UtenteDTO> getListaStudentiPiattaforma() throws DataBaseException, NotFoundException{
		ArrayList<UtenteDTO> lista_studenti_dto=new ArrayList<>();
		piattaforma=Piattaforma.getInstance();
		try {
			piattaforma.caricaUtentiDaDB();
			lista_studenti_dto=piattaforma.getListaStudentiPiattaforma();
			if(lista_studenti_dto.isEmpty()) {
				throw new NotFoundException("Non ci sono studenti iscritti alla piattaforma");
			}
		} catch (ClassNotFoundException | SQLException e) {
			throw new DataBaseException("Errore durante l'accesso al Database "+e);
		}
		return lista_studenti_dto;
	}
}