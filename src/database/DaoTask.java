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
	private ArrayList<DaoSoluzione> soluzioni;
	
	public DaoTask(int idTask) throws ClassNotFoundException, SQLException {
		this.idTask=idTask;
		this.soluzioni=new ArrayList<>();
		caricaDaDB();
	}
	
	public DaoTask() {
		
	}
	
	
	
	public DaoTask(int idTask, String titolo, String descrizione, LocalDate data_scadenza, LocalDate data_pubblicazione,
			int punteggioMax) {
		super();
		this.idTask = idTask;
		this.titolo = titolo;
		this.descrizione = descrizione;
		this.data_scadenza = data_scadenza;
		this.data_pubblicazione = data_pubblicazione;
		this.punteggioMax = punteggioMax;
		this.soluzioni = new ArrayList<>();
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
	
	public int salvaInDB(String codice_classe) throws ClassNotFoundException, SQLException {
		int ret=0;
		String query = "INSERT INTO Task(Titolo,Descrizione,Data_pubblicazione,Data_scadenza,Punteggio_massimo,CLASSE_codiceClasse) VALUES ( \'"+this.titolo+"\','"+this.descrizione+"\','"+Date.valueOf(this.data_pubblicazione)+"\','"+Date.valueOf(this.data_scadenza)+"\','"+this.punteggioMax+"\','"+codice_classe+"')";
		System.out.println(query);
		ret=DBConnectionManager.updateQueryReturnGeneratedKey(query);
		return ret;
	}
	
	public void caricaSoluzioniDaDB() throws ClassNotFoundException, SQLException {
		String query="SELECT * FROM Soluzioni WHERE TASK_idTask='"+this.idTask+"';";
		System.out.println(query);
		ResultSet rs=DBConnectionManager.selectQuery(query);
		while (rs.next()) {
			DaoSoluzione soluzioneDB=new DaoSoluzione();
			soluzioneDB.setIdSoluzione(rs.getInt("Id_soluzione"));
			soluzioneDB.setContenuto(rs.getBytes("Contenuto"));
			soluzioneDB.setData_consegna(rs.getDate("Data_consegna").toLocalDate());
			soluzioneDB.setPunteggio(rs.getInt("Punteggio"));
			soluzioneDB.setTask(this);
			DaoStudente studenteDB=new DaoStudente(rs.getString("STUDENTE_emailStudente"));
			soluzioneDB.setStudente(studenteDB);
			soluzioni.add(soluzioneDB);
		}
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

	public ArrayList<DaoSoluzione> getSoluzioni() {
		return soluzioni;
	}

	public void setSoluzioni(ArrayList<DaoSoluzione> soluzioni) {
		this.soluzioni = soluzioni;
	}

	public int getIdTask() {
		return idTask;
	}

	public void setIdTask(int idTask) {
		this.idTask = idTask;
	}



	
	
	
}
