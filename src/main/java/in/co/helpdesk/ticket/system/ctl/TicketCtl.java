package in.co.helpdesk.ticket.system.ctl;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import in.co.helpdesk.ticket.system.bean.BaseBean;
import in.co.helpdesk.ticket.system.bean.TicketBean;
import in.co.helpdesk.ticket.system.bean.UserBean;
import in.co.helpdesk.ticket.system.exception.ApplicationException;
import in.co.helpdesk.ticket.system.exception.DuplicateRecordException;
import in.co.helpdesk.ticket.system.model.CategoryModel;
import in.co.helpdesk.ticket.system.model.TicketModel;
import in.co.helpdesk.ticket.system.util.DataUtility;
import in.co.helpdesk.ticket.system.util.DataValidator;
import in.co.helpdesk.ticket.system.util.PropertyReader;
import in.co.helpdesk.ticket.system.util.ServletUtility;



/**
 * Servlet implementation class TicketCtl
 */
@WebServlet(name = "TicketCtl", urlPatterns = { "/ctl/ticket" })
public class TicketCtl extends BaseCtl {
	private static final long serialVersionUID = 1L;

	private static Logger log = Logger.getLogger(TicketCtl.class);
	
	
	

	@Override
	protected void preload(HttpServletRequest request) {
			
		try {
			request.setAttribute("categoryList", new CategoryModel().list());
		} catch (ApplicationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	protected boolean validate(HttpServletRequest request) {
		log.debug("TicketCtl validate method start");
		boolean pass = true;

		if (DataValidator.isNull(request.getParameter("categoryId"))) {
			request.setAttribute("categoryId", PropertyReader.getValue("error.require", "Category Name"));
			pass = false;
		}
		
		if (DataValidator.isNull(request.getParameter("title"))) {
			request.setAttribute("title", PropertyReader.getValue("error.require", "Title"));
			pass = false;
		}
		
		if (DataValidator.isNull(request.getParameter("description"))) {
			request.setAttribute("description", PropertyReader.getValue("error.require", "Decription"));
			pass = false;
		}

		log.debug("TicketCtl validate method end");
		return pass;
	}

	@Override
	protected BaseBean populateBean(HttpServletRequest request) {
		log.debug("TicketCtl populateBean method start");
		TicketBean bean = new TicketBean();
		bean.setId(DataUtility.getLong(request.getParameter("id")));
		bean.setCategoryId(DataUtility.getLong(request.getParameter("categoryId")));
		bean.setTitle(DataUtility.getString(request.getParameter("title")));
		bean.setDescription(DataUtility.getString(request.getParameter("description")));
		populateDTO(bean, request);
		log.debug("TicketCtl populateBean method end");
		return bean;
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		log.debug("TicketCtl doGet method start");
		String op = DataUtility.getString(request.getParameter("operation"));

		TicketModel model = new TicketModel();
		long id = DataUtility.getLong(request.getParameter("id"));

		ServletUtility.setOpration("Add", request);
		if (id > 0 || op != null) {
			System.out.println("in id > 0  condition");
			TicketBean bean;
			try {
				bean = model.findByPK(id);
				ServletUtility.setOpration("Edit", request);
				ServletUtility.setBean(bean, request);
			} catch (ApplicationException e) {
				ServletUtility.handleException(e, request, response);
				return;
			}
		}

		ServletUtility.forward(getView(), request, response);
		log.debug("TicketCtl doGet method end");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		log.debug("TicketCtl doPost method start");
		String op = DataUtility.getString(request.getParameter("operation"));
		TicketModel model = new TicketModel();
		HttpSession session=request.getSession();
		long id = DataUtility.getLong(request.getParameter("id"));

		if (OP_SAVE.equalsIgnoreCase(op)) {
			TicketBean bean = (TicketBean) populateBean(request);
			try {
				
				UserBean uBean=(UserBean)session.getAttribute("user");

				bean.setUserId(uBean.getId());
				bean.setUserName(uBean.getName());
				
				if (id > 0) {
					model.update(bean);
					ServletUtility.setOpration("Edit", request);
					ServletUtility.setSuccessMessage("Data is successfully Updated", request);
					ServletUtility.setBean(bean, request);
				} else {
					bean.setStatus("Pending");
					long pk = model.add(bean);
					ServletUtility.setSuccessMessage("Data is successfully Saved", request);
					ServletUtility.forward(getView(), request, response);
				}

			} catch (ApplicationException e) {
				e.printStackTrace();
				ServletUtility.forward(HTSView.ERROR_VIEW, request, response);
				return;

			} catch (DuplicateRecordException e) {
				ServletUtility.setBean(bean, request);
				ServletUtility.setErrorMessage(e.getMessage(), request);
			}

		} else if (OP_DELETE.equalsIgnoreCase(op)) {
			TicketBean bean = (TicketBean) populateBean(request);
			try {
				model.delete(bean);
				ServletUtility.redirect(HTSView.TICKET_CTL, request, response);
			} catch (ApplicationException e) {
				ServletUtility.handleException(e, request, response);
				e.printStackTrace();
			}
		} else if (OP_CANCEL.equalsIgnoreCase(op)) {
			ServletUtility.redirect(HTSView.TICKET_LIST_CTL, request, response);
			return;
		} else if (OP_RESET.equalsIgnoreCase(op)) {
			ServletUtility.redirect(HTSView.TICKET_CTL, request, response);
			return;
		}
		ServletUtility.forward(getView(), request, response);
		log.debug("Ticket doPost method end");
	}

	@Override
	protected String getView() {
		return HTSView.TICKET_VIEW;
	}

}
