package in.co.helpdesk.ticket.system.exception;

/**
 * ApplicationException is propogated from Service classes when an business
 * logic exception occurered.
 */

public class ApplicationException  extends Exception
{
	
	public ApplicationException(String msg) {
		super(msg);
	}
}
