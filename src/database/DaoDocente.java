package database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


public class DaoDocente {
	private String nome;
	private String cognome;
	private String email;

	
	
	public DaoDocente(String email)throws ClassNotFoundException, SQLException {
		this.email=email;
		caricaDaDB();
	}
	
	public DaoDocente() {
	}
	
	
	
	public DaoDocente(String nome, String cognome, String email) {
		this.nome = nome;
		this.cognome = cognome;
		this.email = email;
	}

	public void caricaDaDB() throws ClassNotFoundException, SQLException{
		String query="SELECT * FROM Docenti WHERE Email='"+this.email+"';";
		System.out.println(query);
		ResultSet rs=DBConnectionManager.selectQuery(query);
		if (rs.next()) {
			this.setNome(rs.getString("nome"));
			this.setCognome(rs.getString("cognome"));
			this.setEmail(rs.getString("email"));
		}
		else {
			this.email=null;
		}
	}
	
	public int salvaInDB() throws ClassNotFoundException, SQLException {
		int ret=0;
		String query = "INSERT INTO Docenti(Email,Nome,Cognome) VALUES ( \'"+this.email+"\','"+this.nome+"\','"+this.cognome+"')";
		System.out.println(query);
		ret=DBConnectionManager.updateQuery(query);
		return ret;
	}
	
	
	/*public void caricaClassiDaDB()throws ClassNotFoundException, SQLException {
		String query="SELECT * FROM Classi WHERE DOCENTE_emailDocente='"+this.email+"';";
		ResultSet rs=DBConnectionManager.selectQuery(query);
		while(rs.next()) {
			DaoClasse classeDB=new DaoClasse();
			classeDB.setCodice(rs.getString("Codice"));
			classeDB.setNome(rs.getString("Nome"));
			classeDB.setDocente(this);
			classi.add(classeDB);
		}
	}*/
	
	public ArrayList<DaoDocente> getListaDocenti() throws ClassNotFoundException, SQLException {
		ArrayList<DaoDocente> lista_docenti_temp=new ArrayList<>();
		String query="SELECT * FROM Docenti";
		System.out.println(query);
		ResultSet rs=DBConnectionManager.selectQuery(query);
		while(rs.next()) {
			DaoDocente docente_temp=new DaoDocente();
			docente_temp.setNome(rs.getString("Nome"));
			docente_temp.setCognome(rs.getString("Cognome"));
			docente_temp.setEmail(rs.getString("Email"));
			lista_docenti_temp.add(docente_temp);
		}
		return lista_docenti_temp;
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

	
	
	
	
	
}
