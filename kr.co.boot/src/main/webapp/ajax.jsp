<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>스프링부트에서 라이브러리 없이 폼데이터로 전송</title>
</head>
<body>
<input type="checkbox" id="all">전체선택 <br>
<!-- pd class는 빈 클래스입니다 쓰지마세요 라고 명시해두긔 -->
<input type="checkbox" class="pd" value="pd1">상품1 <br>
<input type="checkbox" class="pd" value="pd2">상품2 <br>
<input type="checkbox" class="pd" value="pd3">상품3 <br>
<input type="checkbox" class="pd" value="pd4">상품4 <br>
<input type="checkbox" class="pd" value="pd5">상품5 <br>

<input type="button" value="전송" onclick="ajax()"><br>

</body>
<script src="./ajax.js?v=2"></script>
<!-- js스크립트를 상단에 쓰면 작동이 안될 수 있음 
	 defer="defer"속성을 쓰면 가능하나 완벽하지않음
	 스크립트는 아래에 쓰긔  -->
</html>