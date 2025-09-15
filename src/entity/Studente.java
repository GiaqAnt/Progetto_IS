package entity;

import database.DaoSoluzione;
import database.DaoStudente;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

public class Studente extends Utente {
	private Classe classe;
	private ArrayList<Soluzione> soluzioni;
	
	
	public Studente(DaoStudente studenteDB, Classe classe) throws ClassNotFoundException, SQLException {
		super(studenteDB);
		this.classe=classe;
		this.soluzioni=new ArrayList<>();
	}
	
	public Studente(DaoStudente studenteDB) throws ClassNotFoundException, SQLException {
		super(studenteDB);
		this.soluzioni=new ArrayList<>();
	}
	
	public Studente(String email) throws ClassNotFoundException, SQLException {
		super(email,"Studente");
		this.soluzioni=new ArrayList<>();
	}

	public Studente(String nome, String cognome, String email, String password) throws ClassNotFoundException, SQLException {
		super(nome, cognome, email,password,"Studente");
		this.soluzioni=new ArrayList<>();
	}
	
	public Studente(String nome, String cognome, String email, String password, Classe classe) throws ClassNotFoundException, SQLException {
		super(nome, cognome, email,password,"Studente");
		this.classe=classe;
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
		studenteDB.setPassword(this.password);
		studenteDB.salvaInDB();
	}

	public void caricaClasseDaDB() throws ClassNotFoundException, SQLException {
		DaoStudente studenteDB=new DaoStudente(this.nome,this.cognome,this.email,this.password);
		String codiceClasse=studenteDB.getCodiceClasse();
		if(codiceClasse==null)
			this.classe=null;
		else
			this.classe=new Classe(codiceClasse);
	}
	
	public void caricaSoluzioniDaDB() throws ClassNotFoundException, SQLException {
		if(soluzioni.isEmpty()) {
			DaoSoluzione soluzioneDB=new DaoSoluzione();
			ArrayList<DaoSoluzione> lista_soluzioni_temp=new ArrayList<>();
			lista_soluzioni_temp=soluzioneDB.getSoluzioniStudente(this.email);
			for(DaoSoluzione s: lista_soluzioni_temp) {
				Task task=new Task(s.getIdTask());
				Soluzione soluzione_temp=new Soluzione(task,s.getIdSoluzione(),s.getContenuto(),s.getPunteggio(),s.getData_consegna(),this);
				soluzioni.add(soluzione_temp);
			}
		}
	}

	public void consegnaSoluzione(Task task,byte[] contenuto,LocalDate dataConsegna) throws ClassNotFoundException, SQLException {
		Soluzione soluzione=new Soluzione(task,contenuto,dataConsegna,this);
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
			somma+=s.getPunteggio();
		}
		if (!soluzioni.isEmpty())
			return (float) somma/soluzioni.size();
		else
			return 0;
	}
	
	public int calcolaTaskCompletati() {
		int conta=0;
		for(Soluzione s: this.soluzioni) {
			if(s.getPunteggio()!=0) {
				conta++;
			}
		}
		return conta;
	}
	
	public int calcolaPunteggioTotale() {
		int somma=0;
		for(Soluzione s: this.soluzioni) {
			somma+=s.getPunteggio();
		}
		return somma;
	}

	public ArrayList<Soluzione> getSoluzioni() {
		return soluzioni;
	}

	public void setSoluzioni(ArrayList<Soluzione> soluzioni) {
		this.soluzioni = soluzioni;
	}
	
	
}
