<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>User 관리 프로젝트</title>
</head>
<body>
  <img src="https://cdn.pixabay.com/photo/2023/03/27/17/24/cat-7881256_1280.png" width=500px height=500px>
 <%-- c:if 조건, 로그인이 안 되어 있는 경우 
 session scope에  loginUser가 없는 경우 --%>
 <c:if test="${empty sessionScope.loginUser}">
    <h1>Login</h1>
    <form action="/login" method="post">
      
      <div>
      ID : <input type="text" name="userId">
      </div>
      <div>
      PW : <input type="password" name="userPw">
      </div>
      <div>
      <button>로그인</button>
      <a href="/signUp">사용자 등록</a>
      </div>
    </form>
 </c:if>

  <%-- 로그인 상태인 경우 --%>
  <c:if test="${!empty sessionScope.loginUser}" >
  <h1>${loginUser.userName}님 환영합니다! </h1>
  <ul>
  <li>userNo : ${loginUser.userNo}</li>
  <li>userId : ${loginUser.userId}</li>
  <li>userName : ${loginUser.userName}</li>
  <li>enrollDate : ${loginUser.enrollDate}</li>
  </ul>

  <button id="logout">Logout</button>

  <hr>

<h3>메뉴</h3>
  <ul>
  <li><a href="#"> 사용자 목록 조회 </a></li>
  </ul>

  </c:if>


  <%-- action 요청 / method 요청 방식 --%>

  <%-- 느낌표 눌러서 자동완성 찾기 --%>
  <%-- session에 메시지가 존재하는 경우 (비어있지 않으면이란 뜻이니까) --%>
  <c:if test="${!empty sessionScope.message}" >
  <script>
   alert("${sessionScope.message}");
  </script>
  <%-- session에 존재하는 message 제거, 알림을 한 번만 띄우도록 하겠단 용도 --%>
  <c:remove var="message" scope="session" />
  </c:if>

<script src="/resources/js/main.js"></script>
</body>
</html>