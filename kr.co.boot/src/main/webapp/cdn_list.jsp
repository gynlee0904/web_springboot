<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>CDN API에서 적용된 이미지를 출력</title>
</head>
<body>
<!-- <img src="http://localhost:8080/cdn_listapi/cat"> -->
<img src="http://172.30.1.28:8080/cdn_listapi/cat">  
<!-- 외부에서도 접속 가능 -->

<!-- 
http://localhost:8080/cdn_listapi/cat 라는 주소로 그대로 접속하면 이미지 안나옴(바이트화되어있음)
img src 라는 태그를 통해 이미지로 보여줌. 

cdn서버에 있는 해당 경로(=>cdn_listapi/cat)로 접속해서 이미지 출력 

왜 cdn을 구축함?? 이미지를 마음대로 못퍼가게 하려구 
f12눌러서 봤을 때 파일명을 숨길 수 있음
-->
</body>
</html>