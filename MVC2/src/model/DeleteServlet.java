package model;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.BbsDao;

/**
 * Servlet implementation class DeleteServlet
 */
@WebServlet("/bbsDelete")
public class DeleteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
   @Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String sSeq = req.getParameter("seq");
		int seq = Integer.parseInt(sSeq);
		
		BbsDao bbsDao = BbsDao.getInstance();
		
		boolean isS = bbsDao.deleteBbs(seq);
		resp.sendRedirect("finding.jsp?type=delete&isS=" + isS);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		super.doPost(req, resp);
	}

}
