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
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
