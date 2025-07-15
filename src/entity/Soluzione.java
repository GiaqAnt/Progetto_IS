package entity;

import java.sql.SQLException;
import java.time.LocalDate;

import database.DaoSoluzione;
import database.DaoStudente;
import database.DaoTask;
import eccezioni.PunteggioTroppoAltoException;


public class Soluzione {
	private int idSoluzione;
	private byte[] contenuto;
	private int punteggio;
	private LocalDate data_consegna;
	private Task task;
	private Studente studente;
	
	public Soluzione(int idTask, byte[] contenuto, LocalDate data_consegna, String emailStudente) throws ClassNotFoundException, SQLException {
		this.contenuto=contenuto;
		this.data_consegna=data_consegna;
		this.task=new Task(idTask);
		this.studente=new Studente(emailStudente);
	}
	
	public Soluzione(DaoSoluzione soluzioneDB) throws ClassNotFoundException, SQLException {
		this.idSoluzione=soluzioneDB.getIdSoluzione();
		this.contenuto=soluzioneDB.getContenuto();
		this.punteggio=soluzioneDB.getPunteggio();
		this.data_consegna=soluzioneDB.getData_consegna();
		this.task=new Task(soluzioneDB.getTask());
		this.studente=new Studente(soluzioneDB.getStudente());
	}
	
	public Soluzione(int idSoluzione) throws ClassNotFoundException, SQLException {
		DaoSoluzione soluzioneDB=new DaoSoluzione(idSoluzione);
		this.idSoluzione=soluzioneDB.getIdSoluzione();
		this.contenuto=soluzioneDB.getContenuto();
		this.data_consegna=soluzioneDB.getData_consegna();
		this.punteggio=soluzioneDB.getPunteggio();
		this.task=new Task(soluzioneDB.getTask());
		this.studente=new Studente(soluzioneDB.getStudente());
	}
	
	public void salvaInDB() throws ClassNotFoundException, SQLException {
		DaoSoluzione soluzioneDB=new DaoSoluzione();
		soluzioneDB.setContenuto(contenuto);
		soluzioneDB.setData_consegna(data_consegna);
		soluzioneDB.setStudente(new DaoStudente(this.studente.getEmail()));
		soluzioneDB.setTask(new DaoTask(this.task.getIdTask()));
		soluzioneDB.salvaInDB();
	}
	
	public void assegnaPunteggio(int punteggio) throws ClassNotFoundException, SQLException, PunteggioTroppoAltoException {
		DaoSoluzione soluzioneDB=new DaoSoluzione(this.idSoluzione);
		soluzioneDB.assegnaPunteggio(punteggio);
	}
	

	public int getIdSoluzione() {
		return idSoluzione;
	}

	public void setIdSoluzione(int idSoluzione) {
		this.idSoluzione = idSoluzione;
	}

	public byte[] getContenuto() {
		return contenuto;
	}

	public void setContenuto(byte[] contenuto) {
		this.contenuto = contenuto;
	}

	public int getPunteggio() {
		return punteggio;
	}

	public void setPunteggio(int punteggio) {
		this.punteggio = punteggio;
	}

	public LocalDate getData_consegna() {
		return data_consegna;
	}

	public void setData_consegna(LocalDate data_consegna) {
		this.data_consegna = data_consegna;
	}

	public Task getTask() {
		return task;
	}

	public void setTask(Task task) {
		this.task = task;
	}

	public Studente getStudente() {
		return studente;
	}

	public void setStudente(Studente studente) {
		this.studente = studente;
	}
	
	
}
