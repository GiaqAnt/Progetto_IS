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
	private LocalDate data_pubblicazione;
	private LocalDate data_scadenza;
	private int punteggioMax;
	private ArrayList<Soluzione> soluzioni;
	
	
	public Task(String titolo, String descrizione,LocalDate data_pubblicazione, LocalDate data_scadenza, int punteggioMax) {
		this.titolo = titolo;
		this.descrizione = descrizione;
		this.data_pubblicazione=data_pubblicazione;
		this.data_scadenza = data_scadenza;
		this.punteggioMax = punteggioMax;
		this.soluzioni=new ArrayList<>();
	}
	
	public Task(DaoTask taskDB) throws ClassNotFoundException, SQLException {
		this.idTask=taskDB.getIdTask();
		this.titolo=taskDB.getTitolo();
		this.descrizione=taskDB.getDescrizione();
		this.data_pubblicazione=taskDB.getData_pubblicazione();
		this.data_scadenza=taskDB.getData_scadenza();
		this.punteggioMax=taskDB.getPunteggioMax();
		this.soluzioni=new ArrayList<>();
	}
	
	public Task(int idTask) throws ClassNotFoundException, SQLException {
		DaoTask taskDB=new DaoTask(idTask);
		this.idTask=taskDB.getIdTask();
		this.titolo=taskDB.getTitolo();
		this.descrizione=taskDB.getDescrizione();
		this.data_pubblicazione=taskDB.getData_pubblicazione();
		this.data_scadenza=taskDB.getData_scadenza();
		this.punteggioMax=taskDB.getPunteggioMax();
		this.soluzioni=new ArrayList<>();
		
	}
	
	public void SalvaInDB(String codiceClasse) throws ClassNotFoundException, SQLException {
		DaoTask taskDB=new DaoTask();
		taskDB.setTitolo(titolo);
		taskDB.setDescrizione(descrizione);
		taskDB.setData_pubblicazione(data_pubblicazione);
		taskDB.setData_scadenza(data_scadenza);
		taskDB.setPunteggioMax(punteggioMax);
		taskDB.salvaInDB(codiceClasse);
	}
	
	public void caricaSoluzioniDaDB() throws ClassNotFoundException, SQLException{
		if(soluzioni.isEmpty()) {
			DaoTask taskDB=new DaoTask(this.idTask);
			taskDB.caricaSoluzioniDaDB();
			ArrayList<DaoSoluzione> lista_soluzioni_temp=taskDB.getSoluzioni();
			for(int i=0;i<lista_soluzioni_temp.size();i++) {
				Soluzione temp=new Soluzione(lista_soluzioni_temp.get(i));
				soluzioni.add(temp);
			}
		}
	}

	public ArrayList<SoluzioneDTO> getListaSoluzioni(){
		ArrayList<SoluzioneDTO> lista_soluzioni_dto=new ArrayList<>();
		for(int i=0;i<soluzioni.size();i++) {
			int idSoluzione=soluzioni.get(i).getIdSoluzione();
			byte[] contenuto=soluzioni.get(i).getContenuto();
			int punteggio=soluzioni.get(i).getPunteggio();
			LocalDate data_consegna=soluzioni.get(i).getData_consegna();
			if(punteggio==0) {
				SoluzioneDTO temp=new SoluzioneDTO(idSoluzione,contenuto,punteggio,data_consegna,soluzioni.get(i).getStudente().getEmail(),this.punteggioMax);
				lista_soluzioni_dto.add(temp);
			}
		}
		return lista_soluzioni_dto;
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
		return data_pubblicazione;
	}

	public void setData_pubblicazione(LocalDate data_pubblicazione) {
		this.data_pubblicazione = data_pubblicazione;
	}

	public LocalDate getData_scadenza() {
		return data_scadenza;
	}

	public void setData_scadenza(LocalDate data_scadenza) {
		this.data_scadenza = data_scadenza;
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
