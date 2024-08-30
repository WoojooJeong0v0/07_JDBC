const updateBtn = document.querySelector("#updateBtn");
const deleteBtn = document.querySelector("#deleteBtn");
const goToList = document.querySelector("#goToList");

goToList.addEventListener("click", () => {

  location.href = "/selectAll"; // 목록 페이지 요청

});


// 삭제 버튼이 클릭 되었을 때
deleteBtn.addEventListener("click", () => {

// confirm 이용해서 삭제할지 재확인
 if (!confirm("삭제 하시겠습니까?")){ // 취소 클릭시
  return;
 }

 // JS로 form, input 태그 생성 후 body 태그 제일 밑에 추가해 submit 하기

 const deleteForm = document.createElement("form");  // form

 // 요청 주소 설정
 deleteForm.action = "/deleteUser";

 // 데이터 전달 방식 설정
 deleteForm.method = "POST";

 const input = document.createElement("input"); // input

 // 만들어진 input을 form 자식으로 추가하기
 deleteForm.append(input); // 마지막 자식 추가

 // input type, name, value 설정
 input.type = "hidden";
 input.name = "userNo";

 const userNoTo = document.querySelector("#userNoTd");
 input.value = userNoTo.innerText;
 // value는 input만 씀
 // 그래서 이건 이너텍스트로 작성


 // body태그 제일 마지막에 form 추가
 document.querySelector("body").append(deleteForm);

 // deleteForm 제출하기
 deleteForm.submit();

});