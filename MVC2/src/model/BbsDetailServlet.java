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


@WebServlet("/bbsDetail")
public class BbsDetailServlet extends HttpServlet {
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
    	req.setCharacterEncoding("utf-8");
    	//bbsList에서 seq를 넘겨받는다.
    	String sSeq = req.getParameter("seq");
    	System.out.println(sSeq);
    	int seq = Integer.parseInt(sSeq);
    	BbsDao bbsDao = BbsDao.getInstance();
    	bbsDao.readCount(seq);
    	//seq로 dto를 받아오는 메소드 출력,
    	BbsDto bbsDto = bbsDao.getBbs(seq);
    	
    	//setAttribute로 dto를 짐싸서 forward로 bbsDetail에 보내준다
    	req.setAttribute("bbsDto", bbsDto);
    	forward("bbsDetail.jsp", req, resp);
    }
    
    public void forward(String link,  HttpServletRequest req, HttpServletResponse resp)
    		throws ServletException, IOException{
    	RequestDispatcher dispatch = req.getRequestDispatcher(link);
    	dispatch.forward(req, resp);
    }
}
