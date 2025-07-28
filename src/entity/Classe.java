package entity;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;

import database.DaoClasse;
import database.DaoDocente;
import database.DaoStudente;
import database.DaoTask;
import dto.TaskDTO;
import dto.UtenteDTO;
import utilities.ComparatorStudentiPerTask;
import utilities.ComparatorStudentiPerMedia;

public class Classe {
	private String nome;
	private String codice;
	private Docente docente;
	private ArrayList<Task> tasks;
	private ArrayList<Studente> studenti;
	
	public Classe(String nome, String codice, Docente docente) {
		this.nome = nome;
		this.codice = codice;
		this.docente = docente;
		this.tasks=new ArrayList<>();
		this.studenti=new ArrayList<>();
	}
	
	public Classe(DaoClasse classeDB) throws ClassNotFoundException, SQLException {
		this.codice=classeDB.getCodice();
		this.nome=classeDB.getNome();
		//this.docente=new Docente(classeDB.getDocente());
		this.tasks=new ArrayList<>();
		this.studenti=new ArrayList<>();
	}
	
	public Classe(String codice) throws ClassNotFoundException, SQLException {
		DaoClasse classeDB=new DaoClasse(codice);
		this.codice=classeDB.getCodice();
		this.nome=classeDB.getNome();
		this.tasks=new ArrayList<>();
	}
	
	/*public void salvaInDB() throws ClassNotFoundException, SQLException {
		DaoClasse classeDB=new DaoClasse();
		classeDB.setNome(nome);
		classeDB.setCodice(codice);
		DaoDocente docenteDB=new DaoDocente(this.docente.getEmail());
		classeDB.salvaInDB();
	}*/
	
	
	public void creaTask(String titolo, String descrizione,LocalDate data_pubblicazione, LocalDate data_scadenza,int punteggioMax) throws ClassNotFoundException, SQLException {
		Task task=new Task(titolo,descrizione,data_pubblicazione,data_scadenza,punteggioMax);
		task.SalvaInDB(this.codice);
	}

	
	/*public void caricaTaskDaDB() throws ClassNotFoundException, SQLException {
		if(tasks.isEmpty()) {
			DaoClasse classeDB=new DaoClasse(this.codice);
			classeDB.caricaTaskDaDB();
			ArrayList<DaoTask> lista_task_temp=classeDB.getTasks();
			for(int i=0;i<lista_task_temp.size();i++) {
				Task temp=new Task(lista_task_temp.get(i));
				tasks.add(temp);
			}
		}
	}*/
	
	/*public void caricaStudentiDaDB() throws ClassNotFoundException, SQLException {
		if(studenti.isEmpty()) {
			DaoClasse classeDB=new DaoClasse(this.codice);
			classeDB.caricaStudentiDaDB();
			ArrayList<DaoStudente> lista_studenti_temp=classeDB.getStudenti();
			for(int i=0;i<lista_studenti_temp.size();i++) {
				Studente temp=new Studente(lista_studenti_temp.get(i));
				studenti.add(temp);
			}
		}
	}*/
	
	public ArrayList<TaskDTO> getListaTask() throws ClassNotFoundException, SQLException{
		ArrayList<TaskDTO> lista_task_dto=new ArrayList<>();
		for(int i=0;i<tasks.size();i++) {
			int idTask=tasks.get(i).getIdTask();
			String titolo=tasks.get(i).getTitolo();
			String descrizione=tasks.get(i).getDescrizione();
			LocalDate data_pubblicazione=tasks.get(i).getData_pubblicazione();
			LocalDate data_scadenza=tasks.get(i).getData_scadenza();
			int punteggioMax=tasks.get(i).getPunteggioMax();
			TaskDTO temp=new TaskDTO(idTask,titolo,descrizione,data_pubblicazione,data_scadenza,punteggioMax);
			lista_task_dto.add(temp);
		}
		return lista_task_dto;
	}
	
	public void iscrizioneDaDocente(String emailStudente) throws ClassNotFoundException, SQLException {
		DaoStudente studenteDB=new DaoStudente();
		studenteDB.updateDaoStudente(emailStudente,this.codice);
	}
	
	public ArrayList<UtenteDTO> getClassificaStudentiMedia() throws ClassNotFoundException, SQLException {
		ArrayList<UtenteDTO> lista_studenti_dto=new ArrayList<>();
		ComparatorStudentiPerMedia comparator =new ComparatorStudentiPerMedia();
		this.studenti.sort(comparator);
		for (int i=0;i<this.studenti.size();i++) {
			System.out.println(this.studenti.get(i));
			lista_studenti_dto.add(new UtenteDTO(studenti.get(i).getNome(),studenti.get(i).getCognome(),studenti.get(i).getEmail(),"Studente",studenti.get(i).calcolaMediaPunti()));
		}
		return lista_studenti_dto;
	}
	
	public ArrayList<UtenteDTO> getClassificaStudentiTask() throws ClassNotFoundException, SQLException {
		ArrayList<UtenteDTO> lista_studenti_dto=new ArrayList<>();
		ComparatorStudentiPerTask comparator=new ComparatorStudentiPerTask();
		this.studenti.sort(comparator);
		for (int i=0;i<this.studenti.size();i++) {
			lista_studenti_dto.add(new UtenteDTO(studenti.get(i).getNome(),studenti.get(i).getCognome(),studenti.get(i).getEmail(),"Studente",studenti.get(i).calcolaTaskCompletati()));
			System.out.println(lista_studenti_dto.get(i).getTaskCompletati());
		}
		return lista_studenti_dto;
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

	public Docente getD() {
		return docente;
	}

	public void setD(Docente docente) {
		this.docente = docente;
	}

	public ArrayList<Task> getTasks() {
		return tasks;
	}

	public void setTasks(ArrayList<Task> tasks) {
		this.tasks = tasks;
	}

	public Docente getDocente() {
		return docente;
	}

	public void setDocente(Docente docente) {
		this.docente = docente;
	}

	public ArrayList<Studente> getStudenti() {
		return studenti;
	}

	public void setStudenti(ArrayList<Studente> studenti) {
		this.studenti = studenti;
	}

	@Override
	public String toString() {
		return "Classe [nome=" + nome + ", codice=" + codice + ", docente=" + docente + ", tasks=" + tasks
				+ ", studenti=" + studenti + "]";
	}
	
	
	
}
