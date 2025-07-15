package database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;


public class DaoSoluzione {
	private int idSoluzione;
	private byte[] contenuto;
	private int punteggio;
	private LocalDate data_consegna;
	private DaoTask task;
	private DaoStudente studente;
	
	public DaoSoluzione() {}
	
	public DaoSoluzione(int idSoluzione) throws ClassNotFoundException, SQLException {
		this.idSoluzione=idSoluzione;
		caricaDaDB();
	}
	
	public DaoSoluzione(byte[] contenuto, LocalDate data_consegna, int idTask,String email_studente) throws ClassNotFoundException, SQLException {
		this.contenuto=contenuto;
		this.data_consegna=data_consegna;
		this.task=new DaoTask(idTask);
		this.studente=new DaoStudente(email_studente);
	}	
	
	public DaoSoluzione(int idSoluzione, byte[] contenuto, int punteggio, LocalDate data_consegna, DaoTask task,
			DaoStudente studente) {
		this.idSoluzione = idSoluzione;
		this.contenuto = contenuto;
		this.punteggio = punteggio;
		this.data_consegna = data_consegna;
		this.task = task;
		this.studente = studente;
	}

	public void caricaDaDB() throws ClassNotFoundException, SQLException {
		String query="SELECT * FROM Soluzioni WHERE Id_soluzione='"+this.idSoluzione+"';";
		System.out.println(query);
		ResultSet rs=DBConnectionManager.selectQuery(query);
		if (rs.next()) {
			this.setContenuto(rs.getBytes("Contenuto"));
			this.setPunteggio(rs.getInt("Punteggio"));
			this.setData_consegna(rs.getDate("Data_consegna").toLocalDate());
			DaoTask taskDB=new DaoTask(rs.getInt("TASK_idTask"));
			this.setTask(taskDB);
			DaoStudente studenteDB=new DaoStudente(rs.getString("STUDENTE_emailStudente"));
			this.setStudente(studenteDB);
		}
	}
	
	public int salvaInDB() throws ClassNotFoundException, SQLException {
		int ret=0;
		String query = "INSERT INTO Soluzioni (Contenuto, Data_consegna, TASK_idTask, STUDENTE_emailStudente) " +
	               "VALUES (?, ?, ?, ?)";
		//System.out.println(query);
		ret=DBConnectionManager.updateQuerySolution(query,this.contenuto,this.data_consegna,this.task.getIdTask(),this.studente.getEmail());
		return ret;
	}
	
	public int assegnaPunteggio(int punteggio) throws ClassNotFoundException, SQLException {
		int ret=0;
		String query = "UPDATE Soluzioni SET Punteggio='"+punteggio+"'WHERE Id_soluzione='"+this.idSoluzione+"';";
		System.out.println(query);
		ret=DBConnectionManager.updateQuery(query);
		return ret;
	}

	public int getIdSoluzione() {
		return idSoluzione;
	}

	public void setIdSoluzione(int idSoluzione) {
		this.idSoluzione = idSoluzione;
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

	public void setContenuto(byte[] contenuto) {
		this.contenuto = contenuto;
	}

	public DaoTask getTask() {
		return task;
	}

	public void setTask(DaoTask task) {
		this.task = task;
	}

	public byte[] getContenuto() {
		return contenuto;
	}

	public DaoStudente getStudente() {
		return studente;
	}

	public void setStudente(DaoStudente studente) {
		this.studente = studente;
	}
	
	
	
}
