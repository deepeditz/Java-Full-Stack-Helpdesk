package in.co.helpdesk.ticket.system.ctl;

public interface HTSView {

	public String APP_CONTEXT = "/HelpdeskTicketSystem";

	public String PAGE_FOLDER = "/jsp";

	public String ERROR_VIEW = PAGE_FOLDER + "/Error.jsp";

	public String USER_VIEW = PAGE_FOLDER + "/UserView.jsp";
	public String USER_LIST_VIEW = PAGE_FOLDER + "/userList.jsp";
	public String USER_REGISTRATION_VIEW = PAGE_FOLDER + "/userRegistration.jsp";

	public String LOGIN_VIEW = PAGE_FOLDER + "/login.jsp";
	
	public String CATEGORY_VIEW = PAGE_FOLDER + "/category.jsp";
	public String CATEGORY_LIST_VIEW = PAGE_FOLDER + "/categoryList.jsp";
	
	public String TICKET_VIEW = PAGE_FOLDER + "/ticket.jsp";
	public String TICKET_LIST_VIEW = PAGE_FOLDER + "/ticketList.jsp";
	
	public String COMMENT_VIEW = PAGE_FOLDER + "/comment.jsp";
	public String COMMENT_LIST_VIEW = PAGE_FOLDER + "/commentList.jsp";
	
	public String STATUS_VIEW = PAGE_FOLDER + "/status.jsp";

	public String WELCOME_VIEW = PAGE_FOLDER + "/welcome.jsp";

	public String CHANGE_PASSWORD_VIEW = PAGE_FOLDER + "/changePassword.jsp";

	public String MY_PROFILE_VIEW = PAGE_FOLDER + "/myProfile.jsp";

	public String FORGET_PASSWORD_VIEW = PAGE_FOLDER + "/forgetPassword.jsp";

	public String ERROR_CTL = "/ctl/ErrorCtl";

	public String USER_CTL = APP_CONTEXT + "/ctl/user";
	public String USER_LIST_CTL = APP_CONTEXT + "/ctl/userList";
	
	public String TICKET_CTL = APP_CONTEXT + "/ctl/ticket";
	public String TICKET_LIST_CTL = APP_CONTEXT + "/ctl/ticketList";
	
	public String CATEGORY_CTL = APP_CONTEXT + "/ctl/category";
	public String CATEGORY_LIST_CTL = APP_CONTEXT + "/ctl/categoryList";
	
	public String COMMENT_CTL = APP_CONTEXT + "/ctl/comment";
	public String COMMENT_LIST_CTL = APP_CONTEXT + "/ctl/commentList";
	
	
	public String STATUS_CTL = APP_CONTEXT + "/ctl/status";
	

	public String USER_REGISTRATION_CTL = APP_CONTEXT + "/registration";
	public String LOGIN_CTL = APP_CONTEXT + "/login";
	public String WELCOME_CTL = APP_CONTEXT + "/welcome";
	public String LOGOUT_CTL = APP_CONTEXT + "/LoginCtl";

	public String CHANGE_PASSWORD_CTL = APP_CONTEXT + "/ctl/changePassword";

	public String MY_PROFILE_CTL = APP_CONTEXT + "/ctl/myProfile";

	public String FORGET_PASSWORD_CTL = APP_CONTEXT + "/forgetPassword";

}
