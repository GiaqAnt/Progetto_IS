package database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


public class DaoStudente {
	private String nome;
	private String cognome;
	private String email;
	private ArrayList<DaoSoluzione> soluzioni;
	private DaoClasse classe;
	public DaoStudente() {
	}
	
	public DaoStudente(String email) throws ClassNotFoundException, SQLException {
		this.email=email;
		this.soluzioni=new ArrayList<>();
		caricaDaDB();
	}
	
	
	
	public DaoStudente(String nome, String cognome, String email, DaoClasse classe) {
		this.nome = nome;
		this.cognome = cognome;
		this.email = email;
		this.soluzioni = new ArrayList<>();
		this.classe = classe;
	}

	public void caricaDaDB() throws ClassNotFoundException, SQLException {
		String query="SELECT * FROM Studenti WHERE Email='"+this.email+"';";
		System.out.println(query);
		ResultSet rs=DBConnectionManager.selectQuery(query);
		if (rs.next()) {
			this.setNome(rs.getString("nome"));
			this.setCognome(rs.getString("cognome"));
			this.setEmail(rs.getString("Email"));
			DaoClasse classeDB;
			if(rs.getString("CLASSE_codiceClasse")!=null)
				classeDB=new DaoClasse(rs.getString("CLASSE_codiceClasse"));
			else
				classeDB=null;
			this.setClasse(classeDB);
		}
		else {
			this.email=null;
		}
	}
	
	public int salvaInDB(String email) throws ClassNotFoundException, SQLException {
		int ret=0;
		String query = "INSERT INTO Studenti(Email,Nome,Cognome) VALUES ( \'"+email+"\','"+this.nome+"\','"+this.cognome+"')";
		System.out.println(query);
		ret=DBConnectionManager.updateQuery(query);
		return ret;
	}
	
	
	public void caricaSoluzioniDaDB() throws ClassNotFoundException, SQLException {
		String query="SELECT * FROM Soluzioni WHERE STUDENTE_emailStudente='"+this.email+"';";
		System.out.println(query);
		ResultSet rs=DBConnectionManager.selectQuery(query);
		while(rs.next()) {
			DaoSoluzione soluzioneDB=new DaoSoluzione();
			soluzioneDB.setIdSoluzione(rs.getInt("Id_soluzione"));
			soluzioneDB.setContenuto(rs.getBytes("Contenuto"));
			soluzioneDB.setData_consegna(rs.getDate("Data_consegna").toLocalDate());
			soluzioneDB.setPunteggio(rs.getInt("Punteggio"));
			DaoTask taskDB=new DaoTask(rs.getInt("TASK_idTask"));
			soluzioneDB.setTask(taskDB);
			soluzioneDB.setStudente(this);
			soluzioni.add(soluzioneDB);
		}
	}
	
	public ArrayList<DaoStudente> getListaStudenti() throws ClassNotFoundException, SQLException{
		ArrayList<DaoStudente> lista_studenti_temp=new ArrayList<>();
		String query="SELECT * FROM Studenti";
		System.out.println(query);
		ResultSet rs=DBConnectionManager.selectQuery(query);
		while(rs.next()) {
			DaoStudente studente_temp=new DaoStudente();
			studente_temp.setNome(rs.getString("Nome"));
			studente_temp.setCognome(rs.getString("Cognome"));
			studente_temp.setEmail(rs.getString("Email"));
			DaoClasse classeDB;
			if(rs.getString("CLASSE_codiceClasse")!=null)
				classeDB=new DaoClasse(rs.getString("CLASSE_codiceClasse"));
			else
				classeDB=null;
			studente_temp.setClasse(classeDB);
			lista_studenti_temp.add(studente_temp);
		}
		return lista_studenti_temp;		
	}
	
	public int updateDaoStudente(String emailStudente, String codiceClasse) throws ClassNotFoundException, SQLException {
		int ret=0;
		String query="UPDATE Studenti SET CLASSE_codiceClasse='"+codiceClasse+"'WHERE Email='"+emailStudente+"';";
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
	public String getCognome() {
		return cognome;
	}
	public void setCognome(String cognome) {
		this.cognome = cognome;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}

	public ArrayList<DaoSoluzione> getSoluzioni() {
		return soluzioni;
	}

	public void setSoluzioni(ArrayList<DaoSoluzione> soluzioni) {
		this.soluzioni = soluzioni;
	}

	public DaoClasse getClasse() {
		return classe;
	}

	public void setClasse(DaoClasse classe) {
		this.classe = classe;
	}
}
