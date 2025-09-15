package control;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

import dto.TaskDTO;
import dto.UtenteDTO;
import eccezioni.ConsegnaInRitardoException;
import eccezioni.DataBaseException;
import eccezioni.GiaConsegnatoException;
import eccezioni.NotFoundException;
import entity.Classe;
import entity.Soluzione;
import entity.Studente;
import entity.Task;
import entity.Utente;

public class ControllerStudente {
	private static ControllerStudente instance;
	private Utente utenteCorrente;
	
	public ControllerStudente() {}
	
	public static ControllerStudente getInstance() {
		if(instance==null) {
			instance=new ControllerStudente();
		}
		return instance;
	}
	
	public void setUtenteCorrente(Utente utente) {
		this.utenteCorrente=utente;
	}
	
	public Utente getUtenteCorrente() {
		return utenteCorrente;
	}
	
	
	public ArrayList<TaskDTO> getListaTaskStudente() throws DataBaseException, NotFoundException{
		ArrayList<TaskDTO> lista_task_dto=new ArrayList<>();
		try {
			Studente stud=(Studente)this.utenteCorrente;
			stud.caricaClasseDaDB();
			Classe classe=stud.getClasse();
			classe.caricaTaskDaDB();
			lista_task_dto=classe.getListaTaskDTO();
			if(lista_task_dto.isEmpty()) 
				throw new NotFoundException("Non ci sono task assegnati alla classe");
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			throw new DataBaseException("Errore durante l'accesso al Database "+e);	
		}
		return lista_task_dto;
	}
	
	public void consegnaSoluzione(int idTask,File contenuto) throws IOException, DataBaseException, ConsegnaInRitardoException, GiaConsegnatoException {
		Studente stud=(Studente)this.utenteCorrente;
		LocalDate dataConsegna=LocalDate.now();
		byte[] byteContenuto=Files.readAllBytes(contenuto.toPath());
		try {
			Classe classe=stud.getClasse();
			Task task=classe.getTask(idTask);
			LocalDate dataScadenza=task.getData_scadenza();
			if(dataScadenza.isBefore(dataConsegna))
				throw new ConsegnaInRitardoException("Il task è scaduto");			stud.caricaSoluzioniDaDB();
			for(Soluzione s: stud.getSoluzioni()) {
				s.caricaTaskDaDB();
				if(s.getTask().equals(task)) {
					throw new GiaConsegnatoException("Lo studente ha già consegnato una soluzione per questo task");
				}
			}
			stud.consegnaSoluzione(task,byteContenuto,dataConsegna);
		} catch (ClassNotFoundException | SQLException e) {
			throw new DataBaseException("Errore durante l'accesso al Database "+e);	
		}
	}
	
	public ArrayList<UtenteDTO> getClassificaStudentiMedia() throws DataBaseException{
		ArrayList<UtenteDTO> lista_studenti_dto=new ArrayList<>();
		Studente stud=(Studente)this.utenteCorrente;
		try {
			stud.caricaClasseDaDB();
			Classe classe=stud.getClasse();
			classe.caricaStudentiDaDB();
			for (Studente s: classe.getStudenti()) 
				s.caricaSoluzioniDaDB();
			lista_studenti_dto=classe.getClassificaStudentiMedia();
		} catch (ClassNotFoundException | SQLException e) {
			throw new DataBaseException("Errore durante l'accesso al Database "+e);	
		}
		return lista_studenti_dto;
	}
	
	public ArrayList<UtenteDTO> getClassificaStudentiTask() throws DataBaseException{
		ArrayList<UtenteDTO> lista_studenti_dto=new ArrayList<>();
		Studente stud=(Studente)this.utenteCorrente;
		try {
			stud.caricaClasseDaDB();
			Classe classe=stud.getClasse();
			classe.caricaStudentiDaDB();
			for (Studente s: classe.getStudenti()) 
				s.caricaSoluzioniDaDB();
			lista_studenti_dto=classe.getClassificaStudentiTask();
		} catch (ClassNotFoundException | SQLException e) {
			throw new DataBaseException("Errore durante l'accesso al Database "+e);	
		}
		return lista_studenti_dto;
	}
	
	public float getMedia() throws DataBaseException {
		Studente stud=(Studente) this.utenteCorrente;
		try {
			stud.caricaSoluzioniDaDB();
		} catch (ClassNotFoundException | SQLException e) {
			throw new DataBaseException("Errore durante l'accesso al Database "+e);			}
		return stud.calcolaMediaPunti();
	}
	
	public int getPunteggioTotale() throws DataBaseException {
		Studente stud=(Studente) this.utenteCorrente;
		try {
			stud.caricaSoluzioniDaDB();
		} catch (ClassNotFoundException | SQLException e) {
			throw new DataBaseException("Errore durante l'accesso al Database "+e);			}
		return stud.calcolaPunteggioTotale();
	}
	
	public void iscrizioneDaStudente(String codiceClasse) throws DataBaseException {
		try {
			Studente stud=(Studente) this.utenteCorrente;
			Classe classe=new Classe(codiceClasse);
			classe.iscrizioneDaStudente(stud);
		} catch (ClassNotFoundException | SQLException e) {
			throw new DataBaseException("Errore durante l'accesso al Database "+e);
		}
	}
}
