package entity;

import database.*;

import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.sql.SQLException;
import dto.ClasseDTO;

public class Docente extends Utente {
	private ArrayList<Classe> classi;

	public Docente(DaoDocente docenteDB) throws ClassNotFoundException, SQLException {
		super(docenteDB);
		this.classi=new ArrayList<>();
	}

	public Docente(String email) throws ClassNotFoundException, SQLException {
		super(email,"Docente");
		this.classi=new ArrayList<>();
	}
	
	public Docente(String nome, String cognome, String email, String password) {
		super(nome, cognome, email,password,"Docente");
		this.classi=new ArrayList<>();
	}
	
	public void salvaInDB() throws ClassNotFoundException, SQLException {
		DaoDocente docenteDB=new DaoDocente(this.nome,this.cognome,this.email,this.password);
		docenteDB.salvaInDB();
	}

	public void creaClasse(String nome, String codice) throws ClassNotFoundException, SQLException {
		Classe classe=new Classe(nome,codice,this);
		classe.salvaInDB();
	}
	
	
	public ArrayList<ClasseDTO> getListaClassiDTO() throws ClassNotFoundException, SQLException{
		ArrayList<ClasseDTO> lista_dto=new ArrayList<>();
		for (Classe c: classi) {
			ClasseDTO temp=new ClasseDTO(c.getNome(),c.getCodice());
			lista_dto.add(temp);
		}
		return lista_dto;
	}
	
	public void caricaClassiDaDB() throws ClassNotFoundException, SQLException{
		if (classi.isEmpty()) {
			DaoClasse classeDB=new DaoClasse();
			ArrayList<DaoClasse> lista_classi_temp=classeDB.getListaClassiDocente(this.email);
			for(DaoClasse c: lista_classi_temp) {
				Classe classe_temp=new Classe(c,this);
				classi.add(classe_temp);
			}
		}
	}
	
	public Classe getClasse(String codice) throws ClassNotFoundException, SQLException {
		boolean trovato=false;
		int i=0;
		while(i<classi.size() && !trovato) {
			if(classi.get(i).getCodice().equals(codice)) {
					trovato=true;
				}
			else 
				i++;	
		}
		if(trovato)
			return classi.get(i);
		else
			throw new NoSuchElementException();
		
	}



	public ArrayList<Classe> getClassi() {
		return classi;
	}



	public void setClassi(ArrayList<Classe> classi) {
		this.classi = classi;
	}
	
	
	
}
