<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Ryan Login</title>
<link rel="stylesheet" href="style.css">
<script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>
<script src="http://lab.alexcican.com/set_cookies/cookie.js"
		type="text/javascript"></script>
<style>
.sort {
	font-size: 11px;
	margin-top: 10px;
	display: flex;
	justify-content: space-between;
	align-items: center;
	display: flex;
}

.sort a:hover {
	opacity: 0.3
}

.sort1 {
	display: flex;
	align-items: center;
}

.sort1 span {
	margin-left: 3px;
}

a {
	margin-left: 0;
	font-size: 11px;
	text-decoration: none;
	color: black;
	text-decoration: none;
	font-size: 11px;
}

#_btnLogin:hover {
	opacity: 0.3
}

#chk_save_id {
	margin: 0;
}
</style>
</head>
<body>
	<form id="frm">
		<svg id="ryan" viewBox="0 0 120 120"
			xmlns="http://www.w3.org/2000/svg">
            <path d="M0,150 C0,65 120,65 120,150" fill="#e0a243"
				stroke="#000" stroke-width="2.5" />
            <g class="ears">
                <path d="M46,32 L46,30 C46,16 26,16 26,30 L26,32"
				fill="#e0a243" stroke="#000" stroke-width="2.5"
				stroke-linecap="round" transform="rotate(-10,38,24)" />
                <path d="M74,32 L74,30 C74,16 94,16 94,30 L94,32"
				fill="#e0a243" stroke="#000" stroke-width="2.5"
				stroke-linecap="round" transform="rotate(10,82,24)" />
            </g>
            <circle cx="60" cy="60" r="40" fill="#e0a243" stroke="#000"
				stroke-width="2.5" />
            <g class="eyes">
                <!-- left eye and eyebrow-->
                <line x1="37" x2="50" y1="46" y2="46" stroke="#000"
				stroke-width="3" stroke-linecap="round" />
                <circle cx="44" cy="55" r="3" fill="#000" />
                <!-- right eye and eyebrow -->
                <line x1="70" x2="83" y1="46" y2="46" stroke="#000"
				stroke-width="3" stroke-linecap="round" />
                <circle cx="76" cy="55" r="3" fill="#000" />
            </g>
            <g class="muzzle">
                <path
				d="M60,66 C58.5,61 49,63 49,69 C49,75 58,77 60,71 M60,66 C61.5,61 71,63 71,69 C71,75 62,77 60,71"
				fill="#fff" />
                <path
				d="M60,66 C58.5,61 49,63 49,69 C49,75 58,77 60,71 M60,66 C61.5,61 71,63 71,69 C71,75 62,77 60,71"
				fill="#fff" stroke="#000" stroke-width="2.5" stroke-linejoin="round"
				stroke-linecap="round" />
                <polygon points="59,63.5,60,63.4,61,63.5,60,65"
				fill="#000" stroke="#000" stroke-width="5" stroke-linejoin="round" />
            </g>
            <path
				d="M40,105 C10,140 110,140 80,105 L80,105 L70,111 L60,105 L50,111 L40,105"
				fill="#fff" />
        </svg>
        <input name="id" id="id" type="text" placeholder="Id">
        <input name="pwd" id="pwd" type="password" placeholder="Password">
        <input type="button" id="_btnLogin" value="Login">
        <div class="sort">
        	
        	<!--  회원가입 버튼 클릭시 regiButton() 호출-->
        	<button type="button" onclick="regiButton()">회원가입</button>
        	<div class="sort1">
        		<input type="checkbox" id="chk_sava_id"><span>아이디 저장</span>
        	</div>
        </div>
        </form>
        <script>
        /* RegiInfoServlet으로 이동*/
        function regiButton() {
        	location.href="registerInfo?type=moveRegister"
        }
        // 로그인 버튼 클릭시에 동작하는 함수
        $("#_btnLogin").click(function(){
        	if($("#id").val().trim() == "") {
        		alert("id를 입력해 주십시오.");
        		$("#id").focus();
        	}else if ($("#pwd").val().trim() == "") {
        		alert("password를 입력해 주십이오.");
        		$("#pwd").focus();
        	} else {
        		//LoginServlet 으로 이동
        		$("#frm").attr({
        			"action": "login",
        			"method": "post"
        		}).submit();
        	}
        });
        
        var user_id = $.cookie("user_id");
        if(user_id !=null) { //저장한 쿠키 있을
        	$("#id").val(user_id);
        	$("#chk_save_id").attr("checked", "checked");
        }
        $("#chk_save_id").click(function() {
        	
        	if($("#chk_save_id").is(":checked")) {
        		
        		if($("#id").val.trim() == "") {
        			alert("id를 입력하세요.");
        			$("#chk_save_id").prop("checked", false);
        		} else { //정상 가입한 경우 
						//쿠키 저장 
						$.cookie("user_id", $("#id").val().trim(), {
							expires : 7,
							path : './'
						});
        			}
        		}else {
        			// alert("체크없어짐");
        			$.removeCookie("user_id", {
        				path: './'
        			});
        		}	
        });
        
        </script>
        <script src="script.js">
        
        </script>

</body>
</html>