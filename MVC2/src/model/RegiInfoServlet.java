package model;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.MemberDao;
import dto.MemberDto;


@WebServlet("/registerInfo")
public class RegiInfoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
   
    @Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		processFunc(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// regi.jsp에서 submit을 했을때 받아오는 곳
		String type= req.getParameter("type");
		MemberDao memberDao = MemberDao.getInstance();
		//type=registerAf를 받아오기 떄문에 해당 type이 존재할때 
		if (type.equals("registerAf")) {
			String id = req.getParameter("id");
			String pwd = req.getParameter("pwd");
			String name = req.getParameter("name");
			String email = req.getParameter("eamil");
			
			//멤버를 등록하기 위한 Dao의 메소드
			MemberDto memberDto = new MemberDto(id, pwd, name, email, 3);
			boolean isS = memberDao.addMember(memberDto);
			resp.sendRedirect("finding.jsp?type=register&isS=" + isS);
			
		}
	}
	public void processFunc(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String type = req.getParameter("type");
		System.out.println(type);
		MemberDao memberDao = MemberDao.getInstance();
		
		//type이 moveRegister일때는 regi.jsp로 단순 이동
		if(type.equals("moveRegister")) {
			resp.sendRedirect("regi.jsp");
		} else if (type.equals("idCheck")) {
			String id = req.getParameter("id");
			System.out.println(id);
			//true or false 를 출력해주고 해당 값은 Ajax에서 success시 해당 out.println(isS)를 읽어간다
			PrintWriter out = resp.getWriter();
			boolean isS = memberDao.getID(id);
			out.println(isS);
		}
	}
	

}
