package edu.kh.todolist.service;

import static edu.kh.jdbc.common.JDBCTemplate.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.kh.todolist.dao.TodoListDao;
import edu.kh.todolist.dao.TodoListDaoImpl;
import edu.kh.todolist.dto.Todo;

public class TodoListServiceImpl implements TodoListService{

	private TodoListDao dao = null;
	
	// 기본 생성자
	public TodoListServiceImpl() throws FileNotFoundException, IOException, ClassNotFoundException {
		// 객체 생성 시 TodoListDAO 객체 생성
		dao = new TodoListDaoImpl();
	}
	
	
	@Override
	public Map<String, Object> todoListFullView() throws Exception {
		
		Connection conn = getConnection();
		
		// 할 일 목록 얻어오기 
		List<Todo> todoList = dao.selectAll(conn);
		
		
		// 완료된 할 일 개수 카운트
		int completeCount = 0;

		for(Todo todo : todoList) {
			if(todo.getComplete().equals("O")) { // get 사용해서 complete 된 값을 가져오고 equals 로 문자열 O와 비교
				completeCount++;
			}
		}
		
		// 메서드에서 반환은 하나의 값 또는 객체 밖에 할 수 없기 때문에
		// Map이라는 컬렉션을 이용해 여러 값을 한 번에 담아서 반환
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("todoList", todoList);
		map.put("completeCount", completeCount);
		
		return map;
	}




	/**
	 * 디테일 콘텐츠 보기
	 */
	@Override
	public Todo todoDetailView(int todoNo) throws Exception {
		
		Connection conn = getConnection();
		
		Todo todo = dao.todoDetailView(conn, todoNo);
		
		return todo;
	}


	@Override
	public int todoAdd(String title, String detail) throws FileNotFoundException, IOException {
		
		
		
		return 0;
	}


	@Override
	public boolean todoComplete(int index) throws FileNotFoundException, IOException {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public boolean todoUpdate(int index, String title, String detail) throws FileNotFoundException, IOException {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public String todoDelete(int index) throws FileNotFoundException, IOException {
		// TODO Auto-generated method stub
		return null;
	}
	
	
}
