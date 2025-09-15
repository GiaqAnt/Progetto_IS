package database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


public class DaoStudente {
	private String nome;
	private String cognome;
	private String email;
	private String password;
	public DaoStudente() {
	}
	
	public DaoStudente(String email) throws ClassNotFoundException, SQLException {
		this.email=email;
		caricaDaDB();
	}
	
	
	
	public DaoStudente(String nome, String cognome, String email, String password) {
		this.nome = nome;
		this.cognome = cognome;
		this.email = email;
		this.password=password;
	}

	public void caricaDaDB() throws ClassNotFoundException, SQLException {
		String query="SELECT * FROM Studenti WHERE Email='"+this.email+"';";
		System.out.println(query);
		ResultSet rs=DBConnectionManager.selectQuery(query);
		if (rs.next()) {
			this.setNome(rs.getString("nome"));
			this.setCognome(rs.getString("cognome"));
			this.setEmail(rs.getString("Email"));
			this.setPassword(rs.getString("Password"));
		}
		else {
			this.email=null;
		}
	}
	
	public int salvaInDB() throws ClassNotFoundException, SQLException {
		int ret=0;
		String query = "INSERT INTO Studenti(Email,Nome,Cognome,Password) VALUES ( \'"+this.email+"\','"+this.nome+"\','"+this.cognome+"\','"+this.password+"')";
		System.out.println(query);
		ret=DBConnectionManager.updateQuery(query);
		return ret;
	}
	
	
	public ArrayList<DaoStudente> getListaStudentiClasse(String codice_classe) throws ClassNotFoundException, SQLException{
		ArrayList<DaoStudente> listaStudenti=new ArrayList<>();
		String query="SELECT * FROM Studenti WHERE CLASSE_codiceClasse='"+codice_classe+"';";
		System.out.println(query);
		ResultSet rs=DBConnectionManager.selectQuery(query);
		while(rs.next()) {
			String nome=rs.getString("Nome");
			String cognome=rs.getString("Cognome");
			String email=rs.getString("Email");
			String password=rs.getString("Password");
			DaoStudente temp=new DaoStudente(nome,cognome,email,password);
			listaStudenti.add(temp);
		}
		return listaStudenti;
	}
	
	public String getCodiceClasse() throws ClassNotFoundException, SQLException {
		String codice=null;
		String query="SELECT * FROM Studenti WHERE Email='"+this.email+"';";
		System.out.println(query);
		ResultSet rs=DBConnectionManager.selectQuery(query);
		if(rs.next()) {
			codice=rs.getString("CLASSE_codiceClasse");
		}
		return codice;
	}
	
	public int updateDaoStudente(String emailStudente, String codiceClasse) throws ClassNotFoundException, SQLException {
		int ret=0;
		String query="UPDATE Studenti SET CLASSE_codiceClasse='"+codiceClasse+"'WHERE Email='"+emailStudente+"';";
		System.out.println(query);
		ret=DBConnectionManager.updateQuery(query);
		return ret;
	}
	
	public ArrayList<DaoStudente> getListaStudenti() throws ClassNotFoundException, SQLException{
		ArrayList<DaoStudente> listaStudenti=new ArrayList<>();
		String query="SELECT * FROM Studenti";
		System.out.println(query);
		ResultSet rs=DBConnectionManager.selectQuery(query);
		while(rs.next()) {
			DaoStudente studente_temp=new DaoStudente(rs.getString("Nome"),rs.getString("Cognome"),rs.getString("Email"),rs.getString("Password"));
			listaStudenti.add(studente_temp);
		}
		return listaStudenti;
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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
}
