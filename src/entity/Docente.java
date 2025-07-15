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
	
	public Docente(String nome, String cognome, String email) {
		super(nome, cognome, email,"Docente");
		this.classi=new ArrayList<>();
	}
	
	public void salvaInDB() throws ClassNotFoundException, SQLException {
		DaoDocente docenteDB=new DaoDocente();
		docenteDB.setNome(this.getNome());
		docenteDB.setCognome(this.getCognome());
		docenteDB.setEmail(this.getEmail());
		docenteDB.salvaInDB(this.getEmail());
	}

	public void creaClasse(String nome, String codice) throws ClassNotFoundException, SQLException {
		Classe classe=new Classe(nome,codice,this);
		classe.salvaInDB();
	}
	
	
	public ArrayList<ClasseDTO> getListaClassiDTO() throws ClassNotFoundException, SQLException{
		ArrayList<ClasseDTO> lista_dto=new ArrayList<>();
		for (int i=0;i<classi.size();i++) {
			String nome=classi.get(i).getNome();
			String codice=classi.get(i).getCodice();
			ClasseDTO temp=new ClasseDTO(nome,codice);
			lista_dto.add(temp);
		}
		return lista_dto;
	}
	
	public void caricaClassiDaDB() throws ClassNotFoundException, SQLException{
		if (classi.isEmpty()) {
			DaoDocente docenteDB= new DaoDocente(this.getEmail());
			docenteDB.caricaClassiDaDB();
			ArrayList<DaoClasse> lista_classi_temp=docenteDB.getListClassi();
			for(int i=0;i<lista_classi_temp.size();i++) {
				Classe classe_temp=new Classe(lista_classi_temp.get(i));
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
