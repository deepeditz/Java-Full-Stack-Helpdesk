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
import in.co.helpdesk.ticket.system.bean.CommentBean;
import in.co.helpdesk.ticket.system.bean.UserBean;
import in.co.helpdesk.ticket.system.exception.ApplicationException;
import in.co.helpdesk.ticket.system.exception.DuplicateRecordException;
import in.co.helpdesk.ticket.system.model.CommentModel;
import in.co.helpdesk.ticket.system.util.DataUtility;
import in.co.helpdesk.ticket.system.util.DataValidator;
import in.co.helpdesk.ticket.system.util.PropertyReader;
import in.co.helpdesk.ticket.system.util.ServletUtility;



/**
 * Servlet implementation class CommentCtl
 */
@WebServlet(name = "CommentCtl", urlPatterns = { "/ctl/comment" })
public class CommentCtl extends BaseCtl {
	private static final long serialVersionUID = 1L;

	private static Logger log = Logger.getLogger(CommentCtl.class);

	@Override
	protected boolean validate(HttpServletRequest request) {
		log.debug("CommentCtl validate method start");
		boolean pass = true;

		if (DataValidator.isNull(request.getParameter("comment"))) {
			request.setAttribute("comment", PropertyReader.getValue("error.require", "Comment"));
			pass = false;
		}

		log.debug("CommentCtl validate method end");
		return pass;
	}

	@Override
	protected BaseBean populateBean(HttpServletRequest request) {
		log.debug("CommentCtl populateBean method start");
		CommentBean bean = new CommentBean();
		bean.setId(DataUtility.getLong(request.getParameter("id")));
		bean.setComment(DataUtility.getString(request.getParameter("comment")));
		populateDTO(bean, request);
		log.debug("CommentCtl populateBean method end");
		return bean;
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		log.debug("CommentCtl doGet method start");
		String op = DataUtility.getString(request.getParameter("operation"));

		CommentModel model = new CommentModel();
		long tId = DataUtility.getLong(request.getParameter("tId"));

		if(tId>0) {
			request.getSession().setAttribute("tId",tId);
		}else {
			ServletUtility.redirect(HTSView.TICKET_LIST_CTL, request, response);
			return;
		}
		ServletUtility.setBean(new CommentBean(), request);

		ServletUtility.forward(getView(), request, response);
		log.debug("CommentCtl doGet method end");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		log.debug("CommentCtl doPost method start");
		String op = DataUtility.getString(request.getParameter("operation"));
		CommentModel model = new CommentModel();
		long id = DataUtility.getLong(request.getParameter("id"));

		HttpSession session=request.getSession();
		
		if (OP_SAVE.equalsIgnoreCase(op)) {

			CommentBean bean = (CommentBean) populateBean(request);

			try {
					long tId=DataUtility.getLong(String.valueOf(session.getAttribute("tId")));
					bean.setTicketId(tId);
					UserBean uBean=(UserBean)session.getAttribute("user");
					bean.setUserName(uBean.getName());
					long pk = model.add(bean);
					ServletUtility.setSuccessMessage("Data is successfully Saved", request);
					ServletUtility.forward(getView(), request, response);
				

			} catch (ApplicationException e) {
				e.printStackTrace();
				ServletUtility.forward(HTSView.ERROR_VIEW, request, response);
				return;

			} catch (DuplicateRecordException e) {
				ServletUtility.setBean(bean, request);
				ServletUtility.setErrorMessage(e.getMessage(), request);
			}

		} else if (OP_DELETE.equalsIgnoreCase(op)) {
			CommentBean bean = (CommentBean) populateBean(request);
			try {
				model.delete(bean);
				ServletUtility.redirect(HTSView.COMMENT_CTL, request, response);
			} catch (ApplicationException e) {
				ServletUtility.handleException(e, request, response);
				e.printStackTrace();
			}
		} else if (OP_CANCEL.equalsIgnoreCase(op)) {
			ServletUtility.redirect(HTSView.COMMENT_LIST_CTL, request, response);
			return;
		} else if (OP_RESET.equalsIgnoreCase(op)) {
			ServletUtility.redirect(HTSView.COMMENT_CTL, request, response);
			return;
		}
		ServletUtility.forward(getView(), request, response);
		log.debug("Comment doPost method end");
	}

	@Override
	protected String getView() {
		return HTSView.COMMENT_VIEW;
	}

}
