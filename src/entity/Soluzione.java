package entity;

import java.sql.SQLException;
import java.time.LocalDate;

import database.DaoSoluzione;


public class Soluzione {
	private int idSoluzione;
	private byte[] contenuto;
	private int punteggio;
	private LocalDate dataConsegna;
	private Task task;
	private Studente studente;
	
	public Soluzione(Task task, byte[] contenuto, int punteggio, LocalDate data_consegna, Studente studente) throws ClassNotFoundException, SQLException {
		this.contenuto=contenuto;
		this.dataConsegna=data_consegna;
		this.task=task;
		this.studente=studente;
		this.punteggio=punteggio;
	}
	
	public Soluzione(Task task, byte[] contenuto, LocalDate data_consegna, Studente studente) throws ClassNotFoundException, SQLException {
		this.contenuto=contenuto;
		this.dataConsegna=data_consegna;
		this.task=task;
		this.studente=studente;
	}
	
	public Soluzione(Task task, byte[] contenuto, int punteggio, LocalDate data_consegna) throws ClassNotFoundException, SQLException {
		this.contenuto=contenuto;
		this.dataConsegna=data_consegna;
		this.task=task;
		this.punteggio=punteggio;
	}
	
	public Soluzione(int idSoluzione, byte[] contenuto, int punteggio, LocalDate data_consegna) throws ClassNotFoundException, SQLException {
		this.idSoluzione=idSoluzione;
		this.contenuto=contenuto;
		this.dataConsegna=data_consegna;
		this.punteggio=punteggio;
	}
	
	public Soluzione(Task task, int idSoluzione, byte[] contenuto, int punteggio, LocalDate data_consegna, Studente stud) throws ClassNotFoundException, SQLException {
		this.idSoluzione=idSoluzione;
		this.contenuto=contenuto;
		this.dataConsegna=data_consegna;
		this.punteggio=punteggio;
		this.task=task;
		this.studente=stud;
	}
	
	public Soluzione(Task task, int idSoluzione, byte[] contenuto, int punteggio, LocalDate data_consegna) throws ClassNotFoundException, SQLException {
		this.idSoluzione=idSoluzione;
		this.contenuto=contenuto;
		this.dataConsegna=data_consegna;
		this.punteggio=punteggio;
		this.task=task;
	}
	
	public Soluzione(DaoSoluzione soluzioneDB) throws ClassNotFoundException, SQLException {
		this.idSoluzione=soluzioneDB.getIdSoluzione();
		this.contenuto=soluzioneDB.getContenuto();
		this.punteggio=soluzioneDB.getPunteggio();
		this.dataConsegna=soluzioneDB.getData_consegna();
		
	}
	
	public Soluzione(int idSoluzione) throws ClassNotFoundException, SQLException {
		DaoSoluzione soluzioneDB=new DaoSoluzione(idSoluzione);
		this.idSoluzione=soluzioneDB.getIdSoluzione();
		this.contenuto=soluzioneDB.getContenuto();
		this.dataConsegna=soluzioneDB.getData_consegna();
		this.punteggio=soluzioneDB.getPunteggio();
		this.studente=new Studente(soluzioneDB.getEmailStudente());
		this.task=new Task(soluzioneDB.getIdTask());
		
	}
	
	public void salvaInDB() throws ClassNotFoundException, SQLException {
		DaoSoluzione soluzioneDB=new DaoSoluzione(contenuto,dataConsegna);
		soluzioneDB.salvaInDB(this.task.getIdTask(),this.studente.getEmail());
	}
	
	public void assegnaPunteggio(int punteggio) throws ClassNotFoundException, SQLException {
		DaoSoluzione soluzioneDB=new DaoSoluzione(this.idSoluzione,this.contenuto,punteggio,this.dataConsegna);
		soluzioneDB.assegnaPunteggio(punteggio);
	}
	
	public void caricaTaskDaDB() throws ClassNotFoundException, SQLException {
		DaoSoluzione soluzioneDB=new DaoSoluzione(this.idSoluzione,this.contenuto,this.punteggio,this.dataConsegna);
		this.task=new Task(soluzioneDB.getIdTask());
	}
	
	public void caricaStudenteDaDB() throws ClassNotFoundException, SQLException {
		DaoSoluzione soluzioneDB=new DaoSoluzione(this.idSoluzione,this.contenuto,this.punteggio,this.dataConsegna);
		this.studente=new Studente(soluzioneDB.getEmailStudente());
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
		return dataConsegna;
	}

	public void setData_consegna(LocalDate data_consegna) {
		this.dataConsegna = data_consegna;
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
