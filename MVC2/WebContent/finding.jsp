<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	
	<%
		String name = request.getParameter("name");
		String type = request.getParameter("type");
		
		System.out.println(type + " " + name);
		
		/* type 이 login일때 
		각각을 type으로 다 나눈 이유는
		finding에서 servlet으로 보내주는 처리를 전부 하려고 하기 때문이다
		기존 Af의 마무리 역할
		*/
			if(type.equals("login")) {
				//LoginServlet.jsp에서 dto가 존재한 경우 이름을 받아온다
				//즉 로그인이 정확하게 수행되었을 경우
				//ListServlet 으로 이동
				
				if(name !=null){
		%>
		<script>
			alert("안녕하세요 <%=name%>님");
			location.href = "bbsList";
		</script>	
		<% 		
		//로그인 정상 수행 아닐시
				}else{
		%>		
		<script>
			alert("로그인에 실패하였습니다.");
			location.href = "loginLyan";
		</script>
		<% 
			}
			}else
		%>
		
		<!-- BbswriterServlet에서 type및 isS값을 넘겨받아서 처리 -->
		
		<%
			if(type.equals("write")) {
				String sIsS = request.getParameter("isS");
				boolean isS = Boolean.parseBoolean(sIsS);
				System.out.println(isS);
				
				if (isS == true) {
		%>
		<script>
			alert("글 작성이 완료되었습니다.");
			location.href = "bbsList";
		</script>
		<%
			}else{
		%>
		<script>
			alert("글 작성에 실패하였습니다.");
			location.href= "bbsList";
		</script>
		<%
			}
			}else	
		%>	
			
		<!--  RegiInfoServlet에서 type 및 isS값을 넘겨받아서 처리 -->
		<% 	
			if(type.equals("register")) {
				String sIsS = request.getParameter("isS");
				boolean isS = Boolean.parseBoolean(sIsS);
				System.out.println(isS);
				
				if (isS == true) {
		%>	
		<script>
			alert("회원가입이 완료되었습니다.");
			location.href= "loginLyan";
		</script>
		<% 
				}else {
		%>
		<script>
			alert("회원가입에 실패하였습니다.");
			location.href = "registerInfo?type=moveRegister";
		</script>
		<%
			}
			}else
		%>
		
		<!-- BbsUpdateServlet에서 type및 isS 값을 넘겨받아서 처리 -->
		<%
			if (type.equals("update")) {
				String sIsS = request.getParameter("isS");
				boolean isS = Boolean.parseBoolean(sIsS);
				System.out.println(isS);
				
				if (isS == true) {
			
		%>
		<script>
			alert("글 수정이 완료되었습니다.");
			location.href = "bbsList";
		</script>
		<%
			}else{
		%>
		<script>
			alert("글 수정에 실패하였습니다.");
			location.href = "bbsList";
		</script>
		<%
			}
			} else
		%>
		
		<!--  AnswerServlet에서 type및 isS값을 넘겨받아서 처리 -->
		<%
			if(type.equals("answer")) {
				String sIsS = request.getParameter("isS");
				boolean isS = Boolean.parseBoolean(sIsS);
				System.out.println(isS);
				
				if(isS == true) {
					
		%>
		<script>
			alert("답글 작성이 완료되었습니다.");
			location.href = "bbsList";
		</script>
		<%
			} else {
		%>
		<script>
			alert("답글 작성에 실패하였습니다.");
			location.href = "bbsList";
		</script>
		<%
			}
			}else	
		%>
		
		<!--  AnswerServlet에서 type 및 isS 값을 넘겨받아서 처리 -->
		<%
			if (type.equals("delete")) {
				String sIsS = request.getParameter("isS");
				boolean isS = Boolean.parseBoolean(sIsS);
				System.out.println(isS);
				
				if (isS == true) {
					
		%>
		<script>
			alert("삭제가 완료되었습니다.");
			location.href = "bbsList";
		</script>
		<%
			}else{
		%>
		<script>
			alert("삭제에 실패하였습니다.");
			location.href = "bbsList";
		</script>
		<%
			}
			} else	
		%>
</body>
</html>