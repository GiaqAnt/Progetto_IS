package eccezioni;


@SuppressWarnings("serial")
public class StudenteGiaIscrittoException extends Exception {
    public StudenteGiaIscrittoException(String emailStudente, String codiceClasse) {
        super("Lo studente con email " + emailStudente + " è già iscritto alla classe " + codiceClasse);
    }

	public StudenteGiaIscrittoException() {
		super();
	}

	public StudenteGiaIscrittoException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public StudenteGiaIscrittoException(String message, Throwable cause) {
		super(message, cause);
	}

	public StudenteGiaIscrittoException(String message) {
		super(message);
	}

	public StudenteGiaIscrittoException(Throwable cause) {
		super(cause);
	}
	
	@Override
	public String toString() {
		return this.getMessage();
	}
    
}
