package tp1.p2.control.exceptions;

public class GameException extends Exception {
	public String errorMessage;
	private static final long serialVersionUID = 1L;

	public GameException(String message) {
		super(message);
		errorMessage = message;
	}

	public GameException(Throwable cause) {
		super(cause);
		errorMessage = "No error specified.";
	}

	public GameException(String message, Throwable cause) {
		super(message, cause);
		errorMessage = message;
	}

	public String getMessage() {
		return errorMessage;
	}
}
