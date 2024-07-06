package in.co.helpdesk.ticket.system.bean;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentBean extends BaseBean {
	
	private long ticketNo;
	private long ticketId;
	private String ticketTitle;
	private String userName;
	private Date date;
	private String comment;
	

	@Override
	public String getKey() {
		return null;
	}

	@Override
	public String getValue() {
		return null;
	}

}
