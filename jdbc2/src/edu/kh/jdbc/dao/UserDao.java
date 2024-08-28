package edu.kh.jdbc.dao;

import java.sql.Connection;

import static edu.kh.jdbc.common.JDBCTemplate.*; // static을 가져 오겠다!
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.sql.Date;

import edu.kh.jdbc.common.JDBCTemplate;
import edu.kh.jdbc.dto.User;
import edu.kh.jdbc.view.UserView;

// Data Access Object : DB나 파일 같은 데이터 저장된 곳에 접근하는 용도의 객체
// -> DB에 접근하여 Java 에서 원하는 결과를 얻기 위해 
// 	SQL 수행하고 결과 받환 받는 역할

public class UserDao {
	
	// 필드 
	// - DB접근 관련한 JDBC 객체 참조형 변수를 미리 선언
	private Statement stmt = null;
	private PreparedStatement pstmt = null;
	private ResultSet rs = null;
	
	/**
	 * 전달 받은 Connection 이용해 DB 접근하여
	 * 전달 받은 아이디와 일치하는 User 정보 조회하기
	 * @param conn : Service에서 생성한 Connection 객체
	 * @param input : View에서 입력 받은 아이디가 넘어오게 해야 함
	 * @return
	 */
	public User selectId(Connection conn, String input) {
		
		User user = null; // 결과 저장용 변수
		
		try {
		
			// SQL 작성
			String sql = "SELECT * FROM TB_USER WHERE USER_ID = ?"; 
			
			// PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			
			// ? placeholder 에 알맞은 값 대입
			pstmt.setString(1, input);
			
			// SQL 수행 후 결과 반환 받기
			rs = pstmt.executeQuery();
			
			// 조회 결과가 있을 경우
			// 중복되는 아이디가 없을 경우 1행만 조회되므로
			// while보다 if 사용이 효과적임
			if (rs.next()) {
				
				// 각 컬럼 값 얻어오기
				int userNo = rs.getInt("USER_NO");
				String userId = rs.getString("USER_ID");
				String userPw = rs.getString("USER_PW");
				String userName = rs.getString("USER_NAME");
				
				// java.sql.Date 활용
				Date enrollDate = rs.getDate("ENROLL_DATE");
				
				// 조회된 컬럼 값 이용해 USer 객체 생성
				user = new User(userNo, userId, userPw, userName, enrollDate.toString());
				
			} // if end (조회 결과가 있을 때에만 실행)
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// JDBC 객체 반환하는 close 구문 작성했었음 
			JDBCTemplate.close(rs);
			JDBCTemplate.close(pstmt);
			
			// Connection 객체는 Service에서 close!!!!
		}
		
		return user; // 맨 위에 만든 결과 저장용 변수, 결과 반환 (생성된 User 또는 null)
		
		
	}

	
	/**
	 * User 등록 DAO 메서드
	 * @param conn : DB 연결 정보가 담겨있는 객체
	 * @param user : 입력 받은 id, pw, name 
	 * @return result (만들어서 반환 시킬 예정) : INSERT 결과 행의 개수 반환
	 * @throws Exception : 발생하는 예외 모두 던짐 (view에서 모아서 처리)
	 */
	public int insertUser(Connection conn, User user) throws Exception {
		
		
		// SQL 수행 중 발생하는 예외를 catch로 처리하지 않고
		// throws를 이용해서 호출부로 던져 처리 예정
		// catch 문이 필요 없음
		
		// dao 에서 해야 할 일
		// 1. 결과 저장용 변수 선언
		int result = 0;
		
		try {
			
			// 2. SQL 작성
			String sql = """
					INSERT INTO TB_USER
					VALUES(SEQ_USER_NO.NEXTVAL, ?, ?, ?, DEFAULT)
					""";
			
			// 3. PreparedStatement 생성
			pstmt = conn.prepareStatement(sql);
			// java 값을 입력받을 준비가 되어 있음
			
			// 4. ? placeholder 에 알맞은 값 대입
			pstmt.setString(1, user.getUserId());
			pstmt.setString(2, user.getUserPw());
			pstmt.setString(3, user.getUserName());
			
			// 5. SQL(INSERT) 수행(executeUpdate) 후 결과 반환 받기
			// 결과 (삽입된 행의 개수, int) 반환 받기
			result = pstmt.executeUpdate();
			
			
		} finally {
			// 6. 사용한 JDBC 객체 자원 반환 close 
			close(pstmt);
		}
		
		// 결과 저장용 변수에 저장된 값 반환
		return result;
	}


	/**
	 * User 전체 조회 DAO 메서드
	 * @param conn
	 * @return userList
	 * @throws Exception
	 */
	public List<User> selectAll(Connection conn) throws Exception {
		
		// 1. 결과 저장용 변수 선언
		// -> List 같은 컬렉션 반환하는 경우
		// 변수 선언 시 객체도 같이 생성해 두자!!!
		// 왜 객체로 만들었는가? 240828 수업 재확인
		List<User> userList = new ArrayList<User>();
		
		try {
			// throws 로 처리해서 catch 문 안 함
			// 2. SQL 작성
			
			String sql = """
					SELECT
						USER_NO,
						USER_ID,
						USER_PW,
						USER_NAME,
						TO_CHAR(ENROLL_DATE, 'YYYY"년" MM"월" DD"일"') ENROLL_DATE
					FROM TB_USER
					ORDER BY USER_NO ASC
					""";
			
			// 3. preparedstatement 생성
			
			pstmt = conn.prepareStatement(sql);
			
			// 4. ? 에 알맞은 값 대입 (생략)
			
			// 5. SQL 수행 (SELECET) (executeQuery)후 결과 (ResultSet) 반환 받기
			rs = pstmt.executeQuery();
			
			// 6. 조회 결과 ResultSet 커서 이용해서 1행씩 접근 후 컬럼값 얻어 오기
			
			/*
			 * 몇 행이 조회될지 모른다 -> while
			 * 무조건 1행 조회된다 -> if 
			 */
			while(rs.next()) { // rs.next() 커서를 1행 이동 시켜 이동된 행 데이터가 있으면 true / 없으면 false
				int userNo = rs.getInt("USER_NO");
				String userId = rs.getString("USER_ID");
				String userPw = rs.getString("USER_PW");
				String userName = rs.getString("USER_NAME");
				String enrollDate = rs.getString("ENROLL_DATE");
				// java.sql.Date 타입으로 값을 저장하지 않는 이유! 
				// -> TO_CHAR() 이용해서 문자열로 변환했기 때문!
				
				// 조회된 값을 userList에 추가 
				// -> User 객체 생성해 조회된 값을 담고
				//  userList에 추가하기
				User user = new User(userNo, userId, userPw, userName, enrollDate);
				
				userList.add(user);
				
				// ResultSet을 List에 옮겨담는 이유 
				// 1. List 사용이 편해서
				// -> 호환되는 곳도 많음 (jsp, thymeleaf 등)
				// 2. 사용된 ResultSet은 Dao에서 close 되기 때문
			}
			
		} finally {
			close(rs);
			close(pstmt);
		}
		
		
		
		// 조회 결과가 담긴 list 반환
		return userList;
	}


	/**
	 * 이름에 검색어 포함된 유저 검색
	 * @param conn
	 * @return
	 * @throws Exception
	 */
	public List<User> keywordSearch(Connection conn, String keyword) throws Exception {
		// 매개변수가 빠져 있어서 추가 해야 함
		// List 미리 만들어 놓자!
		List<User> searchList = new ArrayList<User>();
		
		try {
			
			
			String sql = """
					SELECT
					USER_NO,
					USER_ID,
					USER_PW,
					USER_NAME,
					TO_CHAR(ENROLL_DATE, 'YYYY"년" MM"월" DD"일"') ENROLL_DATE
				FROM TB_USER
				WHERE USER_NAME LIKE '%' || ? || '%'
				ORDER BY USER_NO ASC
					""";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, keyword);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				int userNo = rs.getInt("USER_NO");
				String userId = rs.getString("USER_ID");
				String userPw = rs.getString("USER_PW");
				String userName = rs.getString("USER_NAME");
				String enrollDate = rs.getString("ENROLL_DATE");
				User user = new User(userNo, userId, userPw, userName, enrollDate);
				searchList.add(user);
			}
						
		} finally {
			close(rs);
			close(pstmt);
		}
		
		
		return searchList;
	}


	
	/**
	 * 
	 * @param conn
	 * @param input
	 * @return user
	 * @throws Exception
	 */
	public User selectUser(Connection conn, int input) throws Exception {
		
		User user = null;
		
		try {
			
			String sql = """
					SELECT
						USER_NO,
						USER_ID,
						USER_PW,
						USER_NAME,
						TO_CHAR(ENROLL_DATE, 'YYYY"년" MM"월" DD"일"') ENROLL_DATE
					FROM TB_USER
					WHERE USER_NO = ?
					""";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, input);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				int userNo        = rs.getInt("USER_NO");
				String userId     = rs.getString("USER_ID");
				String userPw     = rs.getString("USER_PW");
				String userName   = rs.getString("USER_NAME");
				String enrollDate = rs.getString("ENROLL_DATE");
				
				user = new User(userNo, userId, 
								userPw, userName, enrollDate);
			}
			
		}finally {
			close(rs);
			close(pstmt);
		}
		
		
		return user;
	}


	public int deleteUser(Connection conn, int input) throws Exception {
		
		int result = 0;
		
		try {
			
			String sql = """
					DELETE 
					FROM TB_USER
					WHERE USER_NO = ?
					""";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, input);
			
			result = pstmt.executeUpdate();
			
			
		} finally {
			close(pstmt);
		}
		
		return result;
	}


	
	/**
	 * 
	 * @param conn
	 * @param userId
	 * @param userPw
	 * @return
	 * @throws Exception
	 */
	public int selectUser(Connection conn, String userId, String userPw) throws Exception {
		
		int userNo = 0;
		
		try {
			
			String sql = """
					SELECT USER_NO
					FROM TB_USER
					WHERE USER_ID = ?
					AND USER_PW = ?
					""";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, userId);
			pstmt.setString(2, userPw);
			
			rs = pstmt.executeQuery();
			
			// 조회된 1행 있을 경우
			if(rs.next()) {
				userNo =  rs.getInt("USER_NO");
			}
			
			
		} finally {
			close(rs);
			close(pstmt);
		}
		
		
		return userNo;
	}


	
	/**
	 * 6 번
	 * @param conn
	 * @param userNo
	 * @param editName
	 * @return
	 * @throws Exception
	 */
	public int updateName(Connection conn, int userNo, String editName) throws Exception {
		
		int result = 0;
		
		try {
			
			String sql = """
					UPDATE TB_USER
					 SET 
					 USER_NAME = ?
					WHERE USER_NO = ?
					""";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, editName);
			pstmt.setInt(2, userNo);
			
			result = pstmt.executeUpdate();
			
		} finally {
			close(pstmt);
		}
		
		return result;
	}


	
	/**
	 * 7.
	 * @param conn
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	public int idCheck(Connection conn, String userId) throws Exception {
		
		int count = 0; // 결과 저장용 변수
		
		try {
			
			String sql = """
					SELECT COUNT(*)
					FROM TB_USER
					WHERE USER_ID = ?
					""";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, userId);
			
			rs = pstmt.executeQuery();
			
			if (rs.next()) { // COUNT(*) 그룹함수 결과가 1행만 조회되기 때문
				count = rs.getInt(1); // 조회된 컬럼순서 이용해서 컬럼값 얻어오기 가능 -> 추천하지는 않음
			}
			
		} finally {
			close(rs);
			close(pstmt);
		}
		
		return count;
	}

}
