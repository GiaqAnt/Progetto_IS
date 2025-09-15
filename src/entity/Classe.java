package entity;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

import database.DaoClasse;
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
	
	public Classe(DaoClasse classeDB,Docente docente) throws ClassNotFoundException, SQLException {
		this.codice=classeDB.getCodice();
		this.nome=classeDB.getNome();
		this.docente=docente;
		this.tasks=new ArrayList<>();
		this.studenti=new ArrayList<>();
	}
	
	public Classe(String codice) throws ClassNotFoundException, SQLException {
		DaoClasse classeDB=new DaoClasse(codice);
		this.docente=null;
		this.codice=classeDB.getCodice();
		this.nome=classeDB.getNome();
		this.tasks=new ArrayList<>();
		this.studenti=new ArrayList<>();
	}
	
	public void salvaInDB() throws ClassNotFoundException, SQLException {
		DaoClasse classeDB=new DaoClasse();
		classeDB.setNome(nome);
		classeDB.setCodice(codice);
		classeDB.salvaInDB(this.docente.getEmail());
	}
	
	
	public void creaTask(String titolo, String descrizione,LocalDate dataPubblicazione, LocalDate dataScadenza,int punteggioMax) throws ClassNotFoundException, SQLException {
		Task task=new Task(titolo,descrizione,dataPubblicazione,dataScadenza,punteggioMax,this);
		task.salvaInDB();
	}

	
	public void caricaTaskDaDB() throws ClassNotFoundException, SQLException {
		if(tasks.isEmpty()) {
			DaoTask taskDB=new DaoTask();
			ArrayList<DaoTask> lista_task_temp=taskDB.getListaTaskClasse(this.codice);
			for(DaoTask t:lista_task_temp) {
				Task temp=new Task(t,this);
				tasks.add(temp);
			}
		}
	}
	
	public void caricaStudentiDaDB() throws ClassNotFoundException, SQLException {
		if(studenti.isEmpty()) {
			DaoStudente studenteDB=new DaoStudente();
			ArrayList<DaoStudente> lista_studenti_temp=studenteDB.getListaStudentiClasse(this.codice);
			for(DaoStudente s: lista_studenti_temp) {
				Studente temp=new Studente(s,this);
				studenti.add(temp);
			}
		}
	}
	
	public void caricaDocenteDaDB() throws ClassNotFoundException, SQLException {
		if(docente==null) {
			DaoClasse classeDB=new DaoClasse(this.nome,this.codice);
			this.docente=new Docente(classeDB.getEmailDocente());
		}
	}
	
	public ArrayList<TaskDTO> getListaTaskDTO() throws ClassNotFoundException, SQLException{
		ArrayList<TaskDTO> lista_task_dto=new ArrayList<>();
		for(Task t: tasks) {
			TaskDTO temp=new TaskDTO(t.getIdTask(),t.getTitolo(),t.getDescrizione(),t.getData_pubblicazione(),t.getData_scadenza(),t.getPunteggioMax());
			lista_task_dto.add(temp);
		}
		return lista_task_dto;
	}
	
	public void iscrizioneDaDocente(String emailStudente) throws ClassNotFoundException, SQLException {
		DaoStudente studenteDB=new DaoStudente();
		studenteDB.updateDaoStudente(emailStudente,this.codice);
	}
	
	public ArrayList<UtenteDTO> getClassificaStudentiMedia() throws ClassNotFoundException, SQLException {
	    ArrayList<UtenteDTO> lista_studenti_dto = new ArrayList<>();
	    ComparatorStudentiPerMedia comparator = new ComparatorStudentiPerMedia();
	    this.studenti.sort(comparator);
	    for (Studente s : this.studenti) {
	    	UtenteDTO temp = new UtenteDTO(s.getNome(),s.getCognome(),s.getEmail(),"Studente",s.calcolaTaskCompletati());
		    lista_studenti_dto.add(temp);
		    System.out.println(temp.getMediaPunti());	    }  
	    return lista_studenti_dto;
	}

	
	public ArrayList<UtenteDTO> getClassificaStudentiTask() throws ClassNotFoundException, SQLException {
		ArrayList<UtenteDTO> lista_studenti_dto=new ArrayList<>();
		ComparatorStudentiPerTask comparator=new ComparatorStudentiPerTask();
		this.studenti.sort(comparator);
		for (Studente s : this.studenti) {
		    UtenteDTO temp = new UtenteDTO(s.getNome(),s.getCognome(),s.getEmail(),"Studente",s.calcolaTaskCompletati());
		    lista_studenti_dto.add(temp);
		}
		return lista_studenti_dto;
	}
	
	
	public Task getTask(int idTask) {
		Task temp=null;
		int i=0;
		boolean trovato=false;
		while(i<tasks.size() && !trovato) {
			if(tasks.get(i).getIdTask()==idTask) {
				trovato=true;
				temp=tasks.get(i);
			}
			else
				i++;
		}
		return temp;
	}
	
	public ArrayList<UtenteDTO> getListaStudentiDTO() {
		ArrayList<UtenteDTO> lista_studenti_dto=new ArrayList<>();
		for (Studente s: this.studenti) {
			UtenteDTO temp=new UtenteDTO(s.nome,s.cognome,s.email,s.ruolo);
			lista_studenti_dto.add(temp);
		}
		return lista_studenti_dto;
	}
	
	public void iscrizioneDaStudente(Studente stud) throws ClassNotFoundException, SQLException {
		DaoStudente studenteDB=new DaoStudente(stud.getNome(),stud.getCognome(),stud.getEmail(),stud.getPassword());
		studenteDB.updateDaoStudente(stud.getEmail(), this.codice);
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
