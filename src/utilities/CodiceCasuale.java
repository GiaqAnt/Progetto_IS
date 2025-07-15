package utilities;

import java.util.Random;

public class CodiceCasuale {
	@SuppressWarnings("unused")
	public static String generaCodiceCasuale() {
		final String CARATTERI="ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
		final int LUNGHEZZA=10;
		
		Random random=new Random();
		StringBuilder codice=new StringBuilder(LUNGHEZZA);
		
		for(int i=0;i<LUNGHEZZA;i++) {
			int index=random.nextInt(CARATTERI.length());
			codice.append(CARATTERI.charAt(index));
		}
		return codice.toString();
	}
}
