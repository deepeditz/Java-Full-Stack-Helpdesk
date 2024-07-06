package in.co.helpdesk.ticket.system.exception;

/**
 * DuplicateRecordException thrown when a duplicate record occurred
 */

public class DuplicateRecordException extends Exception {

	public DuplicateRecordException(String msg) {
		super(msg);
	}
}
