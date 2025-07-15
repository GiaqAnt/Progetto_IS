package eccezioni;

@SuppressWarnings("serial")
public class UtenteGiaRegistratoException extends Exception {

	public UtenteGiaRegistratoException() {
		super();
	}

	public UtenteGiaRegistratoException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public UtenteGiaRegistratoException(String message, Throwable cause) {
		super(message, cause);
	}

	public UtenteGiaRegistratoException(String message) {
		super(message);
	}

	public UtenteGiaRegistratoException(Throwable cause) {
		super(cause);
	}

	@Override
	public String toString() {
		return this.getMessage();
	}
}
