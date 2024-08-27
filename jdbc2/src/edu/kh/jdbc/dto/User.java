package edu.kh.jdbc.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

// Data Transfer Object : 값을 묶어서 전달하는 용도 객체
// - > DB에 데이터를 전달하거나 가져올 때 사용
// == DB 특정 테이블 한 행의 데이터를 저장하는 형태로 클래스 작성

@Getter              // 외부에서 호출하면 해당 값을 가져갈 수 있음 
@Setter              // 
@NoArgsConstructor   // 매개변수 없는 기본 생성자
@AllArgsConstructor  // 매개변수 전부 다 가지고 있는 생성자
@ToString            // 필드 정보 하나의 문자열로 반환
public class User {

	private int userNumber;
	private String userId;
	private String userPw;
	private String userName;
	private String enrollDate;
	// enrolldate java.sql.date가 아니라 왜 String으로 했는가?
	//  -> DB 조회 시 날짜 데이터를 원하는 형태의 문자열로 변환하여 조회할 예정 
	//  -> TO_CHAR() 이용 (SQL, 원하는 형태의 문자열로 바꿀 수 있음)
	
	
}
