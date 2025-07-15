package eccezioni;

@SuppressWarnings("serial")
public class PunteggioTroppoAltoException extends Exception {

	public PunteggioTroppoAltoException() {
		super();
	}

	public PunteggioTroppoAltoException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public PunteggioTroppoAltoException(String message, Throwable cause) {
		super(message, cause);
	}

	public PunteggioTroppoAltoException(String message) {
		super(message);
	}

	public PunteggioTroppoAltoException(Throwable cause) {
		super(cause);
	}
	
	@Override
	public String toString() {
		return this.getMessage();
	}
	
}
