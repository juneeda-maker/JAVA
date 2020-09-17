<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>
</head>
<body>
	<h1>회원가입</h1>
	<p>환영합니다.</p>
	<div align="center">
	
	<!-- submit시 RegiInfoServlet에 method방식은 pos에
	  type registerAf와 값들을 가지고 이동한다 -->
		<form action="registerInfo" method="post">
		<input type="hidden" name="type" value="registerAf">
			<table border="1">
				<tr>
					<td>아이디</td>
					<td><input type="text" id="id" name="id" size="20">id확인
						<p id="idcheck" style="font-size: 8px"></p>
						<input type="button" id="btn" value="id확인">
					</td>
				</tr>
				<tr>
					<td>패스워드</td>
					<td><input type="text" name="pwd" size="20"></td>
				</tr>
				<tr>
					<td>이름</td>
					<td><input type="text" name="name" size="20"></td>
				</tr>
				<tr>
					<td>이메일</td>
					<td><input type="text" name="email" size="20"></td>
				</tr>
				<tr>
					<td colspan="2"><input type="submit" value="회원가입"></td>
				</tr>
			</table>
		</form>
	</div>
	<script>
	
	//idCheck를 RegiInfoServlet에 넘겨주고 success로 값을 받아온다
	//값을 정확히 받아오면 console.log에 true or false가 찍힌다
		$("#btn").click(function(){
			//alert("됨");
			$.ajax({
				url: "./registerInfo",
				//data : "id=" + $("#id").val(),
				//json타입
				type: "get",
				data: {
					"type": "idCheck",
					"id": $("#id").val()
				},
				success: function(data) {
					console.log(data);
					if(data.trim() === "false"){ 
						//alert("사용 가능한 아이디입니다"
						$("#idcheck").css("color", "0000ff");
						$("#idcheck").html("사용할 수 있는 id입니다.");
					}else if(data.trim() === "true"){
						$("#idcheck").css("color", "#ff0000");
						$("#idcheck").html("사용중인 id입니다.");
						$("#id").val("");
					}
				}
			})
		});
	</script>
</body>
</html>