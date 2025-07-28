package utilities;

import java.util.Comparator;

import entity.Studente;

public class ComparatorStudentiPerTask implements Comparator<Studente> {

	@Override
	public int compare(Studente o1, Studente o2) {
		return o1.calcolaTaskCompletati()-o2.calcolaTaskCompletati();
	}
	
}
