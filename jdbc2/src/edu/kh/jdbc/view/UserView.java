package edu.kh.jdbc.view;

import java.util.Scanner;

import edu.kh.jdbc.dto.User;
import edu.kh.jdbc.service.UserService;

public class UserView {
	
	// 필드
	private UserService service = new UserService();
	private Scanner sc = new Scanner(System.in);
	
	/**
	 * JDBC Template 사용 테스트
	 */
	// 테스트용 메서드
	public void test() {
		// 입력된 ID와 일치하는 USER 정보 조회하기
		System.out.println("ID 입력 : ");
		String input = sc.nextLine();
		
		// 서비스 호출 후 결과 반환 받기
		// 서비스 작성 후 작성 예정 
		
		User user = service.selectId(input);
		
		// 결과 출력
		System.out.println(user);
	}

}
