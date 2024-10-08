package edu.kh.jdbc.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import edu.kh.jdbc.dto.User;
import edu.kh.jdbc.service.UserService;
import edu.kh.jdbc.service.UserServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/selectAll")
public class SelectAllServlet extends HttpServlet {
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		try {
			
			UserService service = new UserServiceImpl();
			
			List<User> userList = service.selectAll();
			req.setAttribute("userList", userList); // 정확히 이해 못함..set 하는 부분 8/30 9시 수업 다시 확인
			// 리퀘스트가 응답하기 전에 해당 내용을 담아둬야 함
			// 담아둔 내용을 전달할 문자열을 앞에 작성하고 뒤에 내용도 같이 작성
			String path = "/WEB-INF/views/selectAll.jsp";
			req.getRequestDispatcher(path).forward(req, resp);
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
