package utilities;

import java.util.Comparator;

import entity.Studente;

public class ComparatorStudentiPerMedia implements Comparator<Studente> {

	@Override
	public int compare(Studente o1, Studente o2) {
		return(int) (o1.calcolaMediaPunti()-o2.calcolaMediaPunti());
	}
	
}
