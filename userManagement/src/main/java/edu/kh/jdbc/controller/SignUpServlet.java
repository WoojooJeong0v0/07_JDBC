	package edu.kh.jdbc.controller;

import java.io.IOException;

import edu.kh.jdbc.dto.User;
import edu.kh.jdbc.service.UserService;
import edu.kh.jdbc.service.UserServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/signUp")
public class SignUpServlet extends HttpServlet {
	
	
	// 사용자 등록 페이지로 전환하는 doGET
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		//  요청 위임
		String path = "/WEB-INF/views/signUp.jsp";
		req.getRequestDispatcher(path).forward(req, resp);
	}
	
	// 사용자 등록(실제 사용자를 등록) 하는 doPOST
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		// 제출된 파라미터 얻어 오기
		//              제출된 input의 name 속성 값
		String userId = req.getParameter("userId");
		String userPw = req.getParameter("userPw");
		String userName = req.getParameter("userName");
		
		try {
			
			// 전달 받은 파라미터를 한 번에 저장할 User DTO 객체 생성
			
			User user = new User();
			user.setUserId(userId);
			user.setUserPw(userPw);
			user.setUserName(userName);
			
			// 서비스 호출 INSERT 후 결과 int 삽입된 행의개수 반환 받기
			UserService service = new UserServiceImpl();
			
			int result = service.insertUser(user);
			
			// 결과에 따라 응답 방법 처리
			String message = null;
			
			if (result > 0) message = userId + " 사용자 등록 성공";
			else 			message = "등록 실패";
			
			// page, request, session, application 중
			// session 이용해서 message 값 전달 (폐기되지 않도록)
			HttpSession session = req.getSession();
			
			session.setAttribute("message", message);
			 
			// 메인 페이지로 리다이렉트(재요청) 
			// 리다이렉트하면 기존 리퀘랑 리스폰이 폐기됨
			resp.sendRedirect("/");
			
			// / 로 매핑하면 index로 보통 많이 가는 편
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	
		
	}
	
	
}
