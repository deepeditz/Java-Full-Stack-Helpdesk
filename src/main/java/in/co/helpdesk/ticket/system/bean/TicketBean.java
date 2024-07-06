package in.co.helpdesk.ticket.system.bean;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TicketBean extends BaseBean {

	private long categoryId;
	private String categoryName;
	private long ticketNo;
	private String title;
	private Date date;
	private String status;
	private String userName;
	private long userId;
	private String description;

	@Override
	public String getKey() {
		// TODO Auto-generated method stub
		return String.valueOf(id);
	}

	@Override
	public String getValue() {
		// TODO Auto-generated method stub
		return title;
	}

}
