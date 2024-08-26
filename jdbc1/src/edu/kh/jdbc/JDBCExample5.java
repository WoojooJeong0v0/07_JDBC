package edu.kh.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.Scanner;

public class JDBCExample5 {
	
	
	public static void main(String[] args) {
		
		
		// 아이디, 비밀번호, 이름 입력받아
		// TB_USER 테이블에 삽입하기
		
		
		/* 1. JDBC 객체 참조 변수 선언 */
		Connection conn = null;
		// 중요!! pstmt
		/*
		 * PreparedStatement pstmt - 준비된 statement
		 * SQL 중간에  ?(placeholder)를 작성하여
		 *   ? 자리에 java 값을 대입할 준비가 되어있는 Statement
		 *   
		 *   (빈칸 넣은 거라고 생각하면 됨)
		 *   
		 *   장점 1 : SQL 작성이 간단해짐
		 *   장점 2 : ?에 값 대입 시 자료형에 맞는 형태의 리터럴 표기법으로 대입됨
		 *   			예 ) String 대입 - > '값' (자동으로 ',' 추가)
		 *   			예 ) int 대입 - > 값
		 *   장점 3 : 성능/속도적 우위
		 *   
		 *   ** PreparedStatement는 Statement의 자식이다
		 */
		PreparedStatement pstmt = null;
		Scanner sc = new Scanner (System.in);
		
		// SELECT가 아니기 때문에 ResultSet 필요 없다!
		
		try {
			
			/* DriverManager 이용 Connection  */
			
			Class.forName("oracle.jdbc.driver.OracleDriver");
			
			// url == type + host + port + dbName
			String url = "jdbc:oracle:thin:@localhost:1521:XE";
			String userName = "KH_JSH";
			String password = "KH1234";
			
			conn = DriverManager.getConnection(url, userName, password);
			
			System.out.print("아이디 입력 : ");
			String id = sc.nextLine();
			
			System.out.print("비밀번호 입력 : ");
			String pw = sc.nextLine();
			
			System.out.print("이름 입력 : ");
			String name = sc.nextLine();
			
			String sql = """
					INSERT INTO TB_USER 
					VALUES(SEQ_USER_NO.NEXTVAL, ?, ?, ?, DEFAULT )
					""";
			
			/* AutoCommit 끄기!!!
			 * 
			 * 왜 끄는가 -> 개발자가 트랜잭션을 마음대로 제어하기 위해서
			 *  */
			conn.setAutoCommit(false);
			
			
			
			/* 4. PreparedStatement 객체 생성 */
			// 객체 생성과 동시에 SQL이 담긴다!!!
			// 왜??? -> 미리 ?(placeholder)에 값 받을 준비를 해야 하니까!
			// 값이 올 때까지 기다려야 하기 때문
			pstmt = conn.prepareStatement(sql);
			
			
			/* 5. ?(placeholder)에 알맞은 값 대입 */
			
			// pstmt.set자료형(순서,대입할 값)
			pstmt.setString(1, id);
			pstmt.setString(2, pw);
			pstmt.setString(3, name);
			// -> 여기까지 실행하면 SQL이 작성완료된 상태!!
			
			/* 6. SQL(INSERT구문) 실행 후 결과(int) 반환 받기 */
			// executeQuery () : SELECT 수행, ResultSet 반환
			// executeUpdate() : DML 수행, 결과 행 개수(int) 반환
			// -> 보통 DML 실패 시 0, 성공 시 0 초과된 값이 반환됨
			int result = pstmt.executeUpdate();
			
			
			/* 7. Result 값에 따른 결과 처리 + 트랜잭션 제어 처리 */
			if (result > 0) { // insert 성공 시
				System.out.println(id + "님이 추가 되었습니다");
				conn.commit(); // COMMIT 수행 -> DB에 INSERT 반영
			} else {
				System.out.println("추가 실패");
				conn.rollback(); // 실패는 무조건 rollback 처리!!
			}
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				/* 8. 사용한 JDBC 객체 자원 반환 */
				if (pstmt != null) pstmt.close();
				if (conn != null) conn.close();
				if (sc != null) sc.close();
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		
		
		
		
	} // main end

}

/*
-- TB_USER 테이블 생성
CREATE TABLE TB_USER(
	USER_NO     NUMBER CONSTRAINT TB_USER_PK PRIMARY KEY,
	USER_ID     VARCHAR2(30) NOT NULL,
	USER_PW     VARCHAR2(30) NOT NULL,
	USER_NAME   VARCHAR2(30) NOT NULL,
	ENROLL_DATE DATE DEFAULT CURRENT_DATE
);

COMMENT ON COLUMN TB_USER.USER_NO     IS '사용자 번호';
COMMENT ON COLUMN TB_USER.USER_ID     IS '사용자 아이디';
COMMENT ON COLUMN TB_USER.USER_PW     IS '사용자 비밀번호';
COMMENT ON COLUMN TB_USER.USER_NAME   IS '사용자 이름';
COMMENT ON COLUMN TB_USER.ENROLL_DATE IS '사용자 가입일';


-- USER_NO 컬럼에 삽입될 시퀀스 생성
CREATE SEQUENCE SEQ_USER_NO NOCACHE;

-- 샘플 데이터 INSERT
INSERT INTO TB_USER 
VALUES(SEQ_USER_NO.NEXTVAL, 
	'user01', 'pass01', '유저일', DEFAULT );

SELECT * FROM TB_USER;
*/