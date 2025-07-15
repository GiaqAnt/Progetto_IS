package dto;

import java.time.LocalDate;

public class SoluzioneDTO {
	private int idSoluzione;
	private byte[] contenuto;
	private int punteggio;
	private LocalDate data_consegna;
	private String emailStudente;
	private int punteggioMax;
	
	
	
	
	public SoluzioneDTO(int idSoluzione, byte[] contenuto, int punteggio, LocalDate data_consegna, String emailStudente,int punteggioMax) {
		this.idSoluzione = idSoluzione;
		this.contenuto=contenuto;
		this.punteggio = punteggio;
		this.data_consegna = data_consegna;
		this.emailStudente=emailStudente;
		this.punteggioMax=punteggioMax;
	}
	
	public int getIdSoluzione() {
		return idSoluzione;
	}
	public void setIdSoluzione(int idSoluzione) {
		this.idSoluzione = idSoluzione;
	}
	public byte[] getContenuto() {
		return contenuto;
	}
	public void setContenuto(byte[] contenuto) {
		this.contenuto = contenuto;
	}
	public int getPunteggio() {
		return punteggio;
	}
	public void setPunteggio(int punteggio) {
		this.punteggio = punteggio;
	}
	public LocalDate getData_consegna() {
		return data_consegna;
	}
	public void setData_consegna(LocalDate data_consegna) {
		this.data_consegna = data_consegna;
	}

	@Override
	public String toString() {
		return "idSoluzione=" + idSoluzione + ", emailStudente="
				+ emailStudente + ", data_consegna=" + data_consegna+",punteggio massimo="+punteggioMax;
	}
	
	
}
