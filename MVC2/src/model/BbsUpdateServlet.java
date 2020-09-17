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


@WebServlet("/bbsUpdate")
public class BbsUpdateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
  
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		processFunc(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		super.doPost(req, resp);
	}
	
	public void processFunc(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		String type = req.getParameter("type");
		//bbsUpdate.jsp에 넘기기 위해서는.
		//bbsUpdate에서도 detail과 같이 해당 기입된 정보들이 필요하기 떄문에.
		//seq로 접근해서 다시 bbsDto를 seq로가져온다.
		if(type.equals("moveUpdate")) {
			String sSeq = req.getParameter("seq");
			System.out.println(sSeq);
			int seq = Integer.parseInt(sSeq);
			
			BbsDao bbsDao = BbsDao.getInstance();
			BbsDto bbsDto = bbsDao.getBbs(seq);
			//짐을 싸서.
			req.setAttribute("bbsDto", bbsDto);
			//forqard로 보내준다.
			forward("bbsUpdate.jsp", req, resp);
			
			//bbsUpdate에서 수정버튼을 눌러서 submit 되고 나서의 수행.
		}else if(type.equals("bbsUpdateAf")) {
			String sSeq = req.getParameter("seq");
			System.out.println(sSeq);
			int seq = Integer.parseInt(sSeq);
			
			String title = req.getParameter("title");
			String content = req.getParameter("content");
			BbsDao bbsDao = BbsDao.getInstance();
			//값들을 받아서 수정 메소드 실행.
			boolean isS = bbsDao.updateBbs(seq, title, content);
			System.out.println("업데이트" + isS);
			// finding 에 boolean 값을 넘겨준다.
			resp.sendRedirect("finding.jsp?type=update&isS=" + isS);
		}
	}
	
	public void forward(String link ,HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		RequestDispatcher dispatch = req.getRequestDispatcher(link);
		dispatch.forward(req, resp);
	}

}
