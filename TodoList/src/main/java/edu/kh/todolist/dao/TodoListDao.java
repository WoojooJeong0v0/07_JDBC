package edu.kh.todolist.dao;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.util.List;

import edu.kh.todolist.dto.Todo;

public interface TodoListDao {
	// public abstract를 명시하지 않아도 public abstract으로 해석됨!!

	
	/**
	 * todo 전체 보기
	 * @param conn
	 * @return
	 * @throws Exception
	 */
	List<Todo> selectAll(Connection conn) throws Exception;

	Todo todoDetailView(Connection conn, int todoNo) throws Exception;
}
