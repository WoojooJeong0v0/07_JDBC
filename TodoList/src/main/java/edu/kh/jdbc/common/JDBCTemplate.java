package edu.kh.jdbc.common;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

/**
 * Template : 양식, 주형, 틀
 * 미리 만들어둔 양식
 * 
 * JDBCTemplate : JDBC 작업을 위한 코드를 미리 작성해서 제공하는 클래스
 * 
 *  - getConnection() + AutoCommit false 
 *  - commit() / rollback() 
 *  - 각종 close() 구문
 *  
 *  **** 중요 *****
 *  어디서든 JDBCTemplate 클래스를 
 *  객체로 만들지 않고도 메서드를 사용할 수 있도록 함
 *  
 *  클래스 : 객체의 속성과 기능이 정의 되어 있는 설계도
 *  객체 : 설계도를 올려서 가상세계에서 만듬, 직접 사용 함
 *  
 *  객체로 만들지 않았는데 호출한다? 
 *  
 *  모든 메서드를 public static 으로 선언!! (동적 메모리 위치)
 */
public class JDBCTemplate {

	// 필드 
	private static Connection conn = null; 
	// static 메서드에서 사용 가능한 필드는 static 필드만 가능
	// 프로그램 실행 되자마자 static에 conn 변수가 딱 하나만 생성됨
	
	// 메서드
	/**
	 * 호출 시  Connection 객체를 생성해서 반환하는 메서드
	 * @return conn (==Connection 객체)
	 */
	public static Connection getConnection() {
		
		try {
			
			// 이전에 참조하던 Connection 객체가 존재하고 아직 close된 상태가 아니라면
			if (conn != null && !conn.isClosed()) {
				return conn; // 새로 만들지 않고 기존 Connection 을 반환
			}
			
		
			// driver.xml 파일 내용 읽어오기
			// 1. Properties 객체 생성
			// - Map이 자식 클래스
			// - K, V 모두  String 타입 
			// - xml 파일 입출력 쉽게 하는 메서드 제공
			
			Properties prop = new Properties();
			
			// 2. Properties 메서드를 이용해서
			// driver.xml 파일 내용 읽어와 prop에 저장
			String filePath = 
					JDBCTemplate.class.getResource("/edu/kh/jdbc/sql/driver.xml").getPath();
					// 다이나믹웹프로젝트 할 때 폴더 구조가 달라져서 경로 수정해야 함!!
					// 쉬운 설명 : 빌드(코드를 실행 가능한 상태로 만드는 것) 시
					// 컴파일 된 JDBCTemplate.class 파일의 위치에서 (class는 2진법)
					// /edu/kh/jdbc/sql/driver.xml 파일을 찾아서
					// 실제 경로를 얻어와서 반환하겠다는 뜻
			
			prop.loadFromXML(new FileInputStream(filePath)); // filePath 을 읽어와서 (inputStream) from XML 에 적재해서 prop에 넣겠다 
			// prop에 저장된 값 (driver.xml 에서 읽어온 값)을 이용해 
			// Connection 객체 생성하기
			
			// connection 생성
			// prop.getProperty("KEY") String 으로 KEY값 ; KEY가 일치하는 Value 반환
			Class.forName(prop.getProperty("driver"));
		
			String url = prop.getProperty("url");
			String userName = prop.getProperty("userName");
			String password = prop.getProperty("password");
						
			conn = DriverManager.getConnection(url, userName, password);
			
			// 만들어진 Connection에서 AutoCommit 끄기
			conn.setAutoCommit(false);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return conn;
	}
	
	// -------------------------------------------------------------------
	
	/* 트랜잭션 제어 처리 메서드 commit, rollback */ 
	
	/**
	 * 전달 받은 커넥션에서 수행한 SQL을 COMMIT하는 메서드
	 * @param conn
	 */
	public static void commit(Connection conn) {
		try {
			if (conn != null && !conn.isClosed()) conn.commit(); // 멀쩡한 connection 있으면 commit
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	// ----------------------------------------------------------	
	
	/**
	 * 전달 받은 커넥션에서 수행한 SQL을 ROLLBACK하는 메서드
	 * @param conn
	 */
	public static void rollback(Connection conn) {
		try {
			if (conn != null && !conn.isClosed()) conn.rollback();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	// ----------------------------------------------------------
	
	/**
	 * 전달 받은 커넥션을 close(자원 반환) 하는 메서드
	 * @param conn
	 */
	public static void close(Connection conn) {
		try {
			if (conn != null && !conn.isClosed()) conn.close(); 
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	// ----------------------------------------------------------
	
	/**
	 * 전달 받은 Statement를 close(자원 반환) 하는 메서드
	 * + PreparedStatment 도 close 처리 가능!!
	 *  왜? ->> PreparedStatement가 Statement의 자식이기 때문에!! (다형성 업캐스팅)
	 * @param stmt
	 */
	public static void close(Statement stmt) { // 오버로딩으로 가능함 
		try {
			if (stmt != null && !stmt.isClosed()) stmt.close();  //  동적바인딩에 의해서 pstmt 도 close
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	// ----------------------------------------------------------
	
	/**
	 * 전달 받은 ResultSet을 close(자원 반환) 하는 메서드
	 * @param rs
	 */
	public static void close(ResultSet rs) {
		try {
			if (rs != null && !rs.isClosed()) rs.close(); 
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
}
