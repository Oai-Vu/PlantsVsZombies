package tp1.p2.control.exceptions;

public class CommandParseException extends GameException {

	private static final long serialVersionUID = 1L;

	public CommandParseException() {
		super("[ERROR] Error: Command parse exception");
	}

	public CommandParseException(String message) {
		super(message);
	}

	public CommandParseException(GameException e) {
		super("[ERROR] Error: Command parse exception", e);
	}
}
