package eccezioni;

@SuppressWarnings("serial")
public class GiaConsegnatoException extends Exception {

	public GiaConsegnatoException() {
	}

	public GiaConsegnatoException(String message) {
		super(message);
	}

	public GiaConsegnatoException(Throwable cause) {
		super(cause);
	}

	public GiaConsegnatoException(String message, Throwable cause) {
		super(message, cause);
	}

	public GiaConsegnatoException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
	
	@Override
	public String toString() {
		return this.getMessage();
	}
	

}
