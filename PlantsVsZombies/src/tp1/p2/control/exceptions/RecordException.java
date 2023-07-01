package tp1.p2.control.exceptions;

import java.io.IOException;

public class RecordException extends IOException {
	private static final long serialVersionUID = 1L;

	public RecordException(String message) {
		super(message);
	}
}
