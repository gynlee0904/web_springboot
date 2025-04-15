<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	Date date = new Date();
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>bootstrap 회원가입 + 스프링부트 </title>
<link rel="stylesheet" href="./css/bootstrap.css?v=<%=date%>">
<script src="./js/bootstrap.js?v=<%=date%>"></script>
</head>
<body>
<form id="form" method="post" action="./join.do">
<input type="hidden" name="MCODE" value="1">
<input type="hidden" name="MJOIN" value="WEB">

<div class="row g-3 align-items-center">
  <div class="col-auto">
    <label class="col-form-label">회원 ID</label>
  </div>
  <div class="col-auto">
    <input class="form-control width1" name="MID" type="text" placeholder="아이디를 입력하세요">
  </div>
</div>

<div class="row g-3 align-items-center">
  <div class="col-auto">
    <label class="col-form-label">Password</label>
  </div>
  <div class="col-auto">
    <input type="password" name="MPASS" class="form-control width1" placeholder="패스워드를 입력하세요">
  </div>
</div>

<div class="row g-3 align-items-center">
  <div class="col-auto">
    <label class="col-form-label">고객명</label>
  </div>
  <div class="col-auto">
    <input type="text" name="MNAME" class="form-control width1" placeholder="고객명을 입력하세요">
  </div>
</div>

<div class="row g-3 align-items-center">
  <div class="col-auto">
    <label class="col-form-label">별명</label>
  </div>
  <div class="col-auto">
    <input type="text" name="MNICK" class="form-control width1" placeholder="별명을 입력하세요">
  </div>
</div>

<div class="row g-3 align-items-center">
  <div class="col-auto">
    <label class="col-form-label">이메일</label>
  </div>
  <div class="col-auto">
    <input type="text" name="MEMAIL" class="form-control width1" placeholder="이메일을 입력하세요">
  </div>
</div>

<div class="row g-3 align-items-center">
  <div class="col-auto">
    <label class="col-form-label">연락처</label>
  </div>
  <div class="col-auto">
    <input type="text" name="MHP" class="form-control width1" placeholder="-빼고 숫자만 입력하세요">
  </div>
</div>

<input type="button" id="btn" class="btn btn-pink" value="회원가입">
</form>

</body>
<script type="module">
import {member_join} from "./js/ecma/test.js?v=<%=date%>";
member_join();


</script>
</html>