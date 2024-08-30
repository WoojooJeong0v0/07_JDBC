package edu.kh.jdbc.controller;

import java.io.IOException;
import java.util.List;

import edu.kh.jdbc.dto.User;
import edu.kh.jdbc.service.UserService;
import edu.kh.jdbc.service.UserServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/selectUser")
public class SelectUserServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		try {
			
			String selectUser = req.getParameter("userNo");
			// getParam할 때 받아오는 정보와 같은 이름으로 설정하면 조금 덜 헷갈림
			// 기본적으로 String 으로 얻어옴
			
			UserService service = new UserServiceImpl();
			User user = service.selectUser(selectUser);
			// user 하나니까 리스트일 필요 없는데 뭘 작성해야 하나'ㅡ`?
			// (userNo) 를 받아서 전달해줄 거야 == selectUser
			
			
			// scope에 받아올 애들을 두자
			req.setAttribute("selectUser", user);
			// "selectUser" 는 jsp 와 연결되는 이름(호출과 비슷한 개념)
			
			String path = "WEB-INF/views/selectUser.jsp";
			req.getRequestDispatcher(path).forward(req, resp);
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
}
