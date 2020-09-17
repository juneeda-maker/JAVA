package model;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class ListServlet
 */
@WebServlet("/bbsList")
public class ListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    @Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		processFunc(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// resp.sendRedirect("bbsList.jsp");
	}
	
	public void processFunc (HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String type = req.getParameter("type");
		System.out.println("타입" + type);
		
		//타입들이 나누어져 있다 일단
		//기본적으로 type에 아무값도 넘어오지 않았을때는 단순하게 bbsList.jsp로 이동한다.
		if(type == null || type.equals("")) {
			resp.sendRedirect("bbsList.jsp");
		
		//검색부분
		//type에 search 검색부분을 넘겼을때 검색 단어와 카테고리를 받아서 bbsList에 넘겨준다.
		}else if (type.equals("search")) {
			String searchWord = req.getParameter("searchWord");
			String choice = req.getParameter("choice");
			System.out.println("searchWord" + searchWord);
			
			resp.sendRedirect("bbsList.jsp?searchWord=" + searchWord + "&choice=" + choice);
			//페이징 처리
			//searchWord와 choice 없는경우 그냥 넘겨주고
			//있는 경우 pageNumber와 searchWord와 choice를 같이 bbsList에 넘겨준다.
		}else if (type.equals("page")) {
			String pageNumber = req.getParameter("pageNumber");
			String searchWord = req.getParameter("searchWord");
			String choice = req.getParameter("choice");
			
			if(choice == null || searchWord == null) {
				resp.sendRedirect("bbsList.jsp?pageNumber=" + pageNumber);
			}else if (!choice.equals("sel") && !searchWord.equals("")) {
				resp.sendRedirect("bbsList.jsp?pageNumber=" + pageNumber + "&searchWord=" + searchWord + "&choice=" + choice);
			}
		}
	}

}
