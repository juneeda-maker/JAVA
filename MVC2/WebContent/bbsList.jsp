<%@page import="dto.BbsDto"%>
<%@page import="java.util.List"%>
<%@page import="dao.BbsDao"%>
<%@page import="dto.MemberDto"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%!// 댓글의 여백과 이미지를 추가하는 함수
	public String arrow(int depth) {
		String rs = "<img src='./image/arrow.png' width='20px' height='20px'/>";
		String nbsp = "&nbsp;&nbsp;&nbsp;&nbsp;";

		String ts = "";
		for (int i = 0; i < depth; i++) {
			ts += nbsp;
		}
		return depth == 0 ? "" : ts + rs;
	}%>

<%
	request.setCharacterEncoding("utf-8");
	// 검색
	// 처음 들어왔을때는 파라미터가 넘어오지 않지만
	// 검색, 페이징을 실행했을경우 해당 값들이 넘어오게된다
	String searchWord = request.getParameter("searchWord");
	String choice = request.getParameter("choice");

	//sel을 지정하는 이유는 sel이 넘어오는 경우 아무것도 수행하지 않게 하기 위함
	if (choice == null || choice.equals("")) {
		choice = "sel";
	}

	// 검색어를 지정하지 않고 choice가 넘어 왔을 때
	if (choice.equals("sel")) {
		searchWord = "";
	}
	if (searchWord == null) {
		searchWord = "";
		choice = "sel";
	}
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>
<style>
@charset "UTF-8";

ul {
	list-style-type: none;
	margin: 0;
	padding: 0;
	overflow: hidden;
	background-color: #333;
}

li {
	float: left;
}

li a {
	display: block;
	color: white;
	text-align: center;
	padding: 14px 16px;
	text-decoration: none;
	font-size: 24px;
}

li a:hover {
	color: black;
	background-color: #c0c0c0;
}
</style>
</head>
<body>

	<%
		Object ologin = session.getAttribute("login");
		// request.getSession().getAttribute(name)
		MemberDto mem = null;
		if (ologin == null) {
	%>
	<!-- 세션이 만료되어 로그인이 풀렸을때 
	IndexServlet으로 이동 -->
	<script type="text/javascript">
		alert("로그인 해 주십시오");
		location.href = "loginLyan";
		</script>
	<%
		}
		mem = (MemberDto) ologin;
	%>

	<script type="text/javascript">
$(document).ready(function () {
	var _choice = '<%=choice%>';
	var _searchWord = '<%=searchWord%>';
	if(_choice != '' && _choice != 'sel'){		
		if(_searchWord != ""){			
			$("#choice").val(_choice);
			$("#search").val(_searchWord);
		}
	}
});

</script>


	<%
		// paging

		String spageNumber = request.getParameter("pageNumber");
		int pageNumber = 0;
		if (spageNumber != null && !spageNumber.equals("")) {
			pageNumber = Integer.parseInt(spageNumber);
		}

		BbsDao dao = BbsDao.getInstance();

		List<BbsDto> list = dao.getBbsPagingList(choice, searchWord, pageNumber);

		int len = dao.getAllBbs(choice, searchWord);
		System.out.println("총 글의 갯수:" + len);

		int bbsPage = len / 10; // 예: 22개의 글 -> 3페이지
		if (len % 10 > 0) {
			bbsPage = bbsPage + 1;
		}
	%>

	<h4 align="right" style="background-color: #f0f0f0">
		환영합니다
		<%=mem.getId()%>님
	</h4>

	<h1>게시판</h1>

	<ul>
		<li><a href="./bbsList.jsp">게시판</a></li>
		<!-- <li><a href="./calEx/calendar.jsp">일정관리</a></li> -->
		<!-- <li><a href="./PdsList.jsp">자료실</a></li> -->
	</ul>
	
	<div align="center">

		<table border="1">
			<col width="70">
			<col width="600">
			<col width="150">

			<tr>
				<th>번호</th>
				<th>제목</th>
				<th>작성자</th>
			</tr>
			<%
				if (list == null || list.size() == 0) {
				System.out.println("listsize:"+list.size());
			%>
			<tr>
				<td colspan="3">작성된 글이 없습니다</td>
			
			</tr>
			<%
				} else {

					for (int i = 0; i < list.size(); i++) {
						BbsDto bbs = list.get(i);
					
			%>
			<tr>
				<th><%=i + 1%></th>
			<%-- 	
		<td>
			<%=arrow(bbs.getDepth()) %>
			<a href="bbsdetail.jsp?seq=<%=bbs.getSeq() %>">
				<%=bbs.getTitle() %>	
			</a>
		</td>
		 --%>
				<td>
					<%
						if (bbs.getDel() == 0) {
					%> <%=arrow(bbs.getDepth())%> <!-- 클릭시에 BbsDetailServlet에 seq값을 함께 넘겨준다-->
					<a href="bbsDetail?seq=<%=bbs.getSeq()%>"> <%=bbs.getTitle()%>
				</a> <%
 				} else {
				 %> <font color="#ff0000">이 글은 작성자에 의해서 삭제되었습니다</font> <%
 					}
				 %>
				</td>
				<td align="center"><%=bbs.getId()%></td>
			</tr>
			<%
				}
				}
			%>
		</table>

		<%
			for (int i = 0; i < bbsPage; i++) { // [1] 2 [3]
				if (pageNumber == i) { // 현재 페이지
		%>
		<span style="font-size: 15pt; color: #0000ff; font-weight: bold;">
			<%=i + 1%>
		</span>&nbsp;
		<%
			} else { // 그 외의 페이지
		%>
		<!-- a버튼 클릭시 goPage() 호출 -->
		<a href="#none" title="<%=i + 1%>페이지" onclick="goPage(<%=i%>)"
			style="font-size: 15pt; color: #000; font-weight: bold; text-decoration: none">
			[<%=i + 1%>]
		</a>&nbsp;
		<%
			}
			}
		%>
		<br>
		<!-- 글쓰기 a 태그 누를시에BbsWrite에 type=write를 같이넘겨준다 -->
		<br> <a href="bbsWrite?type=write">글쓰기</a>
	</div>

	<br>

	<div align="center">

		<select id="choice">
			<option value="sel">선택</option>
			<option value="title">제목</option>
			<option value="writer">작성자</option>
			<option value="content">내용</option>
		</select> <input type="text" id="search" value="">

		<!-- 검색버튼 클릭시 searchBbs() 호출 -->
		<button onclick="searchBbs()">검색</button>

	</div>

	<br>
	<br>
	<br>

	<script type="text/javascript">
function searchBbs() {
	var choice = document.getElementById("choice").value;
	var word = $("#search").val();
	if(word == ""){
		document.getElementById("choice").value = 'sel';
	}
	
	//ListServlet에 type=search및 검색어, 카테고리를 넘겨준다
	location.href = "bbsList?type=search&searchWord=" + word + "&choice=" + choice;
	
}

//페이징 함수
function goPage( pageNum ) {
	var choice = $("#choice").val();
	var word = $("#search").val();
	if(word == ""){
		document.getElementById("choice").value = 'sel';
	}
	var linkStr = "bbsList?type=page&pageNumber=" + pageNum;
	if(choice != 'sel' && word != ""){
		linkStr = linkStr + "&searchWord=" + word + "&choice=" + choice;
	}
	location.href = linkStr;
//	location.href = "bbsList.jsp?pageNumber=" + pageNum;
}

</script>


</body>
</html>





