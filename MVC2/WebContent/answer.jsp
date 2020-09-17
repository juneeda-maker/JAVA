<%@page import="dto.MemberDto"%>
<%@page import="dto.BbsDto"%>
<%@page import="dao.BbsDao" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

	<h1>부모글</h1>
	<div align = "center">
		<table style= "border=2">
			<col width="200">
			<col width="500">
			
			<tr>
				<th>작성자</th>
				<td>${bbsDto.id}</td>
			</tr>
			<tr>
				<th>제목</th>
				<td>${bbsDto. title}</td>
			</tr>
			<tr>
				<th>작성자</th>
				<td>${bbsDto.id}</td>
			</tr>
			<tr>
				<th>작성일</th>
				<td>${bbsDto.wdate}</td>
			</tr>
			<tr>
				<th>조회수</th>
				<td>${bbsDto.readcount}</td>
			</tr>
			<tr>
				<th>정보</th>
				<td>${bbsDto.ref}-${bbsDto.step}-${bbsDto.depth }</td>
			</tr>
			<tr>
				<th>내용</th>
				<td><textarea rows="10" cols="70" disabled="disabled">${bbsDto.content }</textarea></td>
			</tr>
		</table>
	
	</div>
	<hr>
	
	<%
		MemberDto mem = (MemberDto) session.getAttribute("login");
	%>
	
	<h1 align ="left">답글</h1>
	<form action="answer" method="post">
		<input type="hidden" name="seq" value="${bbsDto.seq }">
		<input type="hidden" name="type" value="answerAf">
		<table border="1">
			<col width="200">
			<col width="500">
			
			<tr>
				<th>아이디</th>
				<td><input type="text" name="id" readonly="readonly" size="50" value="<%=mem.getId()%>"></td>
			</tr>
			<tr>
				<th>제목</th>
				<td><input type="text" name="title" size="50"></td>
			</tr>
			<tr>
				<th>내용</th>
				<td><textarea rows="10" cols="70" name="content"></textarea></td>
			</tr>
			<tr>
				<td colspan="2" align="center"><input type="submit" value="답글추가"></td>
			</tr>
		</table>
	</form>
</body>
</html>