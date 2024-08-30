package edu.kh.jdbc.dao;

import java.sql.Connection;
import java.util.List;

import edu.kh.jdbc.dto.User;

public interface UserDao {

	/**
	 * 사용자 등록
	 * @param conn
	 * @param user
	 * @return
	 * @throws Exception
	 */
	int insertUser(Connection conn, User user) throws Exception;

	/**
	 * 아이디 중복 검사
	 * @param conn
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	int idCheck(Connection conn, String userId) throws Exception;

	
	/**
	 * login
	 * @param conn
	 * @param userId
	 * @param userPw
	 * @return loginUser
	 * @throws Exception
	 */
	User login(Connection conn, String userId, String userPw) throws Exception;

	List<User> selectAll(Connection conn) throws Exception;

	List<User> search(Connection conn, String searchId) throws Exception;

	User selectUser(Connection conn, String selectUser) throws Exception;

	int deleteUser(Connection conn, int userNo) throws Exception;

	int updateUser(Connection conn, User user) throws Exception;


}
