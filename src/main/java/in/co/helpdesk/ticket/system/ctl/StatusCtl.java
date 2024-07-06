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
import in.co.helpdesk.ticket.system.exception.ApplicationException;
import in.co.helpdesk.ticket.system.exception.DuplicateRecordException;
import in.co.helpdesk.ticket.system.model.TicketModel;
import in.co.helpdesk.ticket.system.util.DataUtility;
import in.co.helpdesk.ticket.system.util.PropertyReader;
import in.co.helpdesk.ticket.system.util.ServletUtility;



/**
 * Servlet implementation class TicketCtl
 */
@WebServlet(name = "StatusCtl", urlPatterns = { "/ctl/status" })
public class StatusCtl extends BaseCtl {
	private static final long serialVersionUID = 1L;

	private static Logger log = Logger.getLogger(StatusCtl.class);

	@Override
	protected boolean validate(HttpServletRequest request) {
		log.debug("TicketCtl validate method start");
		boolean pass = true;

		if ("-----Select-----".equalsIgnoreCase(request.getParameter("status"))) {
			request.setAttribute("status", PropertyReader.getValue("error.require", "Status"));
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
		bean.setStatus(DataUtility.getString(request.getParameter("status")));
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
		long tId = DataUtility.getLong(request.getParameter("tId"));

		if(tId>0) {
			request.getSession().setAttribute("tId",tId);
		}else {
			ServletUtility.redirect(HTSView.TICKET_LIST_CTL, request, response);
			return;
		}
		ServletUtility.setBean(new TicketBean(), request);

		ServletUtility.forward(getView(), request, response);
		log.debug("TicketCtl doGet method end");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		log.debug("TicketCtl doPost method start");
		String op = DataUtility.getString(request.getParameter("operation"));
		TicketModel model = new TicketModel();
		long id = DataUtility.getLong(request.getParameter("id"));

		HttpSession session=request.getSession();
		
		if (OP_SAVE.equalsIgnoreCase(op)) {

			TicketBean bean = (TicketBean) populateBean(request);

			try {
					long tId=DataUtility.getLong(String.valueOf(session.getAttribute("tId")));
					TicketBean tBean=model.findByPK(tId);
					tBean.setStatus(bean.getStatus());
					model.update(tBean);
					ServletUtility.setSuccessMessage("Data is successfully Update", request);
					ServletUtility.forward(getView(), request, response);
				

			} catch (ApplicationException e) {
				e.printStackTrace();
				ServletUtility.forward(HTSView.ERROR_VIEW, request, response);
				return;

			} catch (DuplicateRecordException e) {
				ServletUtility.setBean(bean, request);
				ServletUtility.setErrorMessage(e.getMessage(), request);
			}

		}  else if (OP_RESET.equalsIgnoreCase(op)) {
			ServletUtility.redirect(HTSView.STATUS_CTL, request, response);
			return;
		}
		ServletUtility.forward(getView(), request, response);
		log.debug("Ticket doPost method end");
	}

	@Override
	protected String getView() {
		return HTSView.STATUS_VIEW;
	}

}
