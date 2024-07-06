package in.co.helpdesk.ticket.system.ctl;

import java.io.IOException;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import in.co.helpdesk.ticket.system.bean.BaseBean;
import in.co.helpdesk.ticket.system.bean.UserBean;
import in.co.helpdesk.ticket.system.exception.ApplicationException;
import in.co.helpdesk.ticket.system.model.UserModel;
import in.co.helpdesk.ticket.system.util.DataUtility;
import in.co.helpdesk.ticket.system.util.DataValidator;
import in.co.helpdesk.ticket.system.util.PropertyReader;
import in.co.helpdesk.ticket.system.util.ServletUtility;





/**
 * Servlet implementation class LoginCtl
 */
/**
 * Login functionality Controller. Performs operation for Authentication, and
 * Session Creation and Give permission to access all Functionality regarding
 * Role
 */
@WebServlet(name = "LoginCtl", urlPatterns = { "/login" })
public class LoginCtl extends BaseCtl {

	private static final long serialVersionUID = 1L;

	public static final String OP_REGISTER = "Register";
	public static final String OP_SIGN_IN = "Login";
	public static final String OP_SIGN_UP = "Logout";
	public static final String OP_LOG_OUT = "logout";
	public static String HIT_URI = null;

	private static Logger log = Logger.getLogger(LoginCtl.class.getName());

	public LoginCtl() {
		super();
	}

	@Override
	protected boolean validate(HttpServletRequest request) {
		log.info("LoginCtl Method validate Started");
		boolean pass = true;
		String op = request.getParameter("operation");
		if (OP_SIGN_UP.equals(op) || OP_LOG_OUT.equals(op)) {
			return pass;
		}
		if (DataValidator.isNull(request.getParameter("userName"))) {
			request.setAttribute("userName", PropertyReader.getValue("error.require", "UserName"));
			pass = false;

		}
		if (DataValidator.isNull(request.getParameter("password"))) {
			request.setAttribute("password", PropertyReader.getValue("error.require", "Password"));
			pass = false;
		}
		log.info("LoginCtl Method validate Ended");
		return pass;
	}

	@Override
	protected BaseBean populateBean(HttpServletRequest request) {

		log.info("LoginCtl Method populateBean Started");

		UserBean bean = new UserBean();

		bean.setId(DataUtility.getLong(request.getParameter("id")));

		bean.setUserName(DataUtility.getString(request.getParameter("userName")));

		bean.setPassword(DataUtility.getString(request.getParameter("password")));

		log.info("LOginCtl Method PopulatedBean End");

		return bean;
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		log.info("Method doGet Started");

		HttpSession session = request.getSession(true);
		String op = DataUtility.getString(request.getParameter("operation"));
		if (OP_LOG_OUT.equals(op)) {
			session = request.getSession(false);
			session.invalidate();
			ServletUtility.setSuccessMessage("You have been logged out successfully", request);
			ServletUtility.forward(HTSView.LOGIN_VIEW, request, response);
			return;
		}
		if (session.getAttribute("user") != null) {
			ServletUtility.redirect(HTSView.WELCOME_CTL, request, response);
			return;
		}
		ServletUtility.forward(getView(), request, response);
		log.info("Method doGet end");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		log.info(" LoginCtl Method doPost Started");
		HttpSession session = request.getSession(true);
		String op = DataUtility.getString(request.getParameter("operation"));
		UserModel model = new UserModel();

		if (OP_SIGN_IN.equalsIgnoreCase(op)) {
			UserBean bean = (UserBean) populateBean(request);
			try {
				bean = model.authenticate(bean.getUserName(), bean.getPassword());
				if (bean != null) {
					session.setAttribute("user", bean);
					ServletUtility.redirect(HTSView.WELCOME_CTL, request, response);
					return;
				} else {
					bean = (UserBean) populateBean(request);
					ServletUtility.setBean(bean, request);
					ServletUtility.setErrorMessage("Invalid UserName And Password", request);
				}
			} catch (ApplicationException e) {
				ServletUtility.handleException(e, request, response);
				return;
			}
		} else if (OP_SIGN_UP.equalsIgnoreCase(op)) {
			ServletUtility.redirect(HTSView.USER_REGISTRATION_CTL, request, response);
			return;
		}

		ServletUtility.forward(getView(), request, response);
		log.info("UserCtl Method doPost Ended");
	}

	@Override
	protected String getView() {
		return HTSView.LOGIN_VIEW;
	}

}
