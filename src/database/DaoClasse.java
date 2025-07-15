package database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


public class DaoClasse {
	private String nome;
	private String codice;
	private DaoDocente docente;
	private ArrayList<DaoStudente> studenti;
	private ArrayList<DaoTask> tasks;
	
	public DaoClasse() {}
	
	public DaoClasse(String codice)throws ClassNotFoundException, SQLException  {
		this.codice=codice;
		this.studenti=new ArrayList<>();
		this.tasks=new ArrayList<>();
		caricaDaDB();
	}
	
	
	
	
	public DaoClasse(String nome, String codice, DaoDocente docente) {
		this.nome = nome;
		this.codice = codice;
		this.docente = docente;
		this.studenti = new ArrayList<>();
		this.tasks = new ArrayList<>();
	}

	public void caricaDaDB() throws ClassNotFoundException, SQLException{
		String query="SELECT * FROM Classi WHERE Codice='"+this.codice+"';";
		System.out.println(query);
		ResultSet rs=DBConnectionManager.selectQuery(query);
		if (rs.next()) {
			this.setNome(rs.getString("Nome"));
			DaoDocente docenteDB=new DaoDocente(rs.getString("DOCENTE_emailDocente"));
			this.setDocente(docenteDB);
		}
	}
	
	
	public int salvaInDB() throws ClassNotFoundException, SQLException {
		int ret=0;
		String query = "INSERT INTO Classi(Codice,Nome,DOCENTE_emailDocente) VALUES ( \'"+this.codice+"\',"+"\'"+this.nome+"\','"+this.docente.getEmail()+"')";
		System.out.println(query);
		ret=DBConnectionManager.updateQuery(query);
		return ret;
	}
	
	
	public ArrayList<DaoClasse> getListaClassiDaDB() throws ClassNotFoundException, SQLException{ 
		ArrayList<DaoClasse> lista_classi_temp=new ArrayList<>();
		String query="SELECT * FROM Classi;";
		ResultSet rs=DBConnectionManager.selectQuery(query);
		while(rs.next()) {
			DaoDocente docenteDB=new DaoDocente(rs.getString("DOCENTE_emailDocente"));
			DaoClasse classe_temp=new DaoClasse();
			classe_temp.setNome(rs.getString("Nome"));
			classe_temp.setCodice(rs.getString("Codice"));
			classe_temp.setDocente(docenteDB);
			lista_classi_temp.add(classe_temp);
		}
		return lista_classi_temp;
	}
	
	
	
	
	public DaoDocente getDocente() {
		return docente;
	}



	public void setDocente(DaoDocente docente) {
		this.docente = docente;
	}



	public ArrayList<DaoStudente> getStudenti() {
		return studenti;
	}



	public void setStudenti(ArrayList<DaoStudente> studenti) {
		this.studenti = studenti;
	}

	

	public void caricaTaskDaDB() throws ClassNotFoundException, SQLException {
		String query="SELECT * FROM Task WHERE CLASSE_codiceClasse='"+this.codice+"';";
		System.out.println(query);
		ResultSet rs=DBConnectionManager.selectQuery(query);
		while(rs.next()) {
			DaoTask taskDB=new DaoTask();
			taskDB.setIdTask(rs.getInt("Id_task"));
			taskDB.setTitolo(rs.getString("Titolo"));
			taskDB.setDescrizione(rs.getString("Descrizione"));
			taskDB.setData_scadenza(rs.getDate("Data_scadenza").toLocalDate());
			taskDB.setData_pubblicazione(rs.getDate("Data_pubblicazione").toLocalDate());
			taskDB.setPunteggioMax(rs.getInt("Punteggio_massimo"));
			tasks.add(taskDB);
		}
	}
	
	public void caricaStudentiDaDB() throws ClassNotFoundException, SQLException{
		String query="SELECT * FROM Studenti WHERE CLASSE_codiceClasse='"+this.codice+"';";
		System.out.println(query);
		ResultSet rs=DBConnectionManager.selectQuery(query);
		while(rs.next()) {
			DaoStudente studenteDB=new DaoStudente();
			studenteDB.setEmail(rs.getString("Email"));
			studenteDB.setNome(rs.getString("Nome"));
			studenteDB.setCognome(rs.getString("Cognome"));
			studenteDB.setClasse(this);
			studenti.add(studenteDB);
		}
	}
	
	public int iscrizioneDaDocente(String email_studente) throws ClassNotFoundException, SQLException {
		int ret=0;
		String query="UPDATE Studenti SET CLASSE_codiceClasse='"+this.codice+"'WHERE Email='"+email_studente+"';";
		System.out.println(query);
		ret=DBConnectionManager.updateQuery(query);
		return ret;
	}
	
		public String getNome() {
			return nome;
		}

		public void setNome(String nome) {
			this.nome = nome;
		}

		public String getCodice() {
			return codice;
		}

		public void setCodice(String codice) {
			this.codice = codice;
		}

		public ArrayList<DaoTask> getTasks() {
			return tasks;
		}

		public void setTasks(ArrayList<DaoTask> tasks) {
			this.tasks = tasks;
		}

		@Override
		public String toString() {
			return "DaoClasse [nome=" + nome + ", codice=" + codice + ", docente=" + docente + ", studenti=" + studenti
					+ ", tasks=" + tasks + "]";
		}		
		
		
}

	