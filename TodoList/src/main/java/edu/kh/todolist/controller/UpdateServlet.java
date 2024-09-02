package edu.kh.todolist.controller;

import java.io.IOException;

import edu.kh.todolist.dto.Todo;
import edu.kh.todolist.service.TodoListService;
import edu.kh.todolist.service.TodoListServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/todo/update")
public class UpdateServlet extends HttpServlet {
	
	// 수정 화면 전환 (GET방식 처리 요청)
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		// 전달받은 파라미터 얻어오기
		int index = Integer.parseInt(req.getParameter("index"));
		
		try {
			// 수정 전 제목/내용 조회  == 상세조회
			TodoListService service = new TodoListServiceImpl();
			Todo todo = service.todoDetailView(index);
			
			// request scope 세팅
			req.setAttribute("todo", todo);
			
			// forward할 JSP경로
			String path = "/WEB-INF/views/update.jsp";
			
			// 요청 위임
			req.getRequestDispatcher(path).forward(req, resp);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * 요청 주소가 같아도 데이터 전달 방식 method - GET/POST 이 다르면
	 * 각각의 메서드에서 처리할 수 있다!!
	 */
	
	// doPost 요청처리
	// 할 일 제목/내용 수정
	 @Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		 // 전달받은 파라미터 얻어오기
		 String title = req.getParameter("title");
		 String detail = req.getParameter("detail"); // textarea 값
		 
		 // <input type="hidden" name="index" value="${param.index}">
		 int index = Integer.parseInt(req.getParameter("index"));
		 
		 try {
			TodoListService service = new TodoListServiceImpl();
			boolean result = service.todoUpdate(index, title, detail);
			
			// 수정 성공 시
			// 상세조회 페이지 detail로 redirect 후 "수정되었습니다" alert() 출력
			
			// 수정 실패 시
			// 수정 페이지로 redirect 후 "수정 실패" alert() 출력
			
			String url = null;
			String message = null; // 공통작업 하기 위해 미리 선언
			
			if(result) { // 성공
				url = "/todo/detail?index=" + index;
				message = "수정 되었습니다";
			} else { // 실패
				url = "/todo/update?index=" + index;
				message = "수정 실패";
			}
			
			// session scope 객체에 속성 추가
			req.getSession().setAttribute("message", message);
			
			// Redirect는 GET방식
			resp.sendRedirect(url);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	

}
