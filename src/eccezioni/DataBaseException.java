package eccezioni;

@SuppressWarnings("serial")
public class DataBaseException extends Exception {

	public DataBaseException() {
		super();
	}

	public DataBaseException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public DataBaseException(String message, Throwable cause) {
		super(message, cause);
	}

	public DataBaseException(String message) {
		super(message);
	}

	public DataBaseException(Throwable cause) {
		super(cause);
	}
	
	@Override
	public String toString() {
		return this.getMessage();
	}
	
}
