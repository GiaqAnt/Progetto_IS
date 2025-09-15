package database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


public class DaoClasse {
	private String nome;
	private String codice;

	
	public DaoClasse() {}
	
	public DaoClasse(String codice)throws ClassNotFoundException, SQLException  {
		this.codice=codice;
		caricaDaDB();
	}

	public DaoClasse(String nome, String codice) {
		this.nome = nome;
		this.codice = codice;
	}

	public void caricaDaDB() throws ClassNotFoundException, SQLException{
		String query="SELECT * FROM Classi WHERE Codice='"+this.codice+"';";
		System.out.println(query);
		ResultSet rs=DBConnectionManager.selectQuery(query);
		if (rs.next()) {
			this.setCodice(rs.getString("Codice"));
			this.setNome(rs.getString("Nome"));
		}
	}
	
	
	public int salvaInDB(String emailDocente) throws ClassNotFoundException, SQLException {
		int ret=0;
		String query = "INSERT INTO Classi(Codice,Nome,DOCENTE_emailDocente) VALUES ( \'"+this.codice+"\',"+"\'"+this.nome+"\','"+emailDocente+"')";
		System.out.println(query);
		ret=DBConnectionManager.updateQuery(query);
		return ret;
	}
	
	
	public ArrayList<DaoClasse> getListaClassiDaDB() throws ClassNotFoundException, SQLException{ 
		ArrayList<DaoClasse> lista_classi_temp=new ArrayList<>();
		String query="SELECT * FROM Classi;";
		ResultSet rs=DBConnectionManager.selectQuery(query);
		while(rs.next()) {
			DaoClasse classe_temp=new DaoClasse();
			classe_temp.setNome(rs.getString("Nome"));
			classe_temp.setCodice(rs.getString("Codice"));
			lista_classi_temp.add(classe_temp);
		}
		return lista_classi_temp;
	}
	
	public ArrayList<DaoClasse> getListaClassiDocente(String emailDocente) throws ClassNotFoundException, SQLException{
		ArrayList<DaoClasse> lista_classi_temp=new ArrayList<>();
		String query="SELECT * FROM Classi WHERE DOCENTE_emailDocente='"+emailDocente+"';";
		ResultSet rs=DBConnectionManager.selectQuery(query);
		while(rs.next()) {
			DaoClasse classe_temp=new DaoClasse(rs.getString("Nome"),rs.getString("Codice"));
			lista_classi_temp.add(classe_temp);
		}
		return lista_classi_temp;
	}
	
	public DaoClasse getClasseStudente(String emailStudente) throws ClassNotFoundException, SQLException {
		DaoClasse classeDB=null;
		String query="SELECT c.codice,c.nome FROM Studenti s JOIN Classi c on s.CLASSE_codiceClasse=c.Codice WHERE Email='"+emailStudente+"';";
		System.out.println(query);
		ResultSet rs=DBConnectionManager.selectQuery(query);
		if(rs.next()) {
			String codice=rs.getString("codice");
			String nome=rs.getString("nome");
			classeDB=new DaoClasse(codice,nome);
		}
		return classeDB;
	}
	
	public String getEmailDocente() throws ClassNotFoundException, SQLException {
		String email=null;
		String query="SELECT * FROM Classi WHERE Codice='"+this.codice+"';";
		System.out.println(query);
		ResultSet rs=DBConnectionManager.selectQuery(query);
		if(rs.next()) {
			email=rs.getString("DOCENTE_emailDocente");
		}
		return email;
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

		
		
		
		
}

	