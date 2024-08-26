package edu.kh.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

public class JDBCExample4 {
	
	public static void main(String[] args) {
		
		
		// 부서명을 입력받아 
		// 해당 부서에 근무하는 사원의
		// 사번 이름 부서명 직급명 
		// 직급코드 오름차순 조회
		
		Connection conn = null; // DB연결정보 저장 객체
		Statement stmt = null; // SQL 수행, 결과반환용 객체
		ResultSet rs = null; // SELECT 수행 결과 저장 객체
		
		Scanner sc = new Scanner (System.in);
		
		try {
			
			Class.forName("oracle.jdbc.driver.OracleDriver"); 
			
			String type = "jdbc:oracle:thin:@"; // 드라이버 종류, Oracle thin 타입(간단한 연결)
			
			String host = "localhost"; // DB 서버 컴퓨터의 IP 또는 도메인 주소 입력
			 							// localhost  == 현재 컴퓨터 
			
			String port = ":1521"; // 포트번호 == 프로그램(연결을 위한) 구분 번호
			
			String dbName = ":XE"; // DBMS 이름 (XE = eXpress Edition)
			
			String userName = "KH_JSH"; // 사용자 계정명
			
			String password = "KH1234"; // 계정 비밀번호
			
			
			conn = DriverManager.getConnection(type + host +  port + dbName, userName, password);
			
			System.out.print("부서명 : ");
			String input = sc.nextLine();
			
			String sql = """
					SELECT EMP_ID, EMP_NAME, DEPT_TITLE, JOB_NAME
					FROM EMPLOYEE e
					JOIN JOB j ON e.JOB_CODE = j.JOB_CODE
					JOIN DEPARTMENT d ON e.DEPT_CODE = d.DEPT_ID
					WHERE DEPT_TITLE = '"""
					+ input + "'" + 
					" ORDER BY e.JOB_CODE ASC";
			
			stmt = conn.createStatement();
			
			rs = stmt.executeQuery(sql);
			
			boolean flag = true; // 조회결과가 없으면 true, 있으면 false
			while(rs.next()) {
				flag = false;
				
				String empId = rs.getString("EMP_ID");
				String empName = rs.getString("EMP_NAME");
				String deptTitle = rs.getString("DEPT_TITLE");
				String jobName = rs.getString("JOB_NAME");
				
				System.out.printf("%s / %s / %s / %s \n", empId, empName, deptTitle, jobName);
			}
			
			if (flag) { // flag가 true면 == while 문이 수행된 적 없음
				System.out.println("일치하는 부서가 없습니다");
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null) rs.close();
				if (stmt != null) stmt.close();
				if (conn != null) conn.close();
				if (sc != null) sc.close();
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		
		
	} // main end
	
	/* 강사님 코드
	 * package edu.kh.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

public class JDBCExample4 {
	public static void main(String[] args) {
		
		// 부서명을 입력 받아 
		// 해당 부서에 근무하는 사원의
		// 사번, 이름, 부서명, 직급명을 
		// 직급코드 오름차순으로 조회
		
		// hint : SQL에서 문자열은 양쪽에 ''(홑따옴표) 필요
		// ex) 총무부 입력 => '총무부'
		
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		
		try {
			
			Class.forName("oracle.jdbc.driver.OracleDriver");
			
			// url == type + host + port + dbName
			String url = "jdbc:oracle:thin:@localhost:1521:XE";
			String userName = "KH_BDH";
			String password = "KH1234";
			
			conn = DriverManager.getConnection(url, userName, password);
			
			Scanner sc = new Scanner(System.in);
			
			System.out.print("부서명 입력 : ");
			String input = sc.nextLine();
			
			String sql = """
				SELECT 
					EMP_ID, 
					EMP_NAME, 
					NVL(DEPT_TITLE, '없음') DEPT_TITLE, 
					JOB_NAME
				FROM EMPLOYEE
				JOIN JOB USING(JOB_CODE)
				LEFT JOIN DEPARTMENT ON (DEPT_CODE = DEPT_ID)
				WHERE DEPT_TITLE = '""" + input +
				"' ORDER BY JOB_CODE ASC";
			
			
//			String sql = """
//					SELECT 
//					EMP_ID, 
//					EMP_NAME, 
//					NVL(DEPT_TITLE, '없음') DEPT_TITLE, 
//					JOB_NAME
//				FROM EMPLOYEE
//				JOIN JOB USING(JOB_CODE)
//				LEFT JOIN DEPARTMENT ON (DEPT_CODE = DEPT_ID)""";
//			
//			sql += String.format(" WHERE DEPT_TITLE = '%s' "
//							   + " ORDER BY JOB_CODE ASC", input);
//			
			
			stmt = conn.createStatement();
			
			rs = stmt.executeQuery(sql);
			
			boolean flag = true; 
			// 조회 결과가 없으면 true, 있으면 false
			
			while(rs.next()) {
				flag = false;
				
				String empId     = rs.getString("EMP_ID");
				String empName   = rs.getString("EMP_NAME");
				String deptTitle = rs.getString("DEPT_TITLE");
				String jobName   = rs.getString("JOB_NAME");
				
				System.out.printf(
					"%s / %s / %s / %s \n",
					empId, empName, deptTitle, jobName);
			}
			
			
			if(flag) { // flag == true == while문이 수행된 적 없음
				System.out.println("일치하는 부서가 없습니다");
			}
			
			
		}catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(rs   != null) rs.close();
				if(stmt != null) stmt.close();
				if(conn != null) conn.close();
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}		
		
		
	}
}
	 * 
	 * */

}
