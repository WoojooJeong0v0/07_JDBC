package edu.kh.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.Scanner;

public class JDBCExample6 {

	public static void main(String[] args) {
		
		// 아이디 비밀번호 입력 받아
		// 아이디 비밀번호 일치하는 사용자(TB_USER) 이름을 수정 UPDATE
		
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		// 준비된 Statement 
		// ? placeholder 에 값을 대입할 준비가 된 statement
		// SQL 간단, 리터럴 표기법, 성능 향상
		
		Scanner sc = new Scanner (System.in);
		
		
		try {
			
			
			// connection 생성
			Class.forName("oracle.jdbc.driver.OracleDriver");
			
			// url == type + host + port + dbName
						String url = "jdbc:oracle:thin:@localhost:1521:XE";
						String userName = "KH_JSH";
						String password = "KH1234";
						
			conn = DriverManager.getConnection(url, userName, password);
			
			
			// AutoCommit 끄기
			conn.setAutoCommit(false);
			
			
			// sql 작성
			
			System.out.print("아이디 입력 : ");
			String id = sc.nextLine();
			
			System.out.print("비밀번호 입력 : ");
			String pw = sc.nextLine();
			
			System.out.print("수정할 이름 입력 : ");
			String name = sc.nextLine();
			
			
			String sql = """
					UPDATE TB_USER
					SET 
					 USER_NAME = ?
					WHERE USER_id = ? 
					AND USER_PW = ?
					""";
			
			// PreparedStatement 객체 생성
			
			pstmt = conn.prepareStatement(sql);
			
			// ?에 알맞은 값 세팅
			pstmt.setString(1, name);
			pstmt.setString(2, id);
			pstmt.setString(3, pw);
			
			int result = pstmt.executeUpdate(); // 매개변수 없음 염두에 두기
			
			if (result > 0) { // insert 성공 시
				System.out.println(id + "님 이름 변경 완료!");
				conn.commit(); // COMMIT 수행 -> DB에 INSERT 반영
			} else {
				System.out.println("아이디 또는 비밀번호 불일치");
				conn.rollback(); // 실패는 무조건 rollback 처리!!
			}
			
			
		} catch (Exception e) {	
			e.printStackTrace();
		} finally {
			try {
				if (pstmt != null) pstmt.close();
				if (conn != null) conn.close();
				if (sc != null) sc.close();
			} catch (Exception e) {	
				e.printStackTrace();
			}
		}
		
	} //main end
	
}
