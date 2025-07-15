package dto;


import java.time.LocalDate;

public class TaskDTO {
	private int idTask;
	private String titolo;
	private String descrizione;
	private LocalDate data_pubblicazione;
	private LocalDate data_scadenza;
	private int punteggioMax;
	
	
	
	
	public TaskDTO(int idTask,String titolo, String descrizione, LocalDate data_pubblicazione, LocalDate data_scadenza,
			int punteggioMax) {
		this.idTask=idTask;
		this.titolo = titolo;
		this.descrizione = descrizione;
		this.data_pubblicazione = data_pubblicazione;
		this.data_scadenza = data_scadenza;
		this.punteggioMax = punteggioMax;
		
		
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
	public int getIdTask() {
		return idTask;
	}
	public void setIdTask(int idTask) {
		this.idTask = idTask;
	}
	@Override
	public String toString() {
		return "idTask=" + idTask + ", titolo=" + titolo + ", descrizione=" + descrizione
				+ ", data_pubblicazione=" + data_pubblicazione + ", data_scadenza=" + data_scadenza + ", punteggioMax="
				+ punteggioMax;
	}
	
	
	
}
