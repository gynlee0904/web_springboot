<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>cdn서버에 파일전송</title>
</head>
<body>
<!-- 
cdn : 콘텐츠 전송 네트워크 서비스를 말함. 
cdn-server : 콘텐츠를 제공하는 서버. 이러닝, 스트리밍서비스 등 
쇼핑몰, 상품판매 사이트일 경우 cdn 서버 => 배너, 각종 아이콘, 상품 이미지..
cdn서버를 분리하는 이유 : cdn서버에는 이미지, 동영상, 문서파일 등 컨텐츠 관련 파일만 업로드 함 
.html, jsp, htm, js, css.. 이런 파일은 업로드 하지 않는다. 
 -->
 <form id="frm" method="post" action="./cdn_uploadok.do" enctype="multipart/form-data">
 cdn 파일전송 : <input type="file" name="mfile"> <br>
 <input type="button" value="전송" onclick="fileok()">
</form>
</body>
<script>
function fileok(){
	frm.submit();
}
</script>
</html>