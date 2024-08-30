package edu.kh.jdbc.dao;

//지정된 클래스의 static 메서드 모두 얻어와 사용하겠다 !
import static edu.kh.jdbc.common.JDBCTemplate.*;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import edu.kh.jdbc.common.JDBCTemplate;
import edu.kh.jdbc.dto.User;

public class UserDaoImpl implements UserDao {

	// 필드
	
	
	// JDBC 객체 참조 변수 + Properties 참조 변수 선언
	private Statement stmt = null;
	private PreparedStatement pstmt;
	private ResultSet rs;
	
	private Properties prop;
	// K, V가 모두 String 인 Map, 파일 입출력 쉬움
	
	// 기본 생성자 // 객체 만들 때 생성되는 메서드, 생성될 때 실행되는 메서드 
	public UserDaoImpl() {
		
		// 객체 생성 시 외부에 존재하는 sql.xml 파일 읽어와
		// prop에 저장한다!! 
		
		try {
			String filePath = 
					JDBCTemplate.class.getResource("/edu/kh/jdbc/sql/sql.xml").getPath();
			
			// 지정된 경로의 XML 파일 내용을 읽어와
			// Properties 객체에 K:V 세팅
			prop = new Properties(); // map 
			prop.loadFromXML(new FileInputStream(filePath));
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	/**
	 * 사용자 등록
	 */
	@Override
	public int insertUser(Connection conn, User user) throws Exception {
		
		// 1. 결과 저장용 변수 선언
		int result = 0;
		
		try {
			
			// 2. SQL 작성
			// -> properties 이용 외부 sql.xml 파일에서 읽어온 sql 이용
			String sql = prop.getProperty("insertUser");
			
			// 3. PreparedStatement 생성
			 pstmt = conn.prepareStatement(sql);
			 
			// 4. ? 에 값 세팅
			 pstmt.setString(1, user.getUserId());
			 pstmt.setString(2, user.getUserPw());
			 pstmt.setString(3, user.getUserName());
			 
			// 5. SQL INSERT 수행 후  executeUpdate
			// 결과 반환
			result = pstmt.executeUpdate();
			
			
		} finally {
			// 6 . 사용한 JDBC 객체 자원 반환 close
			close(pstmt);
		}
		
		return result;
	}

	/**
	 * 아이디 중복 확인
	 */
	@Override
	public int idCheck(Connection conn, String userId) throws Exception {
		
		int result = 0;
		
		try {
			
			String sql = prop.getProperty("idCheck");
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, userId);
			rs = pstmt.executeQuery();
			if(rs.next()) { // 1행 결과, if
				result = rs.getInt(1); // 조회결과 1번 컬럼값 얻기
			}
		} finally {
			close(rs);
			close(pstmt);
		}
		
		return result;
	}

	
	/**
	 * login
	 */
	@Override
	public User login(Connection conn, String userId, String userPw) throws Exception {
		
		// 결과 저장용 변수 선언
		User loginUser = null;
		
		try {
			
			String sql = prop.getProperty("login");
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, userId);
			pstmt.setString(2, userPw);
			
			rs = pstmt.executeQuery();
			
			if (rs.next()) {
				int userNo = rs.getInt("USER_NO");
				String id = rs.getString("USER_ID");
				String pw = rs.getString("USER_PW");
				String userName = rs.getString("USER_NAME");
				String enrollDate = rs.getString("ENROLL_DATE");
				
				loginUser = new User(userNo, userId, userPw, userName, enrollDate);
				
			}
			
		} finally {
			close(rs);
			close(pstmt);
		}
		
		return loginUser;
	}

	@Override
	public List<User> selectAll(Connection conn) throws Exception {
		
		List<User> userList = new ArrayList<User>(); // 결과 저장용 변수 선언
		
		
		try {
			
			String sql = prop.getProperty("selectAll");
			
			
			stmt = conn.createStatement(); // 읽기 모드 확인
			rs = stmt.executeQuery(sql);
			
			while(rs.next()) {
					int userNo = rs.getInt("USER_NO");
					String userId = rs.getString("USER_ID");
					String userPw = rs.getString("USER_PW");
					String userName = rs.getString("USER_NAME");
					String enrollDate = rs.getString("ENROLL_DATE");
					
					User user = new User(userNo, userId, userPw, userName, enrollDate); // user 객체로 묶기
					
					userList.add(user); 
				}
			
		} finally {
			close(rs);
			close(stmt);
		}
		
		return userList;
	}

	
	/**
	 * 검색어가 아이디에 포함된 사용자 조회
	 */
	@Override
	public List<User> search(Connection conn, String searchId) throws Exception {
		
		// arrayList 미리 선언하는 이유 
		// == 조회된 결과를 추가 add 해서 한 묶음 반환하기 위해 
		List<User> userList = new ArrayList<User>();
		
		
		try {
					
			String sql = prop.getProperty("search");
			
			
			pstmt = conn.prepareStatement(sql); 
			pstmt.setString(1, searchId);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
					int userNo = rs.getInt("USER_NO");
					String userId = rs.getString("USER_ID");
					String userPw = rs.getString("USER_PW");
					String userName = rs.getString("USER_NAME");
					String enrollDate = rs.getString("ENROLL_DATE");
					
					User user = new User(userNo, userId, userPw, userName, enrollDate); // user 객체로 묶기
					
					userList.add(user); 
				}
			
		} finally {
			close(rs);
			close(pstmt);
		}
		
		
		return userList;
	}

	/**
	 * 한 명 조회
	 */
	@Override
	public User selectUser(Connection conn, String selectUser) throws Exception {
		
		User user = null; // 동일한 지역변수 
		// 으잉 일단 수정하기
		
		try {
			
			String sql = prop.getProperty("selectUser");
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, selectUser);
			
			rs = pstmt.executeQuery();
			
			if (rs.next()) {
				int userNo = rs.getInt("USER_NO");
				String id = rs.getString("USER_ID");
				String pw = rs.getString("USER_PW");
				String userName = rs.getString("USER_NAME");
				String enrollDate = rs.getString("ENROLL_DATE");
				
				user = new User(userNo, id, pw, userName, enrollDate);
				
			}
			
		} finally {
			close(rs);
			close(pstmt);
		}
		
		
		return user;
	}

	@Override
	public int deleteUser(Connection conn, int userNo) throws Exception {
		
		int result = 0;
		
		try {
			
			String sql = prop.getProperty("deleteUser");
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, userNo);
			
			// DML은 executeUpdate() 호출
			
			 result = pstmt.executeUpdate();
			
		} finally {
			close(pstmt);
		}
		
		return result;
	}

	@Override
	public int updateUser(Connection conn, User user) throws Exception {
		
		int result = 0;
		
		try {
			String sql = prop.getProperty("updateUser");
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, user.getUserName()); // user를 객체로 가져왔으니 get으로 꺼내줘야 함
			pstmt.setString(2, user.getUserPw());
			pstmt.setInt(3, user.getUserNo());
			
			result = pstmt.executeUpdate();
			
		} finally {
			close(pstmt);
		}
		
		return result;
	}
	
	
}
