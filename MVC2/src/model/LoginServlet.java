package model;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.MemberDao;
import dto.MemberDto;


@WebServlet("/login")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    @Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
	}

    @Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		processFunc(req, resp);
	}
    
    public void processFunc(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    	String id = req.getParameter("id");
    	String pwd = req.getParameter("pwd");
    	System.out.println(id + " " + pwd);
    	MemberDao dao = MemberDao.getInstance();
    	
    	//해당 id와 pwd를 기준으로 정보를 가져오기.
    	MemberDto dto = dao.login(id, pwd);
    	
    	// dto가 존재할때 (가입된 아이디와 비밀번호가 일치할때 )
    	if (dto !=null) {
    		System.out.println(dto.toString());
    		HttpSession session = req.getSession();
    		session.setAttribute("login", dto);
    		session.setMaxInactiveInterval(30 * 60 * 60);
    		// finding.jsp에 typelogin과 dto의 이름을 넘겨준다.
    		resp.sendRedirect("finding.jsp?type=login" + "&name=" + dto.getName());
    	} else {
    		//dto 가 존재하지 않기 때문에 name을 넘겨주지 않고 그냥 finding 으로넘겨 처리하게한다.
    		resp.sendRedirect("finding.jsp?type=login");
    	}
    }
    
    

}
