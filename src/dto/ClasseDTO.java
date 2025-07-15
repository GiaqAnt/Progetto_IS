package dto;

public class ClasseDTO {
	private String nome;
	private String codice;
	
	
	public ClasseDTO(String campo1, String campo2) {
		this.nome = campo1;
		this.codice = campo2;
	}


	public String getNome() {
		return nome;
	}


	public void setNome(String campo1) {
		this.nome = campo1;
	}


	public String getCodice() {
		return codice;
	}


	public void setCodice(String campo2) {
		this.codice = campo2;
	}


	@Override
	public String toString() {
		return "nome=" + nome + ", codice" + codice;
	}
	
	
}
