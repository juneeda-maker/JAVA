package model;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.BbsDao;
import dto.BbsDto;

@WebServlet("/answer")
public class AnswerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    @Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		processFunc(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		String type = req.getParameter("type");
		if(type.equals("answerAf")) {
			BbsDao bbsDao = BbsDao.getInstance();
			String sSeq = req.getParameter("seq");
			int seq = Integer.parseInt(sSeq);
			
			String id = req.getParameter("id");
			String title = req.getParameter("title");
			String content = req.getParameter("content");
			boolean isS = bbsDao.answer(seq, new BbsDto(id, title, content));
			resp.sendRedirect("finding.jsp?type=answer&isS=" + isS);
		}
	}
	public void processFunc(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		req.setCharacterEncoding("utf-8");
		String type = req.getParameter("type");
		if(type.equals("moveAnswer")) {
			String sSeq = req.getParameter("seq");
			int seq = Integer.parseInt(sSeq);
			
			BbsDao bbsDao = BbsDao.getInstance();
			BbsDto bbsDto = bbsDao.getBbs(seq);
			
			req.setAttribute("bbsDto", bbsDto);
			forward("answer.jsp", req, resp);
		}
	}
	public void forward(String link, HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		RequestDispatcher dispatch = req.getRequestDispatcher(link);
		dispatch.forward(req, resp);
	}
	

}
