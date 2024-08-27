package edu.kh.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

public class JDBCPractice1 {
	
	public static void main(String[] args) {
		
		
		// EMPLOYEE 테이블에서
		// 사번, 이름, 성별, 급여, 직급명, 부서명을 조회
		// 단, 입력 받은 조건에 맞는 결과만 조회하고 정렬할 것
				
		// - 조건 1 : 성별 (M, F)
		// - 조건 2 : 급여 범위
		// - 조건 3 : 급여 오름차순/내림차순
				
		// [실행화면]
		// 조회할 성별(M/F) : F
		// 급여 범위(최소, 최대 순서로 작성) : 3000000 4000000
		// 급여 정렬(1.ASC, 2.DESC) : 2
				
		// 사번 | 이름   | 성별 | 급여    | 직급명 | 부서명
		//--------------------------------------------------------
		// 218  | 이오리 | F    | 3890000 | 사원   | 없음
		// 203  | 송은희 | F    | 3800000 | 차장   | 해외영업2부
		// 212  | 장쯔위 | F    | 3550000 | 대리   | 기술지원부
		// 222  | 이태림 | F    | 3436240 | 대리   | 기술지원부
		// 207  | 하이유 | F    | 3200000 | 과장   | 해외영업1부
		// 210  | 윤은해 | F    | 3000000 | 사원   | 해외영업1부

		
		
		Connection conn = null; // DB연결정보 저장 객체
		PreparedStatement pstmt = null;
		ResultSet rs = null; // SELECT 수행 결과 저장 객체
		
		Scanner sc = new Scanner (System.in);
		
		try {
			
			
			Class.forName("oracle.jdbc.driver.OracleDriver"); 
			
			// url == type + host + port + dbName
			String url = "jdbc:oracle:thin:@localhost:1521:XE";
			String userName = "KH_JSH";
			String password = "KH1234";
			
			
			conn = DriverManager.getConnection(url, userName, password);
			
			conn.setAutoCommit(false);
			
			System.out.print("성별 (M/F) : ");
			String genderInput = sc.nextLine().toUpperCase(); // 대소문자 구분 안 함
			
			System.out.print("급여 범위 시작 : ");
			String salayInput1 = sc.nextLine();
			
			System.out.print("급여 범위 끝 : ");
			String salayInput2 = sc.nextLine();
			
			System.out.print("급여 정렬 (1. ASC 2. DESC)");
			int salaryArray = sc.nextInt();
			
			String sql = """
					SELECT EMP_ID, EMP_NAME,
					DECODE(SUBSTR(EMP_NO, 8, 1), '1', 'M', '2', 'F') 성별,
					SALARY,
					JOB_NAME,
					NVL(DEPT_TITLE, '없음') 부서명
					FROM EMPLOYEE
					JOIN JOB USING (JOB_CODE)
					LEFT JOIN DEPARTMENT ON EMPLOYEE.DEPT_CODE = DEPARTMENT.DEPT_ID
					WHERE SALARY BETWEEN ? AND ? 
					AND DECODE(SUBSTR(EMP_NO, 8, 1), '1', 'M', '2', 'F') = ?
					ORDER BY SALARY
					""";
			
			// ORDER BY 문에 ? 를 넣으면 안 되는 이유
			// String일 경우 자동으로 ',' 홑따옴표가 붙기 때문
			
			if(salaryArray == 1) sql += "ASC";
			if(salaryArray == 2) sql += "DESC";
			// else  sql += "DESC";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, salayInput1);
			pstmt.setString(2, salayInput2);
			pstmt.setString(3, genderInput);
			
			
			rs = pstmt.executeQuery();
			
			// preparedStatement 는 sql 부르는 걸 먼저 작성하고
			// result set 에 넣을 때는 매개변수를 포함하지 않는다
			
			boolean flag = true; // true 조회 결과 없음 / false 존재한다
			
			while(rs.next()) {
				flag = false;
				
				String empId = rs.getString("EMP_ID");
				String empName = rs.getString("EMP_NAME");
				String gender = rs.getString("성별");
				int salary = rs.getInt("SALARY");
				String jobName = rs.getString("JOB_NAME");
				String deptTitle = rs.getString("부서명");
				
				System.out.printf("%s | %s | %s | %d | %s | %s \n", empId, empName, gender, salary, jobName, deptTitle);
			}
			
			if(flag) { // flag == true인 경우 조회 결과 없음
				System.out.println("조회 결과 없음");
			}
			
			/*		// 7. 커서를 이용해서 한 행씩 접근하여
		//   컬럼 값 얻어오기
		
		System.out.println("사번 | 이름   | 성별 | 급여    | 직급명 | 부서명");
		System.out.println("--------------------------------------------------------");
		
		while(rs.next()) {
			
			String empId     = rs.getString("EMP_ID");
			String empName   = rs.getString("EMP_NAME");
			String gen   	 = rs.getString("GENDER");
			int    salary 	 = rs.getInt("SALARY");
			String jobName   = rs.getString("JOB_NAME");
			String deptTitle = rs.getString("DEPT_TITLE");
			
			System.out.printf(
					"%-4s | %3s | %-4s | %7d | %-3s  | %s \n",
					empId, empName, gen, salary, jobName, deptTitle);
		}*/
			
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null) rs.close();
				if (pstmt != null) pstmt.close();
				if (conn != null) conn.close();
				if (sc != null) sc.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		
	} // main end

}
