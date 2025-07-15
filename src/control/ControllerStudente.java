package control;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

import dto.TaskDTO;
import dto.UtenteDTO;
import eccezioni.DataBaseException;
import eccezioni.NotFoundException;
import entity.Classe;
import entity.Studente;
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
		Studente stud=(Studente)this.utenteCorrente;
		System.out.println(stud.getClasse());
		Classe classe=stud.getClasse();
		try {
			classe.caricaTaskDaDB();
			lista_task_dto=classe.getListaTask();
			if(lista_task_dto.isEmpty()) {
				throw new NotFoundException("Non ci sono task assegnati allo studente");
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			throw new DataBaseException("Errore durante l'accesso al Database "+e);	
		}
		return lista_task_dto;
	}
	
	public void consegnaSoluzione(int idTask,File contenuto) throws IOException, DataBaseException {
		Studente stud=(Studente)this.utenteCorrente;
		LocalDate data_consegna=LocalDate.now();
		try {
			stud.consegnaSoluzione(idTask,Files.readAllBytes(contenuto.toPath()),data_consegna);
		} catch (ClassNotFoundException | SQLException e) {
			throw new DataBaseException("Errore durante l'accesso al Database "+e);	
		}
	}
	
	public ArrayList<UtenteDTO> getClassificaStudenti() throws DataBaseException{
		ArrayList<UtenteDTO> lista_studenti_dto=new ArrayList<>();
		Studente stud=(Studente)this.utenteCorrente;
		Classe classe=stud.getClasse();
		try {
			classe.caricaStudentiDaDB();
			for (Studente s: classe.getStudenti()) 
				s.caricaSoluzioniDaDB();
			lista_studenti_dto=classe.getClassificaStudenti();
		} catch (ClassNotFoundException | SQLException e) {
			throw new DataBaseException("Errore durante l'accesso al Database "+e);	
		}
		return lista_studenti_dto;
	}
}
