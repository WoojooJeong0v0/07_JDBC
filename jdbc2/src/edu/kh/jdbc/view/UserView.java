package edu.kh.jdbc.view;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
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
	
	
	/**
	 * User 관리 프로그램 메인 메뉴
	 */
	public void mainMenu() {
		
		int input = 0;
		
		do {
			
			try {
				
				System.out.println("\n===== User 관리 프로그램 =====\n"); 
				System.out.println("1. User 등록(INSERT)");
				System.out.println("2. User 전체 조회(SELECT)");
				System.out.println("3. User 중 이름에 검색어가 포함된 회원 조회 (SELECT)");
				System.out.println("4. USER_NO를 입력 받아 일치하는 User 조회(SELECT)");
				System.out.println("5. USER_NO를 입력 받아 일치하는 User 삭제(DELETE)");
				System.out.println("6. ID, PW가 일치하는 회원이 있을 경우 이름 수정(UPDATE)");
				System.out.println("7. User 등록(아이디 중복 검사)");
				System.out.println("8. 여러 User 등록하기");
				System.out.println("0. 프로그램 종료");
				
				System.out.print("메뉴 선택 : ");
				input = sc.nextInt();
				sc.nextLine(); // 버퍼에 남은 개행문자 제거
				
				switch(input) {
				case 1 : insertUser(); break;
				case 2 : selectAll(); break;
				case 3 : selectName(); break;
				case 4 : selectUser(); break;
				case 5 : deleteUser(); break;
				case 6 : updateName(); break;
				case 7 : insertUser2(); break;
				case 8 : multiInsertUser(); break;
				case 0 : System.out.println("\n[프로그램 종료]\n"); break;
				default: System.out.println("\n[메뉴 번호만 입력하세요]\n");
				}
				
				System.out.println("------------------------------------");
				
				
			} catch (InputMismatchException e) {
				// scanner에 잘못된 값이 입력 되었을 때
				// 스캐너 이용한 입력 시 자료형 잘못된 경우
				System.out.println("\n***잘못 입력하셨습니다***\n");
				// 잘못 입력해서 while 문 멈추는 걸 방지하기 위함 
				input = -1;
				sc.nextLine(); // 입력 버퍼에 남아있는 잘못된 문자 제거
			} catch (Exception e) {
				// 발생되는 예외 모두 해당 catch 구문으로 모아서 처리 (throws)
				e.printStackTrace();
			}
			
			
		} while(input != 0);
		
	} // mainMenu() end


	/**
	 * 1. User 등록
	 * @throws Exception 
	 */
	private void insertUser() throws Exception {
		System.out.println("\n===User 등록===\n");
		
		System.out.print("ID : ");
		String userId = sc.next();
		
		System.out.print("PW : ");
		String userPw = sc.next();
		
		System.out.print("Name : ");
		String userName = sc.next();
		
		// 입력 받은 값 3개를 한 번에 묶어서 전달
		// User DTO 객체 생성하고 필드에 값 세팅하기
		User user = new User(); // 생성자가 추가적으로 필요하지 않게 매개변수를 덜 씀
		// setter 로 하나씩 묶기 // 롬복 이용
		user.setUserId(userId);
		user.setUserPw(userPw);
		user.setUserName(userName);
		
		// 서비스 호출 후 결과 반환 받기
		// DML 은 int 형으로 반환 받음 ( == 삽입된 행의 개수)
		
		int result = service.insertUser(user);
		
		// 반환된 결과에 따라 출력할 내용 선택
		if (result > 0) {
			System.out.println("\n" + userId + " 사용자가 등록 되었습니다\n");
		} else {
			System.out.println("\n***등록 실패***\n");
		}
		
	}
	
	
	/**
	 * 2. User 전체 조회 (SELECT)
	 */
	private void selectAll() throws Exception {
		System.out.println("\n===User 전체 조회===\n");
		
		// 서비스 메서드 (SELECT) 호출 후 
		// 결과(List<User>) 반환 받기
		List<User> userList = service.selectAll();
		
		// 조회결과 없을 경우
		if (userList.isEmpty()) { // userList 가 비어 있을 경우
			System.out.println("\n***조회 결과가 없습니다\n");
			return;
		}
		
		// 향상된 for 문 
		for (User user : userList) {
			System.out.println(user); // 자동으로 user.toString() 호출
		}
		
	}
	
	
	/**
	 * 3. User 중 이름에 검색어가 포함된 회원 조회
	 * @throws Exception
	 */
	private void selectName() throws Exception {
		System.out.println("\n===User 중 이름 검색어가 포함된 유저 조회 ===\n");
		
		System.out.print("검색어 입력 : ");
		String keyword = sc.nextLine();
		
		// 서비스 SELECT 호출 후
		// 결과(List<User>) 반환 받기
		List<User> searchList = service.selectName(keyword);
		
		if (searchList.isEmpty()) {
			System.out.println("검색 결과 없음");
			return;
		}
		
		for(User user:searchList) {
			System.out.println(user);
		}
	}
	
	
	/**
	 * 4. USER_NO 입력 받아 일치하는 User 조회
	 */
	private void selectUser() throws Exception {
		System.out.println("\n===User NO 입력 받아 일치하는 User 조회 ===\n");
		
		System.out.print("사용자 번호 입력 : ");
		int input = sc.nextInt();
		
		// 사용자 번호 == PK 중복 없다
		// == 일치하는 값이 있다면 1행만 조회 됨
		// 1행의 조회 결과를 담기 위해 사용하는 객체 = User DTO 
		User user = service.selectUser(input);
		
		// 조회 결과 없으면 null 처리, 있으면 !null
		if (user == null) {
			System.out.println("USER_NO 일치하는 회원이 없습니다");
			return;
		} 
		
		System.out.println(user);
	}
	
	
	/**
	 * 5. USER_NO 입력 받아 일치하는 User 삭제
	 */
	private void deleteUser() throws Exception {
		System.out.println("\n===User NO 입력 받아 일치하는 User 삭제 ===\n");
		
		System.out.print("삭제할 사용자 번호 입력 : ");
		int input = sc.nextInt();
		
		// 서비스 DELETE 호출 후 결과 int, 삭제된 행 개수 반환 받기
		int result = service.deleteUser(input);
		
		if (result > 0) System.out.println("삭제 성공");
		else  			System.out.println("사용자 번호 일치하는 User가 존재하지 않습니다");
		
	}
	
	
	/**
	 * 6. id/pw 일치하면 name 수정 
	 * @throws Exception
	 */
	private void updateName() throws Exception {
		System.out.println("\n===ID, PW 일치하는 회원 이름 수정 ===\n");
		
		System.out.print("ID : ");
		String userId = sc.nextLine();
		System.out.print("PW : ");
		String userPw = sc.nextLine();
		
		// 입력 받은 id, pw 일치하는 회원 조회 select 
		//- > user_no조회
		int userNo = service.selectUserNo(userId, userPw);
		
		if (userNo == 0) { // 조회결과 없음
			System.out.println("아이디 또는 비밀번호 일치하는 사용자가 없습니다");
			return;
		}
		
		// 조회된 사용자 번호 있을 경우
		// 수정할 이름 입력
		System.out.print("수정할 이름 입력 : ");
		String editName = sc.nextLine();
		
		// 이름 수정 UPDATE 서비스 호출 후 결과 int 반환 받기
		int result = service.updateName(userNo, editName);
		
		if (result > 0) System.out.println("수정 성공!");
		else 			System.out.println("수정 실패");
		
	}
	
	
	/**
	 * 7.
	 * @throws Exception
	 */
	private void insertUser2() throws Exception {
		System.out.println("\n === User 등록 (아이디 중복 검사) \n");
		
		String userId = null; // 입력된 아이디 저장할 변수
		
		while(true) {
			System.out.print("아이디 입력 : ");
			userId = sc.nextLine();
			
			// 입력 받은 userId 가 중복인지 검사 
			// 검사는 서비스select 호출 후 결과int 반환 받기
			// 중복 == 1 , 아니면 == 0
			
			int count = service.idCheck(userId);
			
			if(count == 0 ) { // 중복 아닌 경우
				System.out.println("사용 가능한 아이디 입니다");
				break;
			}
			
			System.out.println("이미 사용 중인 아이디 입니다. 다시 입력 하세요.");
		}
		
		// 아이디가 중복이 아닌 경우 while 종료 후 pw, name 입력 받기
		System.out.print("PW : ");
		String userPw = sc.next();
		
		System.out.print("Name : ");
		String userName = sc.next();
		
		// 입력 받은 id, pw, name 을 User 객체로 묶어서 서비스 호출
		User user = new User();
		user.setUserId(userId);
		user.setUserPw(userPw);
		user.setUserName(userName);
		
		int result = service.insertUser(user);
		
		// 반환된 결과에 따라 출력할 내용 선택
		if(result > 0) {
			System.out.println("\n" + userId + " 사용자가 등록 되었습니다\n");
		} else {
			System.out.println("\n*** 등록 실패 ***\n");
		}
	}
	
	
	/**
	 * 8.
	 * @throws Exception
	 */
	private void multiInsertUser() throws Exception {
		System.out.println("\n === 여러 User 등록하기 === \n");
		
		// 등록할 유저 수 : 3 
		
		// 1번째 userId : user100 
		// 사용 가능한 ID 입니다
		// 1번째 userPw : pass100
		// 1번째 userName : 유저백
		//--------------------------
		// 2번째 userId : user200 
		// 사용 가능한 ID 입니다
		// 2번째 userPw : pass200
		// 2번째 userName : 유저이백
		
		// 전체 삽입 성공 / 삽입 실패
		
		System.out.print("등록할  User 수 : ");
		int input = sc.nextInt();
		sc.nextLine();
		
		// 입력 받은 회원 정보 저장할 List 객체 생성
		List<User> userList = new ArrayList<User>();
		
for(int i = 0 ; i < input ; i++ ) {
			
			String userId = null; // 입력된 아이디를 저장할 변수
			
			while(true) {
				System.out.print((i+1) + "번째 userId : ");
				userId = sc.nextLine();
				
				int count = service.idCheck(userId);
				
				if(count == 0) { // 중복이 아닌 경우
					System.out.println("사용 가능한 아이디 입니다");
					break;
				}
				
				System.out.println("이미 사용중인 아이디 입니다. 다시 입력 해주세요");
			}
			
			System.out.print((i+1) + "번째 userPw : ");
			String userPw = sc.nextLine();
			
			System.out.print((i+1) + "번째 userName : ");
			String userName = sc.nextLine();
			
			System.out.println("------------------------");
			
			User user = new User();
			user.setUserId(userId);
			user.setUserPw(userPw);
			user.setUserName(userName);
			
			// userList에 user 추가 
			userList.add(user);
			
		} // for end
		
		// 입력 받은 모든 사용자 insert 하는 서비스 호출
		// 결과로 삽입된 행의 개수 반환
		int result = service.multiInsertUser(userList);
		
		// 전체 삽입 성공 시 
		if (result == userList.size()) {
			System.out.println("전체 삽입 성공");
		} else { // 하나라도 실패 시
			System.out.println("삽입 실패!");
		}
		
	}
	

} // service end
