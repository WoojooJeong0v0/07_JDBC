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

@WebServlet("/updateUser")
public class UpdateUserServlet extends HttpServlet {

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		try {
			int userNo = Integer.parseInt(req.getParameter("userNo"));
			String userPw = req.getParameter("userPw");
			String userName = req.getParameter("userName");
			
			// 매개변수 3개는 많다! 묶자
			// 파라미터 User 객체에 세팅하기
			User user = new User();
			user.setUserNo(userNo);
			user.setUserPw(userPw);
			user.setUserName(userName);
			
			UserService service = new UserServiceImpl();
			
			int result = service.updateUser(user);
			
			// 결과에 따라 출력 메시지 지정
			String message = null;
			
			if(result > 0) message = "수정 되었습니다";
			else           message = "수정할 수 없습니다";
			
			// session scope 에 message 세팅
			req.getSession().setAttribute("message", message); //  get으로 세션 얻어오고 set으로 메시지를 두겠다 ("키값", 값)
			
			// 사용자 상세 페이지로 /selectUser 로 redirect
			resp.sendRedirect("/selectUser?userNo=" + userNo); // get방식요청
		
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
}
