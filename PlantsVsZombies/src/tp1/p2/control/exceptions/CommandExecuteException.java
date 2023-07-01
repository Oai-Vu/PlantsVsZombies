package tp1.p2.control.exceptions;

public class CommandExecuteException extends GameException {
	private static final long serialVersionUID = 1L;

	public CommandExecuteException() {
		super("[ERROR] Error: Command execute exception");
	}

	public CommandExecuteException(String message) {
		super(message);
	}

	public CommandExecuteException(GameException e) {
		super("[ERROR] Error: Command execute exception", e);
	}

}
