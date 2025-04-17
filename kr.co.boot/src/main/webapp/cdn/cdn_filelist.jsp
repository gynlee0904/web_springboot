<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	Date date = new Date();
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>cdn 서버 이미지 리스트 목록</title>
<link rel="stylesheet" href="../bootstrap/css/bootstrap.css?v=<%=date%>">
<script src="../bootstrap/js/bootstrap.js?v=<%=date%>"></script>
</head>
<body>

<form id="frm" method="post" action="./cdn_delete.do">
<table class="table table-bordered">
<thead>
	<tr>
		<th align="center">
			<input class="form-check-input" type="checkbox" id="allck">
  		</th>
		<th>이미지</th>
		<th>사용자 파일명</th>
		<th>개발자 파일명</th>
		<th>API 파일명</th>
	</tr>
</thead>

<tbody id="ls">
<c:forEach var="fdata" items="${all}">
	<tr align="center">
		<td >
			<input class="form-check-input ck" type="checkbox" name="cbox" value="${fdata.AIDX}" >
			<!-- value값 꼭 써줘야 함.  체크 안하면 null로 날아감  -->
		</td>
		<td><img src="http://127.0.0.1:8080/cdn/images/${fdata.API_FILE}" style="width:100px;"> </td>
		<!-- http://127.0.0.1:8080/cdn/images/ : 
			 원래 127.0.0.1:8080 대신 도메인을 넣음 -->
		<td>${fdata.ORI_FILE}</td>
<%-- 		<td><a href="${fdata.FILE_URL}" target="-new">${fdata.NEW_FILE}</a></td> --%>
		<td><a href="./download.do/${fdata.NEW_FILE}">${fdata.NEW_FILE}</a></td>
		<!-- <a>태그에 download => url이 http로 잡혀있기 때문에 다운로드 안됨(절대경로로는 해당 속성이 안먹힘) 
			 단, 자기 서버 (로컬호스트)에서 가능  ex: href="/abc.jpg" download="abc.jpg" -->
		<td>${fdata.API_FILE} </td>
	</tr>
</c:forEach>

</tbody>
</table>
</form>
<br><br>

<form id="search" method="get" action="./cdn_filelist.do">
<div class="input-group mb-3 width1">
  <input type="text" name="word" class="form-control" placeholder="찾고자 하는 파일명을 검색하세요">
  <!-- 기본적으로 input 속성 자체에 submit 장착되어있음. input에 커서 깜빡이는 상태에서는 submit이 작동됨. 
  		작동 안되게 하려면 <input>태그 안에 onkeypress="" 작성해줘야 함  -->
  <button class="btn btn-outline-secondary" type="button" id="sh_btn">검색</button>

</div>
</form>
<button class="btn btn-primary" type="button" id="btn">선택 삭제</button>
<button class="btn btn-outline-secondary" type="button" id="sh_btn2">전체목록</button>


</body>
<script type="module">
import {cdn_list} from "../js/ecma/cdn_filelist.js";
</script>
</html>