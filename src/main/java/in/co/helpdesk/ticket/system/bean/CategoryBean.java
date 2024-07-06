package in.co.helpdesk.ticket.system.bean;

import lombok.Getter;
import lombok.Setter;

@Getter 
@Setter
public class CategoryBean extends BaseBean {
	
	private String name;
	private String description;
	
	@Override
	public String getKey() {
		return String.valueOf(id);
	}

	@Override
	public String getValue() {
		return name;
	}

	

}
