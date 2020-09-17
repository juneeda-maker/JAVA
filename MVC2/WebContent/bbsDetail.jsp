<%@page import="dao.MemberDao"%>
<%@page import="dto.MemberDto"%>
<%@page import="dao.BbsDao"%>
<%@page import="dto.BbsDto"%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<style>
.sort {
	margin-top: 10px;
	display: flex;
	justify-content: center;
}

.sort>* {
	margin-right: 10px;
}
</style>
<body>

	<!-- 본인 아이디와 작성자 아이디가 같을때만 수정및 삭제가 가능할수 있도록 session에서 dto를 받아온다. -->
	<%
		MemberDto mem = (MemberDto) request.getSession().getAttribute("login");
	%>
	<%
		//BbsDetailServlet에서 넘겨받은 값.
		BbsDto bbsDto = (BbsDto)request.getAttribute("bbsDto");
	%>
	
	<div align="center">
		<table border="1">
			<colgroup>
				<col style="width: 150">
				<col style="width: 600">
			</colgroup>
			
			<tr>
				<th>작성자</th>
				<td><%=bbsDto.getId()%></td>
			</tr>	
			
			<tr>
				<th>제목</th>
				<td><%=bbsDto.getTitle()%></td>
			</tr>
			
			<tr>
				<th>작성일</th>
				<td><%=bbsDto.getWdate()%></td>
			</tr>
			
			<tr>
				<th>조회수</th>
				<td><%=bbsDto.getReadcount() %></td>
			</tr>
			
			<tr>
				<th>정보</th>
				<td><%=bbsDto.getRef()%>-<%=bbsDto.getStep()%>-<%=bbsDto.getDepth()%></td>
			</tr>
			
			<tr>
				<th>내용</th>
				<td align="center"><textarea rows="10" cols="90" readonly="readonly"><%=bbsDto.getContent()%></textarea></td>
			</tr>
		</table>
		<div class="sort">
			<%
				if (bbsDto.getId().equals(mem.getId())) {
			%>
			<!-- seq를 파라미터로 넣어줘서 BbsUpdateServlet에 넘겨준다. -->
			<button onclick="updateBbs(<%=bbsDto.getSeq()%>)">수정</button>
			
			<!-- seq를 파라미터로 넣어줘서 BbsDeleteServlet에 넘겨준다. -->
			<button onclick="deleteBbs(<%=bbsDto.getSeq()%>)">삭제</button>
			
			<%
				}
			%>
			<form action="answer" method="get">
				<input type="hidden" name="seq" value="<%=bbsDto.getSeq()%>">
				<input type="hidden" name="type" value="moveAnswer">
				<input type="submit" value="댓글">
			</form>
			
			<button type="button" onclick="location.href='bbsList'">목록</button>
		</div>
	</div>
	
	<script type="text/javascript">
	
		function updateBbs(seq) {
			//moveUpdate 파라미터와 seq를 넘겨준다.
			location.href = "bbsUpdate?type=moveUpdate&seq=" + seq;
		}
		
		function deleteBbs(seq) {
			location.href = "bbsDelete?seq=" + seq;
		}
	</script>
</body>
</html>