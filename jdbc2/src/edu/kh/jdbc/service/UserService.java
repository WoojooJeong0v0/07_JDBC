package edu.kh.jdbc.service;

// import static 
// 지정된 경로에 존재하는  static 구문을 모두 얻어와서 
// 클래스명.메서드명()이 아닌 메서드명() 만 작성해도 호출 가능
import static edu.kh.jdbc.common.JDBCTemplate.*; // static을 가져 오겠다!


import java.sql.Connection;
import java.util.List;

import edu.kh.jdbc.common.JDBCTemplate;
import edu.kh.jdbc.dao.UserDao;
import edu.kh.jdbc.dto.User;

// Service : 비즈니스 로직 처리
// - DB에 CRUD 후 결과 반환 받기 (서비스가 시키는 것) -> dao를 호출해서 진행
//   + DML 성공 여부에 따른 트랜잭션 제어 처리를 서비스가 해야 함! (commit / rollback)
//         --> commit/rollback에는 Connection 객체가 필요하기 때문에
//             Connection 객체를 Service에서 생성 후
// 			   Dao에 전달하는 형식의 코드를 작성하게 됨

public class UserService {

	//필드
	private UserDao dao = new UserDao();
	
	// 메서드
	/**
	 * 전달 받은 아이디와 일치하는 User 정보 반환 (DTO인 User에서 받아옴)
	 * @param input 입력된 아이디
	 * @return 아이디가 일치하는 회원 정보, 없으면 null 반환
	 */
	public User selectId(String input) {
		
		// Connection 생성
		Connection conn = JDBCTemplate.getConnection();
		
		// Dao 메서드 호출 후 결과 반환 받기
		// (Dao 작성 후 다시 작성)
		User user = dao.selectId(conn, input);
		
		
		// 다쓴 커넥션 닫기
		JDBCTemplate.close(conn);
		
		return user; // DB 조회 결과 반환
	}

	
	/**
	 * User 등록 서비스
	 * @param user : 입력 받은 id, pw, name
	 * @return 삽입 성공한 결과 행의 개수 반환
	 * @throws Exception 
	 */
	public int insertUser(User user) throws Exception {
		
		// service 작성 순서 
		// 1. Connection 생성 -> import static 구문 작성함
		Connection conn = getConnection();
		
		// 2. 데이터 가공 (할 게 없으면 생략 가능)
		
		// 3. 이제 데이터베이스에 접근할 DAO 메서드(INSERT 수행) 호출 후 결과 반환 받기
		 int result = dao.insertUser(conn, user);
		 
		// 4. INSERT 수행 결과에 따라 트랜잭션 제어 처리
		 if (result > 0) { // 삽입 성공
			 commit(conn);
		 } else { // 삽입 실패
			 rollback(conn);
		 }
		 
		 // 5. Connection 반환하기
		 close(conn);
		 
		 // 6. 결과 반환
		 
		return result;
	}


	
	/** User 전체 조회
	 * @return userList : 조회된 User가 담긴 List
	 * @throws Exception
	 */
	public List<User> selectAll() throws Exception {
		
		 // 1. Connection 생성
		Connection conn = getConnection();
		
		// 2. 데이터 가공 (없으면 생략)
		
		// 3. Dao 메서드(SELECT) 호출 후 결과 반환 받기
		// 결과 (List<User>) 반환받기
		List<User> userList = dao.selectAll(conn); 
		
		// 4. DML인 경우 트랜잭션 처리
		// SELECT는 안 해도 됨!
		
		// 5. Connection 반환
		close(conn);
		
		// 6. 결과 반환
		
		return userList;
	}


	/**
	 * User 이름에 검색어 입력
	 * @param keyword
	 * @return
	 * @throws Exception 
	 */
	public List<User> selectName(String keyword) throws Exception {
		
		Connection conn = getConnection();
		// JDB 연결 객체     JDBC 작성 객체
		
		List<User> searchList = dao.keywordSearch(conn, keyword);
		
		close(conn);
		
		
		return searchList;
	}


	/**
	 * USER_NO 으로 User 검색
	 * @param input
	 * @return
	 * @throws Exception
	 */
	public User selectUser(int input) throws Exception {
		
		Connection conn = getConnection();
		
		User user = dao.selectUser(conn, input);
		
		close(conn);
		
		return null;
	}


	
	/**
	 * User delete
	 * @param input
	 * @return result
	 * @throws Exception
	 */
	public int deleteUser(int input) throws Exception {
		Connection conn = getConnection();
		
		
		// Dao 메서드 delete 호출
		// 결과 (삭제된 행의 개수, int) 반환 받기
		int result = dao.deleteUser(conn, input);
		
		// 결과에 따라 트랜잭션 제어 처리하기 (DML은 바로 수행 안 됨)
		if (result > 0) {
			commit(conn);
		} else {
			rollback(conn);
		}
		
		close(conn);
		
		return result;
	}


	
	/**
	 * id, pw  일치하는 user의 USER_NO (사용자번호) 조회
	 * @param userId
	 * @param userPw
	 * @return
	 * @throws Exception
	 */
	public int selectUserNo(String userId, String userPw) throws Exception {
		
		Connection conn = getConnection(); // 커넥션 생성
		
		// dao 호출 후 결과 반환 받기
		int userNo = dao.selectUser(conn, userId, userPw);
		
		close(conn); // 커넥션 반환
		
		return userNo;
	}


	/**
	 * 위의 일치하는 유저 조회해서 이름 수정
	 * @param userNo
	 * @param editName
	 * @return
	 * @throws Exception
	 */
	public int updateName(int userNo, String editName) throws Exception {
		
		Connection conn = getConnection();
		int result = dao.updateName(conn, userNo, editName);
		
		// 트랜잭션 제어 해야 함!
		if (result > 0) commit(conn);
		else 			rollback(conn);
		
		close(conn);
		
		return result;
	}

	
	/**
	 * 7. 아이디 중복 확인
	 * @param userId
	 * @return count
	 * @throws Exception
	 */
	public int idCheck(String userId) throws Exception {
		
		Connection conn = getConnection();
		
		int count = dao.idCheck(conn, userId);
		
		close(conn);
		
		return count;
	}


	
	/**
	 * 8 userList 에 있는 모든 user insert 하기
	 * @param userList
	 * @return result  삽입된 행의 개수 반환
	 * @throws Exception
	 */
	public int multiInsertUser(List<User> userList) throws Exception {
		
		Connection conn = getConnection();
		
		// 다중 Insert 방법 
		// 1) SQL 이용 (속도가 빠름)
		// 2) Java 반복문 이용한 다중 Insert방법 < 이거 쓸 거야
		
		int count = 0; // 삽입 성공한 행의 개수 count
		
		// 1행씩 삽입
		for(User user : userList) {
			int result = dao.insertUser(conn, user);
			count += result; // 삽입 성공한 행의 개수를 count 누적
		}
		
		// 전체 삽입 성공 시 commit / 아니면 rollback
		if (count == userList.size()) {
			commit(conn);
		} else {
			rollback(conn);
		}
		
		close(conn);
		
		return count;
	}
	
	
}
