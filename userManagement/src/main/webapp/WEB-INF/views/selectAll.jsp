<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>전체 조회하기</title>
</head>
<body>
  <h1>사용자 전체 조회하기</h1>

  <%-- empty가 true 인 경우 : 빈칸, 빈 배열, 빈 리스트, null --%>
  <c:if test="${not empty param.searchId}" >
  <h4> "${param.searchId}" 검색 결과 </h4>
  </c:if>
  <form action="/search">
  ID 검색 : <input type="text" name= "searchId" placeholder="포함되는 아이디 검색" value = "${param.searchId}">
  </form> <img src = "https://cdn.pixabay.com/photo/2017/01/10/23/01/seo-1970475_1280.png" width=25 height=25>
  <br>

  <table border="1">
  <thead> 
    <tr>
      <th>회원 번호</th>
      <th>아이디</th>
      <%-- <th>비밀번호</th> --%>
      <th>이름</th>
      <%-- <th>등록일</th> --%>
    </tr> 
  </thead>
  <tbody>
    <c:if test="${empty userList}">
    <tr>
      <td colspan="5"> 조회 결과가 없습니다 </td>
    </tr>
    <tr>
      <td colsapn="5"> <img src="https://cdn.pixabay.com/photo/2017/06/19/13/15/not-2419257_1280.png" width=100 height=100>
    </tr>
    </c:if>
    <%-- 조회결과 있을 경우 --%>
    <c:if test="${not empty userList}">
          <c:forEach items="${userList}" var="user">
          <tr>
            <td>${user.userNo}</td>

            <td><a href="/selectUser?userNo=${user.userNo}">${user.userId}</a></td>

            <%-- <td>${user.userPw}</td> --%>
            <td>${user.userName}</td>
            <%-- <td>${user.enrollDate}</td> --%>
          </tr>
          </c:forEach>
    </c:if>
  </tbody>
  <tfoot></tfoot>
  
  </table>
  <br>
  <a href="/">돌아가기</a>

   <%-- session에 메시지가 존재하는 경우 (비어있지 않으면이란 뜻이니까) --%>
  <c:if test="${!empty sessionScope.message}" >
  <script>
   alert("${sessionScope.message}");
  </script>
  <%-- session에 존재하는 message 제거, 알림을 한 번만 띄우도록 하겠단 용도 --%>
  <c:remove var="message" scope="session" />
  </c:if>
</body>
</html>