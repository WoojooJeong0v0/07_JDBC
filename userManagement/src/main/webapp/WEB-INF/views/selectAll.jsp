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
  <table>
  <thead> 
  <tr>
  <th>회원 번호</th>
  <th>아이디</th>
  <th>비밀 번호</th>
  <th>이름</th>
  <th>등록일</th>
  </tr> 
  </thead>
  <tbody>
  c:foreach 사용
  </tbody>
  <tfoot></tfoot>
  
  </table>
</body>
</html>