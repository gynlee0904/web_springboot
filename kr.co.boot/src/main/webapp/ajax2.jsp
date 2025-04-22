<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>외부JS를 이용하여 DB에 저장 및 회신결과 받기</title>
</head>
<body>
상담자 아이디 : <input type="text" id="CID"> <br>
상담자명 : <input type="text" id="CNAME"> <br>
<input type="button" value="상담신청" onclick="ajaxtest()"> <br>
</body>
<script src="http://172.30.1.90:8080/ajax2.js" ></script>
<!-- crossorigin="anonymous" : 익명의 연결도 허용한다는 뜻(인증서 없이. 보안ㅈ됨) -->
<script>
function ajaxtest(){
	var cid = document.getElementById("CID");
	var cnm = document.getElementById("CNAME");
	
	if(cid.value=="" || cnm.value==""){
		alert("모든입력값에 필수로 데이터를 입력하세요.");
	}else{
		//상대방 서버에 있는 함수를 로드해서 호출 및 결과값을 호출받음 
		var result1 = apink_love(cid.value, cnm.value);  //API로 전송(제출) 
		console.log("result1 : "+ result1);
		
		if(result1=="ok"){
			alert("상담신청이 올바르게 등록되었습니다");
		}else {
			alert("현재 서비스 불안정으로, 잠시 후 다시 시도해주세요.");
		}
	}
}
</script>
</html>