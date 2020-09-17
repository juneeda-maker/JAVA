<%@page import="dto.MemberDto" %>
<%@page import="dto.BbsDto" %>
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
textarea {
	width: 60px;
	height: 280px;
}
</style>
</head>
<body>
	
	<%
		MemberDto memberDto = (MemberDto) session.getAttribute("login"); //LoginServlet에 담긴 setAttribute
		System.out.println("bbsdto :" + memberDto.toString());
	
	%>
	<!--  
	form -> bbswriteAf.jsp
	ID: input
	Title : input
	Content: textarea
	
	button
	 -->
<a href = "logout.jsp">로그아웃</a>
	<form action="bbsWrite">
	<input type = "hidden" name="type" value="writeAf">
		<P>
			<span>ID:</span><input name = "id" value="<%=memberDto.getId()%>" readonly="readonly">
			 <%-- <input name="id" value="${login.id}"
				readonly="readonly"> --%>
			
			<!--  값 가져오기 -->
		</P>
		<P>
			<span>제목:</span><input name="title">
		</P>
		<span>내용:</span>
		<P>
			<textarea name="content"></textarea>
		</P>
		<input type ="submit" value="글 작성하기">
		<input type="reset" value="취소하기">
	</form>	 
</body>
</html>