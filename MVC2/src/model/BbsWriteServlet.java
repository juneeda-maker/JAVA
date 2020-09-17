package model;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.BbsDao;
import dto.BbsDto;


@WebServlet("/bbsWrite")
public class BbsWriteServlet extends HttpServlet {
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
		System.out.println(type);
		System.out.println("aa");
		//글쓰기 버튼으로 단순 이동할때 
		if(type.equals("write")) {
			resp.sendRedirect("bbsWrite.jsp");
		//bbsWrite.jsp에서 글작성을 완료하고 값을 finding 에서 처리하게끔 isS를 넘겨준다.	
		}else if(type.equals("writeAf")) {
			BbsDao bbsDao = BbsDao.getInstance();
			String id = req.getParameter("id");
			String title = req.getParameter("title");
			String content = req.getParameter("content");
			BbsDto bbsDto = new BbsDto(id, title, content);
			boolean isS = bbsDao.writeBbs(bbsDto);
			resp.sendRedirect("finding.jsp?type=write&isS="+isS);
		}
	}
}
