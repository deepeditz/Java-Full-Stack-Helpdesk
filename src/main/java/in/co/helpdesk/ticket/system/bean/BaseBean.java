package in.co.helpdesk.ticket.system.bean;

import java.sql.Timestamp;

import lombok.Data;

/**
 * BaseBean JavaBean encapsulates Generic attributes
 */

@Data
public abstract class BaseBean implements DropdownListBean,Comparable<BaseBean> {
	
	
	protected long id;
	protected String createdBy;
	protected String modifiedBy;
	protected Timestamp createdDatetime;
	protected Timestamp modifiedDatetime;

	@Override
	public int compareTo(BaseBean b) {
		// TODO Auto-generated method stub
		return getValue().compareTo(b.getValue());
	}
	
	

	
	
	
}
