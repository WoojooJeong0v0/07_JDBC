package shopDAO;
import common.JDBCTemplate.*;
import edu.kh.jdbc.dao.UserDao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ShopDAO {
	
	private PreparedStatement pstmt = null;
	private ResultSet rs = null;
	
	public ShopMember selectMember(Connection conn, String memberId) {
		
		// private UserDao dao = new UserDao();
		
//		private ShopMember mem = new ShopMember();s
		
		ShopMember sm = null;
		
		try {
			String sql = "SELECT * FROM SHOP_MEMBER WHERE MEMEBER_ID = ?";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, memberId);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				String id = rs.getString("MEMBER_ID");
				String pw= rs.getString("MEMBER_PW");
				String phone = rs.getString("PHONE");
				String gender = rs.getString("성별");
				
				sm = new ShopMember(id, pw, phone, gender);
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return sm;
		
		
	}

}
