package entity;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

import database.DaoSoluzione;
import database.DaoTask;
import dto.SoluzioneDTO;

public class Task {
	private int idTask;
	private String titolo;
	private String descrizione;
	private LocalDate dataPubblicazione;
	private LocalDate dataScadenza;
	private int punteggioMax;
	private ArrayList<Soluzione> soluzioni;
	private Classe classe;
	
	
	public Task(String titolo, String descrizione,LocalDate data_pubblicazione, LocalDate data_scadenza, int punteggioMax,Classe classe) {
		this.titolo = titolo;
		this.descrizione = descrizione;
		this.dataPubblicazione=data_pubblicazione;
		this.dataScadenza = data_scadenza;
		this.punteggioMax = punteggioMax;
		this.soluzioni=new ArrayList<>();
		this.classe=classe;
	}
	
	public Task(int idTask,String titolo, String descrizione,LocalDate data_pubblicazione, LocalDate data_scadenza, int punteggioMax,Classe classe) {
		this.idTask=idTask;
		this.titolo = titolo;
		this.descrizione = descrizione;
		this.dataPubblicazione=data_pubblicazione;
		this.dataScadenza = data_scadenza;
		this.punteggioMax = punteggioMax;
		this.classe=classe;
		this.soluzioni=new ArrayList<>();
	}
	
	public Task(DaoTask taskDB,Classe classe) throws ClassNotFoundException, SQLException {
		this.idTask=taskDB.getIdTask();
		this.titolo=taskDB.getTitolo();
		this.descrizione=taskDB.getDescrizione();
		this.dataPubblicazione=taskDB.getData_pubblicazione();
		this.dataScadenza=taskDB.getData_scadenza();
		this.punteggioMax=taskDB.getPunteggioMax();
		this.soluzioni=new ArrayList<>();
		this.classe=classe;
	}
	
	public Task(int idTask) throws ClassNotFoundException, SQLException {
		DaoTask taskDB=new DaoTask(idTask);
		this.idTask=taskDB.getIdTask();
		this.titolo=taskDB.getTitolo();
		this.descrizione=taskDB.getDescrizione();
		this.dataPubblicazione=taskDB.getData_pubblicazione();
		this.dataScadenza=taskDB.getData_scadenza();
		this.punteggioMax=taskDB.getPunteggioMax();
		this.soluzioni=new ArrayList<>();
		
	}
	
	public void salvaInDB() throws ClassNotFoundException, SQLException {
		DaoTask taskDB=new DaoTask(titolo,descrizione,dataPubblicazione,dataScadenza,punteggioMax);
		taskDB.salvaInDB(this.classe.getCodice());
	}
	
	public void caricaSoluzioniDaDB() throws ClassNotFoundException, SQLException{
		ArrayList<DaoSoluzione> listaSoluzioni=new ArrayList<>();
		if(soluzioni.isEmpty()) {
			DaoSoluzione soluzioneDB=new DaoSoluzione();
			listaSoluzioni=soluzioneDB.getListaSoluzioniTask(idTask);
			for(DaoSoluzione s: listaSoluzioni) {
				Studente stud=new Studente(s.getEmailStudente());
				Soluzione temp=new Soluzione(this,s.getIdSoluzione(),s.getContenuto(),s.getPunteggio(),s.getData_consegna(),stud);
				soluzioni.add(temp);
			}
		}
	}

	
	public void caricaClasseDaDB() throws ClassNotFoundException, SQLException {
		DaoTask taskDB=new DaoTask(this.idTask,this.titolo,this.descrizione,this.dataScadenza,this.dataPubblicazione,this.punteggioMax);
		this.classe=new Classe(taskDB.getCodiceClasse());
	}
	
	public ArrayList<SoluzioneDTO> getListaSoluzioniDTO(){
		ArrayList<SoluzioneDTO> lista_soluzioni_dto=new ArrayList<>();
		for(Soluzione s: soluzioni) {
			if(s.getPunteggio()==0) {
				SoluzioneDTO temp=new SoluzioneDTO(s.getIdSoluzione(),s.getContenuto(),s.getPunteggio(),s.getData_consegna(),s.getStudente().getEmail(),s.getTask().getIdTask());
				lista_soluzioni_dto.add(temp);
			}
		}
		return lista_soluzioni_dto;
	}
	
	public Soluzione getSoluzione(int idSoluzione) {
		int i=0;
		boolean trovato=false;
		Soluzione temp=null;
		while(i<soluzioni.size() && !trovato) {
			if(soluzioni.get(i).getIdSoluzione()==idSoluzione) {
				trovato=true;
				temp=soluzioni.get(i);
			}
			else
				i++;
		}
		return temp;
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

	public LocalDate getData_pubblicazione() {
		return dataPubblicazione;
	}

	public void setData_pubblicazione(LocalDate data_pubblicazione) {
		this.dataPubblicazione = data_pubblicazione;
	}

	public LocalDate getData_scadenza() {
		return dataScadenza;
	}

	public void setData_scadenza(LocalDate data_scadenza) {
		this.dataScadenza = data_scadenza;
	}

	public int getPunteggioMax() {
		return punteggioMax;
	}

	public void setPunteggioMax(int punteggioMax) {
		this.punteggioMax = punteggioMax;
	}

	public ArrayList<Soluzione> getSoluzioni() {
		return soluzioni;
	}

	public void setSoluzioni(ArrayList<Soluzione> soluzioni) {
		this.soluzioni = soluzioni;
	}

	public int getIdTask() {
		return idTask;
	}

	public void setIdTask(int idTask) {
		this.idTask = idTask;
	}

	
	
	
	
}
