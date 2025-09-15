package database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;



public class DaoSoluzione {
	private int idSoluzione;
	private byte[] contenuto;
	private int punteggio;
	private LocalDate data_consegna;
	
	public DaoSoluzione() {}
	
	public DaoSoluzione(int idSoluzione) throws ClassNotFoundException, SQLException {
		this.idSoluzione=idSoluzione;
		caricaDaDB();
	}
	
	public DaoSoluzione(byte[] contenuto, LocalDate data_consegna) throws ClassNotFoundException, SQLException {
		this.contenuto=contenuto;
		this.data_consegna=data_consegna;

	}	
	
	public DaoSoluzione(int idSoluzione, byte[] contenuto, int punteggio, LocalDate data_consegna) {
		this.idSoluzione = idSoluzione;
		this.contenuto = contenuto;
		this.punteggio = punteggio;
		this.data_consegna = data_consegna;
	}

	public void caricaDaDB() throws ClassNotFoundException, SQLException {
		String query="SELECT * FROM Soluzioni WHERE Id_soluzione='"+this.idSoluzione+"';";
		System.out.println(query);
		ResultSet rs=DBConnectionManager.selectQuery(query);
		if (rs.next()) {
			this.setContenuto(rs.getBytes("Contenuto"));
			this.setPunteggio(rs.getInt("Punteggio"));
			this.setData_consegna(rs.getDate("Data_consegna").toLocalDate());
		}
	}
	
	public int salvaInDB(int idTask,String emailStudente) throws ClassNotFoundException, SQLException {
		int ret=0;
		String query = "INSERT INTO Soluzioni (Contenuto, Data_consegna, TASK_idTask, STUDENTE_emailStudente) " +
	               "VALUES (?, ?, ?, ?)";
		System.out.println(query);
		ret=DBConnectionManager.updateQuerySolution(query,this.contenuto,this.data_consegna,idTask,emailStudente);
		return ret;
	}
	
	public int assegnaPunteggio(int punteggio) throws ClassNotFoundException, SQLException {
		int ret=0;
		String query = "UPDATE Soluzioni SET Punteggio='"+punteggio+"'WHERE Id_soluzione='"+this.idSoluzione+"';";
		System.out.println(query);
		ret=DBConnectionManager.updateQuery(query);
		return ret;
	}
	
	public int getIdTask() throws SQLException, ClassNotFoundException {
		String query="SELECT * FROM Soluzioni WHERE Id_soluzione='"+this.idSoluzione+"';";
		System.out.println(query);
		ResultSet rs=DBConnectionManager.selectQuery(query);
		if(rs.next()) {
			return rs.getInt("TASK_idTask");
		}
		else
			return 0;
	}
	
	public String getEmailStudente() throws ClassNotFoundException, SQLException {
		String email=null;
		String query="SELECT * FROM Soluzioni WHERE Id_soluzione='"+this.idSoluzione+"';";
		System.out.println(query);
		ResultSet rs=DBConnectionManager.selectQuery(query);
		if(rs.next()) {
			email=rs.getString("STUDENTE_emailStudente");
		}
		return email;
	}
	
	public ArrayList<DaoSoluzione> getListaSoluzioniTask(int idTask) throws ClassNotFoundException, SQLException{
		ArrayList<DaoSoluzione> listaSoluzioni=new ArrayList<>();
		String query="SELECT * FROM Soluzioni WHERE TASK_idTask='"+idTask+"';";
		System.out.println(query);
		ResultSet rs=DBConnectionManager.selectQuery(query);
		while(rs.next()) {
			DaoSoluzione temp=new DaoSoluzione(rs.getInt("Id_soluzione"),rs.getBytes("Contenuto"),rs.getInt("Punteggio"),rs.getDate("Data_consegna").toLocalDate());
			listaSoluzioni.add(temp);
		}
		return listaSoluzioni;
	}
	
	public ArrayList<DaoSoluzione> getSoluzioniStudente(String emailStudente) throws ClassNotFoundException, SQLException{
		ArrayList<DaoSoluzione> listaSoluzioni=new ArrayList<>();
		String query="SELECT * FROM Soluzioni WHERE STUDENTE_emailStudente='"+emailStudente+"';";
		System.out.println(query);
		ResultSet rs=DBConnectionManager.selectQuery(query);
		while(rs.next()) {
			DaoSoluzione temp=new DaoSoluzione(rs.getInt("Id_soluzione"),rs.getBytes("Contenuto"),rs.getInt("Punteggio"),rs.getDate("Data_consegna").toLocalDate());
			listaSoluzioni.add(temp);
		}
		return listaSoluzioni;
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


	public byte[] getContenuto() {
		return contenuto;
	}


	
	
	
}
