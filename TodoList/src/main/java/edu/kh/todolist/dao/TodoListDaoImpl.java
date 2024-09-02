package edu.kh.todolist.dao;
import static edu.kh.jdbc.common.JDBCTemplate.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import edu.kh.jdbc.common.JDBCTemplate;
import edu.kh.todolist.dto.Todo;

public class TodoListDaoImpl implements TodoListDao{
	
//	private List<Todo> todoList = null; 아래 selectAll 에서 사용되어 별도로 필드에 존재하지 않아도 됨
	
	private PreparedStatement pstmt = null;
	private Statement stmt = null;
	private ResultSet rs = null;
	private Properties prop;
	
	public TodoListDaoImpl() {
		
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
	} // 기본생성자 end
	
	@Override
	public List<Todo> selectAll(Connection conn) throws Exception {
		
		List<Todo> todoList = new ArrayList<Todo>(); // 결과 저장용 변수 선언
		
		
		try {
			
			String sql = prop.getProperty("selectAll");
			
			
			stmt = conn.createStatement(); // 읽기 모드 확인
			rs = stmt.executeQuery(sql);
			
			while(rs.next()) {
					int todoNo = rs.getInt("TODO_NO");
					String title = rs.getString("TODO_SUB");
					String detail = rs.getString("TODO_CONTENT");
					String complete = rs.getString("TODO_COMPL");
					String regDate = rs.getString("TODO_DATE");
					
					Todo all = new Todo(todoNo, title, detail, complete, regDate); // 객체로 묶기
					
					todoList.add(all); 
				}
			
		} finally {
			close(rs);
			close(stmt);
		}
		
		return todoList;
	}

	@Override
	public Todo todoDetailView(Connection conn, int todoNo) throws Exception {
		
		Todo todo = null;
		
		try {
			
			String sql = prop.getProperty("detailView");
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, todoNo);
			
			rs = pstmt.executeQuery();
			
			if (rs.next()) {
				int no = rs.getInt("TODO_NO");
				String title = rs.getString("TODO_SUB");
				String complete = rs.getString("TODO_COMPL");
				String regDate = rs.getString("TODO_DATE");
				String detail = rs.getString("TODO_CONTENT");
				
				todo = new Todo(no, title, detail, complete, regDate);
				
			}
			
		} finally {
			close(rs);
			close(pstmt);
		}
		
		return todo;
	}

	
}
