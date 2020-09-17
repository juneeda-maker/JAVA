<%@page import="dao.BbsDao"%>
<%@page import="dto.MemberDto"%>
<%@page import="dto.BbsDto"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>bbswrite.jsp</title>
<style>
span {
	width: 60px;
	display: inline-block;
}

textarea{
	width: 40%;
	height: 280px;
}
</style>
</head>
<body>
		
	<%
		BbsDto bbsDto = (BbsDto)request.getAttribute("bbsDto");
	%>
	
	<!-- bbsUpdateServlet 에 넘겨준다. -->
	<form action="bbsUpdate">
		<p>
		<!--  hidden을 이용해 사용자 눈에는 보이지 않지만 type과 seq를 넘겨준다. -->
		<input type="hidden" name="type" value="bbsUpdateAf">
		<input type="hidden" name="seq" value="<%=bbsDto.getSeq() %>">
			<span>ID:</span><input name="id" value="<%=bbsDto.getId() %>" readonly="readonly">
		</p>
		<p>
			<span>제목:</span><input name="title" value=<%=bbsDto.getTitle() %>>
		</p>
		<span>내용:</span>
		<p>
			<textarea name="content"><%=bbsDto.getContent()%></textarea>
		</p>
		<input type="submit" value="글 수정하기">
		<input type="reset" value="취소하기">
	</form>
</body>
</html>