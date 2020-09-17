package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import db.DBClose;
import db.DBConnection;
import dto.MemberDto;



public class MemberDao {
	
	private static MemberDao dao = new MemberDao();
	
	private MemberDao() {
		DBConnection.initConnection(); 
	}
	
	public static MemberDao getInstance() { //싱글톤 방식으로 처리 .
		return dao;
	}
	
	public boolean addMember(MemberDto dto) {
		String sql = "INSERT INTO MEMBER (ID, PWD, NAME, EMAIL, AUTH)VALUES(?, ?, ?, ?, 3)";
		Connection conn = null;
		PreparedStatement pstmt = null;
		int count = 0;
		
		try {
			conn = DBConnection.getConnection();
			System.out.println("1/6 addMember Success");
			pstmt = conn.prepareStatement(sql);
			System.out.println("2/6 addMember Success");
			
			pstmt.setString(1, dto.getId());
			pstmt.setString(2, dto.getPwd());
			pstmt.setString(3, dto.getName());
			pstmt.setString(4, dto.getEmail());
			
			count = pstmt.executeUpdate();
			
			System.out.println("3/6 addMember Success");
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("addMember fail");
		} finally {
			DBClose.close(pstmt, conn, null);
		}
		return count > 0 ? true : false;
	}
	
	public boolean getID(String id) {
		String sql = "SELECT ID FROM MEMBER WHERE ID =? ";
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		boolean findid = false;
		
		System.out.println("sql:" + sql);
		
		try {
			conn = DBConnection.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				findid = true;
			}
		}catch (SQLException e) {
			e.printStackTrace();
		}finally {
			DBClose.close(pstmt, conn, rs);
		}
		return findid;
	}
	
	public MemberDto login(String id, String pwd) {
		String sql = "SELECT ID, NAME, EMAIL, AUTH FROM MEMBER WHERE ID = ? AND PWD =?";
	
		Connection conn = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;
		
		MemberDto memberDto = null;
		
		try {
			conn = DBConnection.getConnection();
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, id.trim());
			psmt.setString(2, pwd.trim());
			
			rs = psmt.executeQuery();
			
			while(rs.next()) {
				String _id = rs.getString(1);
				String _name = rs.getString(2);
				String _email = rs.getNString(3);
				int _auth = rs.getInt(4);
				
				memberDto = new MemberDto(_id, null, _name, _email, _auth);
			}
		}catch (SQLException e) {
			e.printStackTrace();
		}finally {
			DBClose.close(psmt, conn, rs);
		}
		return memberDto;
	}
}
