package edu.kh.jdbc;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class JDBCExample1 {

	public static void main(String[] args) {
		
		
		// 실행 버튼 Ctrl+F11 
		
		
	/*
	 * JDBC (Java DataBase Connectivity)
	 * - Java 에서 DB 연결(접근)할 수 있게 해주는
	 *  Java API (Java에서 제공하는 코드) 
	 *  -> java.sql 패키지에 존재함
	 * 
	 * */	
		
		
		// java 코드를 이용해서
		// EMPLOYEE 테이블의
		// 사번, 이름, 부서코드, 직급코드, 급여, 입사일 조회
		// 이클립스 콘솔에 출력하기
		
		/*
		 * 1. JDBC 객체 참조용 변수 선언 */
		// java.sql.Connetion
		// 특정 DBMS와 연결하기 위한 정보를 저장한 객체
		// == DBeaver에서 사용하는 DB연결 (계정 접속) 역할의 객체
		// (DB 서버 주소, 포트번호, DB이름, 계정명, 비밀번호 를 작성)
		
		Connection conn = null;
		
		// 4번 하기 전에 작성
		Statement stmt = null;
		// java.sql.Statement  (보고서, 진술서, 보고서, 신고한다는 뜻)
		// 1) SQL을 DB에 전달하는 역할
		// 2) DB에서 SQL 실행 결과를 반환받음
		
		// 5번 하기 전 작성
		ResultSet rs = null;
		// - SELECT 조회 결과 저장 객체
		
		try {
			
			/* 2. java.DriverManager 객체 이용해서 Connection 객체 생성하기 */
			// java.sql.DriverManager
			// - DB연결 정보와 JDBC드라이버를 이용해서
			// 원하는 DB와 연결할 수 있는 Connection 객체 생성하는 객체 
			// (객체를 만들기 위한 객체)
//			DriverManager 
			
			
			/*  2-1) Oracle JDBC Driver 객체를 메모리에 로드(적재)하기  */
			Class.forName("oracle.jdbc.driver.OracleDriver"); // catch 문 추가
			// Class.forname("패키지명+클래스명")
			// - 해당 클래스를 읽어 메모리에 적재
			//  -> JVM이 프로그램 동작에 사용할 객체를 생성하는 구문
			
			// "oracle.jdbc.driver.OracleDriver"
			// - Oracle DBMS 연결 시 필요한 코드가 담긴 클래스
			// Oracle 이 제작해서 준 클래스
			

			/*  2-2) DB 연결 정보 작성  */
			String type = "jdbc:oracle:thin:@"; // 드라이버 종류, Oracle thin 타입(간단한 연결)
			
			String host = "localhost"; // DB 서버 컴퓨터의 IP 또는 도메인 주소 입력
			 							// localhost  == 현재 컴퓨터 
			
			String port = ":1521"; // 포트번호 == 프로그램(연결을 위한) 구분 번호
			
			String dbName = ":XE"; // DBMS 이름 (XE = eXpress Edition)
			
			String userName = "KH_JSH"; // 사용자 계정명
			
			String password = "KH1234"; // 계정 비밀번호
 			
			
			/*  2-3) DB 연결 정보와 DriverManager 이용 해서 connection 객체 생성 */
			conn = DriverManager.getConnection(type + host + port + dbName, userName, password);

			
			// Connection 객체 잘 생성되었는지 확인
			// == DB 연결 정보 오타 없는지 확인
			
			System.out.println(conn);
			
			/*  3. SQL 작성  */
			// 주의사항!!!
			// -> jdbc 코드에서 SQL 작성 시 세미콜론을 작성하면 안 된다!!
			//  --> "SQL 명령어가 올바르게 종료되지 않았습니다" 라는 예외 발생
			String sql = "SELECT " +
			"EMP_ID, EMP_NAME, DEPT_CODE, JOB_CODE, SALARY, HIRE_DATE " + "FROM EMPLOYEE"; // 줄 바꾸고 싶은 곳 뒤에 띄어쓰기 꼭 하나 
			// 어차피 JVM은 SQL 하나밖에 못함
			
			/* 4. Statement 객체 생성 */
			// 하기 전에 잠깐 1번으로 돌아가기
			// stmt 선언 후 돌아오기
			
			stmt = conn.createStatement();
			// 연결된 DB(Connection)에 SQL을 전달하고 결과를 반환 받을 Statement 객체 생성
		
			
			/* 5. Statement 객체 이용해서 SQL 수행 후 결과 반환 받기 */
			// 1) Result Set Statement.executeQuery(sql);
			// SELECT 실행할 때 쓰는 것 
			// 결과로 result set  == java.sql.ResultSet 반환
			
			// 2) int Statement.executeUpdate(sql);
			// DML ( INSERT, UPDATE, DELETE ) 등 (숫자가 반환됨)
			// 결과로 int 반환 (삽입, 수정, 삭제 된 행의 개수)
			
			 rs = stmt.executeQuery(sql);
			// 다시 1번 가서 result set 변수선언
			// 선언되면, 변수명 rs =  < 다시 돌아와서 작성
			 
			
			 /* 6. 조회 결과가 담긴 Result set 을 
			  * 커서(Cursor)를 이용해 1행씩 접근해
			  * 각 행에 작성된 컬럼값 얻어오기 */
			 
			 // rs.next() : 커서를 다음 행으로 이동 시킨 후 
			 			//  이동된 행에 값이 있으면 true, 없으면 false 반환
			 			//  맨 처음 호출 시 1행부터 시작
			 
			 while (rs.next()) {
					// 200	선동일	D9	J1	8000000	2000-02-06 00:00:00.000
				 	
				 // re.get자료형(컬럼명 | 순서);  | 는 또는 이란 뜻
				 // - 현재 행에서 지정된 컬럼 값을 얻어와 반환
				 //  -> 지정된 자료형 형태로 값이 반환됨
				 //  (자료형을 잘못 지정하면 예외 발생)
				 
					// [java]           [db]
					// String           CHAR, VARCHAR2
					// int, long        NUMBER (정수만 저장된 컬럼)
					// float, double    NUMBER (정수 + 실수)
					// java.sql.Date    DATE
				 
				 String empId = rs.getString("EMP_ID");
				 String empName = rs.getString("EMP_NAME");
				 String deptCode = rs.getString("DEPT_CODE");
				 String jobCode = rs.getString("JOB_CODE");
				 
				 int salary = rs.getInt("SALARY");
				 
				 Date hireDate =  rs.getDate("HIRE_DATE");
				 
				 System.out.printf(
						 "사번: %s / 이름: %s /  부서코드: %s / 직급코드: %s / 급여: %d / 입사일: %s\n", 
						 empId, empName, deptCode, jobCode, salary, hireDate.toString()); // toString overriding 
				 
			 }
			
			
			
		} catch (SQLException e) {
			// SQLException : DB연결과 관련된 예외 최상위 부모
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			/* 7. 사용 완료된 JDBC 객체 자원 반환 */
			//  -> 수행하지 않으면 DB와 연결된 Connection이 남아있어서
			//     다른 클라이언트가 추가적으로 연결되지 못하는 문제 발생할 수 있음
			// (장기적으로 보면 계속 메모리용량을 차지하여 속도를 느리게 함)
			
			try {
				/* 만들어진 역순으로 close 수행 추천 */
				if (rs != null) rs.close();
				if (stmt != null) stmt.close();
				if (conn != null) conn.close();
				
				// if 문은 NullPointerException 방지용 구문
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		}
		
		
	}
	
}
