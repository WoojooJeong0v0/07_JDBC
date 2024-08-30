package edu.kh.jdbc.service;

// 지정된 클래스의 static 메서드 모두 얻어와 사용하겠다 !
import static edu.kh.jdbc.common.JDBCTemplate.*;

import java.sql.Connection;
import java.util.List;

import edu.kh.jdbc.common.JDBCTemplate;
import edu.kh.jdbc.dao.UserDao;
import edu.kh.jdbc.dao.UserDaoImpl;
import edu.kh.jdbc.dto.User;

public class UserServiceImpl implements UserService {
	
	// 필드에 dao 만들어두기
	private UserDao dao = new UserDaoImpl();

	@Override
	public int insertUser(User user) throws Exception {
		
		// 1. Connection 생성		
		Connection conn = getConnection(); // JDBC에서 얻어와서 씀!
		
		// 2. 데이터 가공
		
		// 3. DAO 메서드 호출 후 결과 반환
		int result = dao.insertUser(conn, user);
		
		// 4. DML 수행 시 트랜잭션 처리
		if(result > 0) commit(conn);
		else 			rollback(conn);
		
		// 5. 사용 완료된 Connection 반환
		close(conn);
		
		// 결과 반환
		return result;
	}

	// 중복 체크
	@Override
	public int idCheck(String userId) throws Exception {
		
		Connection conn = getConnection();
		int result = dao.idCheck(conn, userId);
		close(conn);
		
		return result;
	}

	@Override
	public User login(String userId, String userPw) throws Exception {
		
		Connection conn = getConnection();
		
		// dao 메서드 호출 후 결과 반환 
		User loginUser = dao.login(conn, userId, userPw);
		
		close(conn);
		
		return loginUser;
	}

	@Override
	public List<User> selectAll() throws Exception {
		
		Connection conn = getConnection();
		List<User> userList = dao.selectAll(conn);
		
		close(conn);
		
		return userList;
	}

	@Override
	public List<User> search(String searchId) throws Exception {
		
		// 커넥션 생성
		Connection conn = getConnection();
		
		// 데이터 가공처리
		// 가공 진행 (없으면 생략)
		searchId = '%' + searchId + '%'; // %검색어% 형태로 가공
		
		// DAO 호출 후 결과 반환 받기
		List<User> userList = dao.search(conn, searchId);
		
		close(conn);
		
		return userList;
	}

	@Override
	public User selectUser(String selectUser) throws Exception {
		
		Connection conn = getConnection();
		
		User user = dao.selectUser(conn, selectUser);
		// The method selectUser(Connection, User) in the type UserDao is not applicable for the arguments (Connection, String) 
		// dao유형의 메서드는 인수에 적용할 수 없습니다 ..'ㅡ`?
		// 중간에 String이 User 로 작성되어서 그렇음
		
		
		return user;
	}

	
	@Override
	public int deleteUser(int userNo) throws Exception {
		
		Connection conn = getConnection();
		
		int result = dao.deleteUser(conn, userNo);
		
		// 트랜젝션 제어 처리 DML을 수행했으니까 
		if(result > 0)  commit(conn);
		else			rollback(conn);
		
		close(conn);
		
		return result;
	}
	

	@Override
	public int updateUser(User user) throws Exception {
		
		Connection conn = getConnection();
		
		int result = dao.updateUser(conn, user);
		
		if(result > 0)  commit(conn);
		else			rollback(conn);
		
		close(conn);
		
		return result;
	}
	
	
}
