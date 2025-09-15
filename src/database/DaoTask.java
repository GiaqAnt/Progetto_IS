package database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.sql.Date;


public class DaoTask {
	private int idTask;
	private String titolo;
	private String descrizione;
	private LocalDate data_scadenza;
	private LocalDate data_pubblicazione;
	private int punteggioMax;
	
	public DaoTask(int idTask) throws ClassNotFoundException, SQLException {
		this.idTask=idTask;
		caricaDaDB();
	}
	
	public DaoTask() {
		
	}
	
	public DaoTask(String titolo, String descrizione, LocalDate data_pubblicazione, LocalDate data_scadenza,
			int punteggioMax) {
		this.titolo = titolo;
		this.descrizione = descrizione;
		this.data_scadenza = data_scadenza;
		this.data_pubblicazione = data_pubblicazione;
		this.punteggioMax = punteggioMax;
	}
	
	public DaoTask(int idTask, String titolo, String descrizione, LocalDate data_pubblicazione, LocalDate data_scadenza,
			int punteggioMax) {
		this.idTask = idTask;
		this.titolo = titolo;
		this.descrizione = descrizione;
		this.data_scadenza = data_scadenza;
		this.data_pubblicazione = data_pubblicazione;
		this.punteggioMax = punteggioMax;
	}

	public void caricaDaDB() throws ClassNotFoundException, SQLException {
		String query="SELECT * FROM Task WHERE Id_task='"+this.idTask+"';";
		System.out.println(query);
		ResultSet rs=DBConnectionManager.selectQuery(query);
		if (rs.next()) {
			this.setIdTask(rs.getInt("Id_task"));
			this.setTitolo(rs.getString("Titolo"));
			this.setDescrizione(rs.getString("Descrizione"));
			this.setData_scadenza(rs.getDate("Data_scadenza").toLocalDate());
			this.setData_pubblicazione(rs.getDate("Data_pubblicazione").toLocalDate());
			this.setPunteggioMax(rs.getInt("Punteggio_massimo"));
		}
	}
	
	public int salvaInDB(String codiceClasse) throws ClassNotFoundException, SQLException {
		int ret=0;
		String query = "INSERT INTO Task(Titolo,Descrizione,Data_pubblicazione,Data_scadenza,Punteggio_massimo,CLASSE_codiceClasse) VALUES ( \'"+this.titolo+"\','"+this.descrizione+"\','"+Date.valueOf(this.data_pubblicazione)+"\','"+Date.valueOf(this.data_scadenza)+"\','"+this.punteggioMax+"\','"+codiceClasse+"')";
		System.out.println(query);
		ret=DBConnectionManager.updateQueryReturnGeneratedKey(query);
		return ret;
	}
	
	
	public ArrayList<DaoTask> getListaTaskClasse(String codiceClasse) throws ClassNotFoundException, SQLException{
		ArrayList<DaoTask> listaTask=new ArrayList<>();
		String query="SELECT * FROM Task WHERE CLASSE_codiceClasse='"+codiceClasse+"';";
		System.out.println(query);
		ResultSet rs=DBConnectionManager.selectQuery(query);
		while(rs.next()) {
			int idTask=rs.getInt("Id_task");
			String titolo=rs.getString("Titolo");
			String descrizione=rs.getString("Descrizione");
			LocalDate dataPubblicazione=rs.getDate("Data_pubblicazione").toLocalDate();
			LocalDate dataScadenza=rs.getDate("Data_scadenza").toLocalDate();
			int punteggioMax=rs.getInt("Punteggio_massimo");
			DaoTask temp=new DaoTask(idTask,titolo,descrizione,dataPubblicazione,dataScadenza,punteggioMax);
			listaTask.add(temp);
		}
		return listaTask;
	}
	
	public String getCodiceClasse() throws ClassNotFoundException, SQLException {
		String codice=null;
		String query="SELECT * FROM Task WHERE Id_task='"+this.idTask+"';";
		System.out.println(query);
		ResultSet rs=DBConnectionManager.selectQuery(query);
		if(rs.next()) {
			codice=rs.getString("CLASSE_codiceClasse");
		}
		return codice;
	}
	
	public String getTitolo() {
		return titolo;
	}

	public void setTitolo(String titolo) {
		this.titolo = titolo;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public LocalDate getData_scadenza() {
		return data_scadenza;
	}

	public void setData_scadenza(LocalDate data_scadenza) {
		this.data_scadenza = data_scadenza;
	}

	public int getPunteggioMax() {
		return punteggioMax;
	}

	public void setPunteggioMax(int punteggioMax) {
		this.punteggioMax = punteggioMax;
	}

	public LocalDate getData_pubblicazione() {
		return data_pubblicazione;
	}

	public void setData_pubblicazione(LocalDate data_pubblicazione) {
		this.data_pubblicazione = data_pubblicazione;
	}


	public int getIdTask() {
		return idTask;
	}

	public void setIdTask(int idTask) {
		this.idTask = idTask;
	}



	
	
	
}
