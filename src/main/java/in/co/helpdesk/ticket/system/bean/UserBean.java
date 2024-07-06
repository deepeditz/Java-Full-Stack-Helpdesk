package in.co.helpdesk.ticket.system.bean;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserBean extends BaseBean {

	private String name;
	private String userName;
	private String password;
	private String confirmPassword;
	private String email;
	private String contactNo;
	private String gender;
	private Date dob;
	private long roleId;
	private String roleName;
	

	@Override
	public String getKey() {
		return String.valueOf(id);
	}

	@Override
	public String getValue() {
		return name;
	}

	
	
}
