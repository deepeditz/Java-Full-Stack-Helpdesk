package in.co.helpdesk.ticket.system.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import in.co.helpdesk.ticket.system.bean.CategoryBean;
import in.co.helpdesk.ticket.system.bean.TicketBean;
import in.co.helpdesk.ticket.system.exception.ApplicationException;
import in.co.helpdesk.ticket.system.exception.DatabaseException;
import in.co.helpdesk.ticket.system.exception.DuplicateRecordException;
import in.co.helpdesk.ticket.system.util.JDBCDataSource;

/**
 * JDBC Implementation of TicketModel
 */

public class TicketModel {
	private static Logger log = Logger.getLogger(TicketModel.class.getName());

	public Integer nextPK() throws DatabaseException {
		log.info("Model nextPK Started");
		Connection conn = null;
		int pk = 0;

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement("SELECT MAX(ID) FROM Ticket");
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				pk = rs.getInt(1);
			}
			rs.close();
		} catch (Exception e) {
			throw new DatabaseException("Exception : Exception in getting PK");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.info("Model nextPK End");
		return pk + 1;
	}
	
	public Long nextTicketNo() throws DatabaseException {
		log.info("Model nextPK Started");
		Connection conn = null;
		long pk = 0;

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement("SELECT MAX(TicketNo) FROM Ticket");
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				pk = rs.getLong(1);
			}
			rs.close();
		} catch (Exception e) {
			throw new DatabaseException("Exception : Exception in getting PK");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.info("Model nextPK End");
		if(pk==0) {
			return 10001001L;
		}else {
		return pk + 1;
		}
	}

	public long add(TicketBean bean) throws ApplicationException, DuplicateRecordException {

		Connection conn = null;
		int pk = 0;

		/*
		 * TicketBean existbean = findByUserName(bean.getUserName());
		 * 
		 * if (existbean != null) { throw new
		 * DuplicateRecordException("User Name is already exists"); }
		 */

			CategoryBean cBean=new CategoryModel().findByPK(bean.getCategoryId());
		
		try {
			conn = JDBCDataSource.getConnection();
			pk = nextPK();
			conn.setAutoCommit(false);
			PreparedStatement pstmt = conn
					.prepareStatement("INSERT INTO Ticket VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			pstmt.setInt(1, pk);
			pstmt.setLong(2,bean.getCategoryId());
			pstmt.setString(3, cBean.getName());
			pstmt.setLong(4,bean.getUserId());
			pstmt.setString(5, bean.getUserName());
			pstmt.setLong(6,nextTicketNo());
			pstmt.setString(7, bean.getTitle());
			pstmt.setDate(8, new java.sql.Date(new Date().getTime()));
			pstmt.setString(9, bean.getStatus());
			pstmt.setString(10, bean.getDescription());
			pstmt.setString(11, bean.getCreatedBy());
			pstmt.setString(12, bean.getModifiedBy());
			pstmt.setTimestamp(13, bean.getCreatedDatetime());
			pstmt.setTimestamp(14, bean.getModifiedDatetime());
			pstmt.executeUpdate();
			conn.commit(); // End transaction
			pstmt.close();
		} catch (Exception e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (Exception ex) {
				ex.printStackTrace();
				throw new ApplicationException("Exception : add rollback exception " + ex.getMessage());
			}
			throw new ApplicationException("Exception : Exception in add Ticket");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		return pk;
	}

	public void delete(TicketBean bean) throws ApplicationException {

		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false); // Begin transaction
			PreparedStatement pstmt = conn.prepareStatement("DELETE FROM Ticket WHERE ID=?");
			pstmt.setLong(1, bean.getId());
			pstmt.executeUpdate();
			conn.commit(); // End transaction
			pstmt.close();

		} catch (Exception e) {

			try {
				conn.rollback();
			} catch (Exception ex) {
				throw new ApplicationException("Exception : Delete rollback exception " + ex.getMessage());
			}
			throw new ApplicationException("Exception : Exception in delete Ticket");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

	}

	public TicketBean findByTitle(String title) throws ApplicationException {
		log.info("Model findByLogin Started");
		StringBuffer sql = new StringBuffer("SELECT * FROM Ticket WHERE title=?");
		TicketBean bean = null;
		Connection conn = null;
		System.out.println("sql" + sql);

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			pstmt.setString(1, title);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				bean = new TicketBean();
				bean.setId(rs.getLong(1));
				bean.setCategoryId(rs.getLong(2));
				bean.setCategoryName(rs.getString(3));
				bean.setUserId(rs.getLong(4));
				bean.setUserName(rs.getString(5));
				bean.setTicketNo(rs.getLong(6));
				bean.setTitle(rs.getString(7));
				bean.setDate(rs.getDate(8));
				bean.setStatus(rs.getString(9));
				bean.setDescription(rs.getString(10));
				bean.setCreatedBy(rs.getString(11));
				bean.setModifiedBy(rs.getString(12));
				bean.setCreatedDatetime(rs.getTimestamp(13));
				bean.setModifiedDatetime(rs.getTimestamp(14));

			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationException("Exception : Exception in getting Ticket by login");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.info("Model findByLogin End");
		return bean;
	}

	public TicketBean findByPK(long pk) throws ApplicationException {
		log.info("Model findByPK Started");
		StringBuffer sql = new StringBuffer("SELECT * FROM Ticket WHERE ID=?");
		TicketBean bean = null;
		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			pstmt.setLong(1, pk);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				bean = new TicketBean();
				bean.setId(rs.getLong(1));
				bean.setCategoryId(rs.getLong(2));
				bean.setCategoryName(rs.getString(3));
				bean.setUserId(rs.getLong(4));
				bean.setUserName(rs.getString(5));
				bean.setTicketNo(rs.getLong(6));
				bean.setTitle(rs.getString(7));
				bean.setDate(rs.getDate(8));
				bean.setStatus(rs.getString(9));
				bean.setDescription(rs.getString(10));
				bean.setCreatedBy(rs.getString(11));
				bean.setModifiedBy(rs.getString(12));
				bean.setCreatedDatetime(rs.getTimestamp(13));
				bean.setModifiedDatetime(rs.getTimestamp(14));

			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationException("Exception : Exception in getting Ticket by pk");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.info("Model findByPK End");
		return bean;
	}

	public void update(TicketBean bean) throws ApplicationException, DuplicateRecordException {
		log.info("Model update Started");
		Connection conn = null;

		/*
		 * TicketBean beanExist = findByUserName(bean.getUserName()); if (beanExist
		 * != null && !(beanExist.getId() == bean.getId())) { throw new
		 * DuplicateRecordException("UserName is already exist"); }
		 */
		CategoryBean cBean=new CategoryModel().findByPK(bean.getCategoryId());
		TicketBean tBean=findByPK(bean.getId());
		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false); // Begin transaction
			PreparedStatement pstmt = conn.prepareStatement(
					"UPDATE Ticket SET categoryId=?,categoryname=?,userId=?,userName=?,ticketNo=?,title=?,Date=?,status=?,description=?,"
							+ "CREATED_BY=?,MODIFIED_BY=?,CREATED_DATETIME=?,MODIFIED_DATETIME=? WHERE ID=?");
			pstmt.setLong(1,bean.getCategoryId());
			pstmt.setString(2, cBean.getName());
			pstmt.setLong(3,bean.getUserId());
			pstmt.setString(4, bean.getUserName());
			pstmt.setLong(5,tBean.getTicketNo());
			pstmt.setString(6, bean.getTitle());
			pstmt.setDate(7, new java.sql.Date(new Date().getTime()));
			pstmt.setString(8, bean.getStatus());
			pstmt.setString(9, bean.getDescription());
			pstmt.setString(10, bean.getCreatedBy());
			pstmt.setString(11, bean.getModifiedBy());
			pstmt.setTimestamp(12, bean.getCreatedDatetime());
			pstmt.setTimestamp(13, bean.getModifiedDatetime());
			pstmt.setLong(14, bean.getId());
			pstmt.executeUpdate();
			conn.commit(); // End transaction
			pstmt.close();
		} catch (Exception e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (Exception ex) {
				throw new ApplicationException("Exception : Delete rollback exception " + ex.getMessage());
			}
			throw new ApplicationException("Exception in updating Ticket ");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.info("Model update End");
	}

	public List<TicketBean> search(TicketBean bean) throws ApplicationException {
		return search(bean, 0, 0);
	}

	public List<TicketBean> search(TicketBean bean, int pageNo, int pageSize) throws ApplicationException {
		log.info("Model search Started");
		StringBuffer sql = new StringBuffer("SELECT * FROM Ticket WHERE 1=1");

		if (bean != null) {
			if (bean.getId() > 0) {
				sql.append(" AND id = " + bean.getId());
			}
			if (bean.getCategoryId() > 0) {
				sql.append(" AND CategoryId = " + bean.getCategoryId());
			}
			if (bean.getUserId() > 0) {
				sql.append(" AND UserId = " + bean.getUserId());
			}
			
			if (bean.getTicketNo() > 0) {
				sql.append(" AND TicketNo = " + bean.getTicketNo());
			}
			
			if (bean.getTitle() != null && bean.getTitle().length() > 0) {
				sql.append(" AND Title like '" + bean.getTitle() + "%'");
			}
			
			if (bean.getCategoryName() != null && bean.getCategoryName().length() > 0) {
				sql.append(" AND CategoryName like '" + bean.getCategoryName() + "%'");
			}
		}

		if (pageSize > 0) {
			pageNo = (pageNo - 1) * pageSize;

			sql.append(" Limit " + pageNo + ", " + pageSize);
		}

		ArrayList<TicketBean> list = new ArrayList<TicketBean>();
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				bean = new TicketBean();
				bean.setId(rs.getLong(1));
				bean.setCategoryId(rs.getLong(2));
				bean.setCategoryName(rs.getString(3));
				bean.setUserId(rs.getLong(4));
				bean.setUserName(rs.getString(5));
				bean.setTicketNo(rs.getLong(6));
				bean.setTitle(rs.getString(7));
				bean.setDate(rs.getDate(8));
				bean.setStatus(rs.getString(9));
				bean.setDescription(rs.getString(10));
				bean.setCreatedBy(rs.getString(11));
				bean.setModifiedBy(rs.getString(12));
				bean.setCreatedDatetime(rs.getTimestamp(13));
				bean.setModifiedDatetime(rs.getTimestamp(14));
				list.add(bean);
			}
			rs.close();
		} catch (Exception e) {
			throw new ApplicationException("Exception : Exception in search Ticket");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.info("Model search End");
		return list;
	}

	public List<TicketBean> list() throws ApplicationException {
		return list(0, 0);
	}

	public List<TicketBean> list(int pageNo, int pageSize) throws ApplicationException {
		log.info("Model list Started");
		ArrayList<TicketBean> list = new ArrayList<TicketBean>();
		StringBuffer sql = new StringBuffer("select * from Ticket");

		if (pageSize > 0) {
			pageNo = (pageNo - 1) * pageSize;
			sql.append(" limit " + pageNo + "," + pageSize);
		}

		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				TicketBean bean = new TicketBean();
				bean.setId(rs.getLong(1));
				bean.setCategoryId(rs.getLong(2));
				bean.setCategoryName(rs.getString(3));
				bean.setUserId(rs.getLong(4));
				bean.setUserName(rs.getString(5));
				bean.setTicketNo(rs.getLong(6));
				bean.setTitle(rs.getString(7));
				bean.setDate(rs.getDate(8));
				bean.setStatus(rs.getString(9));
				bean.setDescription(rs.getString(10));
				bean.setCreatedBy(rs.getString(11));
				bean.setModifiedBy(rs.getString(12));
				bean.setCreatedDatetime(rs.getTimestamp(13));
				bean.setModifiedDatetime(rs.getTimestamp(14));
				list.add(bean);
			}
			rs.close();
		} catch (Exception e) {
			throw new ApplicationException("Exception : Exception in getting list of Tickets");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		log.info("Model list End");
		return list;

	}

	
}
