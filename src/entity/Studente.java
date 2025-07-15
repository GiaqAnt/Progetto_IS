package entity;

import database.DaoSoluzione;
import database.DaoStudente;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

public class Studente extends Utente implements Comparable<Studente> {
	private Classe classe;
	private ArrayList<Soluzione> soluzioni;
	
	
	public Studente(DaoStudente studenteDB) throws ClassNotFoundException, SQLException {
		super(studenteDB);
		if(studenteDB.getClasse()!=null)
			this.classe=new Classe(studenteDB.getClasse());
		else
			this.classe=null;
		this.soluzioni=new ArrayList<>();
	}
	
	public Studente(String email) throws ClassNotFoundException, SQLException {
		super(email,"Studente");
		DaoStudente studenteDB=new DaoStudente(email);
		if(studenteDB.getClasse()!=null)
			this.classe=new Classe(studenteDB.getClasse());
		else
			this.classe=null;
		this.soluzioni=new ArrayList<>();
	}

	public Studente(String nome, String cognome, String email) throws ClassNotFoundException, SQLException {
		super(nome, cognome, email,"Studente");
		this.soluzioni=new ArrayList<>();
	}
	
	
	
	
	@Override
	public boolean equals(Object obj) {
		Studente other=(Studente) obj;
		return(this.getEmail().equalsIgnoreCase(other.getEmail()));
	}

	public void salvaInDB() throws ClassNotFoundException, SQLException {
		DaoStudente studenteDB=new DaoStudente();
		studenteDB.setNome(this.getNome());
		studenteDB.setCognome(this.getCognome());
		studenteDB.setEmail(this.getEmail());
		studenteDB.salvaInDB(this.getEmail());
	}

	public void caricaSoluzioniDaDB() throws ClassNotFoundException, SQLException {
		if(soluzioni.isEmpty()) {
			ArrayList<DaoSoluzione> lista_soluzioni_temp=new ArrayList<>();
			DaoStudente studenteDB=new DaoStudente(this.getEmail());
			studenteDB.caricaSoluzioniDaDB();
			lista_soluzioni_temp=studenteDB.getSoluzioni();
			for(int i=0;i<lista_soluzioni_temp.size();i++) {
				Soluzione soluzione_temp=new Soluzione(lista_soluzioni_temp.get(i));
				soluzioni.add(soluzione_temp);
			}
		}
	}
	
	
	
	@Override
	public int compareTo(Studente o) {
		return (int) (this.calcolaMediaPunti()-o.calcolaMediaPunti());
	}

	public void consegnaSoluzione(int idTask,byte[] contenuto,LocalDate data_consegna) throws ClassNotFoundException, SQLException {
		Soluzione soluzione=new Soluzione(idTask,contenuto,data_consegna,this.getEmail());
		soluzione.salvaInDB();
	}
	
	public Classe getClasse() {
		return classe;
	}

	public void setClasse(Classe classe) {
		this.classe = classe;
	}

	
	public float calcolaMediaPunti() {
		int somma=0;
		for (Soluzione s: this.soluzioni) {
			System.out.println(this.getEmail()+s.getPunteggio());
			somma+=s.getPunteggio();
		}
		if (!soluzioni.isEmpty())
			return (float) somma/soluzioni.size();
		else
			return 0;
	}
	
	
}
