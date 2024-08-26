package edu.kh.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

public class JDBCExample3 {
	
	public static void main(String[] args) {
		
		// 문제
		// 입력 받은 최소 급여 이상
		// 입력 받은 최대 급여 이하를 받는
		// 사원의 사번, 이름, 급여 급여 내림차순으로 조회
		
		Connection conn = null; // DB연결정보 저장 객체
		Statement stmt = null; // SQL 수행, 결과반환용 객체
		ResultSet rs = null; // SELECT 수행 결과 저장 객체
		
		Scanner sc = new Scanner (System.in);
		
		try {
			
			/* 2-1) Oracle JDBC Driver 객체를 메모리에 로드(적재) 하기*/
			Class.forName("oracle.jdbc.driver.OracleDriver"); // db 정보
			
			/* 2-2) DB 연결 정보 작성 */
			String type = "jdbc:oracle:thin:@"; // 드라이버 종류, Oracle thin 타입(간단한 연결)
			
			String host = "khj-1.xyz"; // DB 서버 컴퓨터의 IP 또는 도메인 주소 입력
			 							// localhost  == 현재 컴퓨터 
			
			String port = ":10000"; // 포트번호 == 프로그램(연결을 위한) 구분 번호
			
			String dbName = ":XE"; // DBMS 이름 (XE = eXpress Edition)
			
			String userName = "KH_COMMON"; // 사용자 계정명
			
			String password = "KH1234"; // 계정 비밀번호
			
			conn = DriverManager.getConnection(type + host + port + dbName, userName, password);
			
			/* sql 작성 */
			System.out.print("최소 급여 : ");
			int min = sc.nextInt();
			
			System.out.print("최대 급여 : ");
			int max = sc.nextInt();
			
			
			String sql = """
					SELECT EMP_ID, EMP_NAME, SALARY
					FROM EMPLOYEE
					WHERE SALARY BETWEEN
							 """
					+ min + " AND " + max 
					+ " ORDER BY SALARY DESC";
			
			stmt = conn.createStatement();
			
			rs = stmt.executeQuery(sql);
			
			// 커서이용 1행씩 접근 컬럼 값 얻기
			
			int count = 0;
			while(rs.next()) {
				count++;
				
				String empId = rs.getString("EMP_ID");
				String empName = rs.getString("EMP_NAME");
				int salary = rs.getInt("SALARY");
				
				System.out.printf("%s / %s / %d \n", empId, empName, salary );
			}
			
			System.out.println("총원 : " + count + " 명");
			
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				// close 사용한 객체자원 반환
				if (rs != null) rs.close();
				if (stmt != null) stmt.close();
				if (conn != null) conn.close();
				if (sc != null) sc.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		
		
		
	} // main end

}
