
// 아이디가 userId, check인 요소를 얻어와 변수에 저장
const userId = document.querySelector("#userId");
const check = document.querySelector("#check");

// #userId 에 입력input 됐을 때  (input 이벤트가 감지 되었을 때)
userId.addEventListener("input", e => {

 // 비동기로 아이디 중복값을 얻어오는 ajax 코드 작성 예정
 // servlet 으로 가야 함 
 // -ajax : 서버와 비동기 통신하기 위한 JS 기술 (멀티태스킹, 여러 동작을 수행)
 // -fetch() API : JS에서 제공하는 ajax를 쉽게 쓰는 코드

 // e.target. 지금 타게팅되고 있는 이벤트
 fetch("/signUp/idCheck?userId=" + e.target.value)
 .then(resp => resp.text()) // 응답을 text로 변환
 .then(result => {
    // result 첫번째 then에서 resp.text() 통해 변환된 값

    // console.log(result);

    if(result == 0){// 중복 아님 -> 사용 가능
      check.classList.add("green");      // 클래스추가
      check.classList.remove("red"); //  클래스 제거   

      check.innerText = "사용 가능한 아이디입니다"
    } else {
      check.classList.add("red");   
      check.classList.remove("green");
      check.innerText = "이미 사용 중인 아이디입니다"
    }

 });


});