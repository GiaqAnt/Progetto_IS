package eccezioni;


@SuppressWarnings("serial")
public class ConsegnaInRitardoException extends Exception {

	public ConsegnaInRitardoException() {
	}

	public ConsegnaInRitardoException(String message) {
		super(message);
	}

	public ConsegnaInRitardoException(Throwable cause) {
		super(cause);
	}

	public ConsegnaInRitardoException(String message, Throwable cause) {
		super(message, cause);
	}

	public ConsegnaInRitardoException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	@Override
	public String toString() {
		return this.getMessage();
	}

	
}
