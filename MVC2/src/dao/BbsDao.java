package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import db.DBClose;
import db.DBConnection;
import dto.BbsDto;

public class BbsDao {
	private static BbsDao dao = new BbsDao();
	
	private BbsDao() {
		
	}
	
	public static BbsDao getInstance() {
		return dao;
	}
	
	public List<BbsDto> getBbsList() {
		String sql = "SELECT SEQ, ID, REF, STEP, DEPTH, TITLE, CONTENT, WDATE, DEL, READCOUNT FROM BBS ORDER BY REF DESC, STEP ASC";
		
		Connection conn = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;
		
		List<BbsDto> list = new ArrayList<BbsDto>();
		
		try {
			conn = DBConnection.getConnection();
			psmt = conn.prepareStatement(sql);
			rs = psmt.executeQuery();
			while (rs.next()) {
				BbsDto bbsDto = new BbsDto(rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getInt(4), rs.getInt(5),
						rs.getString(6), rs.getString(7), rs.getString(8), rs.getInt(9), rs.getInt(10));
			}
		}catch (SQLException e) {
			e.printStackTrace();
		}finally {
			DBClose.close(psmt, conn, rs);
		}
		return list;
	}
	
	public String getDate() {
		String sql = "SELECT NOW()";
		
		Connection conn = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;
		
		try {
			conn = DBConnection.getConnection();
			psmt = conn.prepareStatement(sql);
			rs = psmt.executeQuery();
			
			if(rs.next()) {
				return rs.getString(1);
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return " "; //데이터 베이스 오류
	}
	
	public int getMaxSeq() {
		
		Connection conn = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;
		int maxSeq = 0;
		
		try {
			conn = DBConnection.getConnection();
			String sql = "SELECT MAX(SEQ) FROM BBS";
			psmt = conn.prepareStatement(sql);
			rs = psmt.executeQuery();
			
			if(rs.next()) {
				maxSeq = rs.getInt(1); //첫번째 결과값을 인트형으로 
			}
			
		}catch (SQLException e) {
			e.printStackTrace();
		}finally {
			DBClose.close(psmt, conn, rs);
		}
		return maxSeq +1;
	}
	//REF, STEP , DEPTH
	public boolean writeBbs(BbsDto bbs) {
		String sql = "INSERT INTO BBS( ID, REF, STEP, DEPTH, TITLE, CONTENT, WDATE, DEL, READCOUNT) VALUES ( ?, ?, 0, 0, ?, ?, ?, 0, 0)";
		
		Connection conn = null;
		PreparedStatement psmt = null;
		int count = 0; //처리된 레코드 갯수
		
		try {
			conn = DBConnection.getConnection();
			psmt = conn.prepareStatement(sql);
		
			psmt.setString(1, bbs.getId());
			psmt.setInt(2, getMaxSeq());
			psmt.setString(3, bbs.getTitle());
			psmt.setString(4, bbs.getContent());
			psmt.setString(5, getDate());
			
			count = psmt.executeUpdate();
			
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			DBClose.close(psmt, conn, null);
		}
		
		return count > 0 ? true : false;
	}
	
	public BbsDto getBbs(int seq) {
		String sql = "SELECT SEQ, ID , REF, STEP, DEPTH, TITLE, CONTENT, WDATE, DEL, READCOUNT FROM BBS WHERE SEQ=?";
		
		Connection conn = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;
		BbsDto bbsDto = null;
		
		try {
			conn = DBConnection.getConnection();
			psmt = conn.prepareStatement(sql);
			psmt.setInt(1, seq);
			rs = psmt.executeQuery();
			
			while (rs.next()) {
				bbsDto = new BbsDto(rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getInt(4), rs.getInt(5),
						rs.getNString(6), rs.getString(7), rs.getString(8), rs.getInt(9), rs.getInt(10));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBClose.close(psmt, conn, rs);
		}
		return bbsDto;
	}
	
	//readcount 올리기
	public void readCount(int seq) {
		
		String sql = "UPDATE BBS SET READCOUNT = READCOUNT + 1 WHERE SEQ = +seq";
		
		Connection conn = null;
		PreparedStatement psmt = null;
		int count = 0;
		
		try {
			conn = DBConnection.getConnection();
			psmt = conn.prepareStatement(sql);
			count = psmt.executeUpdate();
			
			System.out.println(count);
			
		}catch (SQLException e) {
			e.printStackTrace();
		}finally {
			DBClose.close(psmt, conn, null);
		}
	}
	
	public List<BbsDto> getBbsPagingList(String choice, String searchWord, int page){
		/*
		1. row 번호
		2. 검색
		3. 정렬
		4. 범위 1 ~ 10		
	*/
		
		 String sql = "SELECT SEQ, ID, REF, STEP, DEPTH, TITLE, CONTENT, WDATE, DEL, READCOUNT, T.* FROM (SELECT @RNUM := @RNUM + 1 AS RNUM, A.SEQ, A.ID, A.REF, A.STEP, A.DEPTH, A.TITLE, A.CONTENT, A.WDATE, A.DEL, A.READCOUNT FROM (SELECT * FROM BBS ORDER BY REF DESC, STEP ASC)A, (SELECT @RNUM := 0) B) T"; 
		 		
 
		
		String sqlWord = "";
		if(choice.equals("title"))  {
			sqlWord = "WHERE TITLE LIKE '%" + searchWord.trim() + "%' ";
		}else if (choice.equals("writer")) {
			sqlWord = "WHERE ID = '" + searchWord.trim() + "' ";
		}else if(choice.equals("content")) {
			sqlWord = " WHERE CONTENT LIKE '%" + searchWord.trim() + "%' ";
		}
		sql += sqlWord;
		
		sql += " WHERE RNUM >= ? AND RNUM <= ? ";
		
		Connection conn = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;
		
		List<BbsDto> list = new ArrayList<BbsDto>();
		
		int start, end;
		start = 1 + 10 * page; // 0 ->1 1->11
		end = 10 + 10 * page;
		
		try {
			conn = DBConnection.getConnection();
			System.out.println("1/6 getBbsList success");
			
			psmt = conn.prepareStatement(sql);
			psmt.setInt(1, start);
			psmt.setInt(2, end);			
			System.out.println("2/6 getBbsList success");
			
			rs = psmt.executeQuery();
			System.out.println("3/6 getBbsList success");
			
			while(rs.next()) {
				int i = 1;
				BbsDto dto = new BbsDto(rs.getInt(i++), 
						rs.getString(i++), 
						rs.getInt(i++), 
						rs.getInt(i++), 
						rs.getInt(i++), 
						rs.getString(i++), 
						rs.getString(i++), 
						rs.getString(i++), 
						rs.getInt(i++), 
						rs.getInt(i++));
						list.add(dto);
			}
			System.out.println("4/6 getBbsList success");
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			DBClose.close(psmt, conn, rs);
		}
		
		return list;
	}
	
	public int getAllBbs(String choice, String searchWord) {
		String sql ="SELECT COUNT(*) FROM BBS";
		
		String sqlWord = "";
		if(choice.equals("title")) {
			sqlWord = " WHERE TITLE LIKE '%" + searchWord.trim() + "%' ";
		}else if(choice.equals("writer")) {
			sqlWord = " WHERE ID='" + searchWord.trim() + "'";
		}else if(choice.equals("content")) {
			sqlWord = " WHERE CONTENT LIKE '%" + searchWord.trim() + "%' ";
		}
		sql += sqlWord;
		Connection conn = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;
		
		int len = 0;
		try {
			conn = DBConnection.getConnection();
			psmt = conn.prepareStatement(sql);
			rs = psmt.executeQuery();
			
			if(rs.next()) {
				len = rs.getInt(1);
			}
		}catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBClose.close(psmt, conn, rs);
		}
		return len;
	}
	
	public boolean updateBbs(int seq, String title, String content) {
		String sql = "UPDATE BBS SET TITLE = ?, CONTENT = ? WHERE SEQ =?";
		
		Connection conn = null;
		PreparedStatement psmt = null;
		int count = 0;
		
		try {
			conn = DBConnection.getConnection();
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, title);
			psmt.setString(2, content);
			psmt.setInt(3, seq);
			count = psmt.executeUpdate();
		}catch (SQLException e) {
			e.printStackTrace();
		}
		return count > 0 ? true : false;
	}
	
	public boolean deleteBbs(int seq) {
		String sql = "UPDATE BBS SET DEL=1 WHERE SEQ = ?";
		
		Connection conn = null;
		PreparedStatement psmt = null;
		int count = 0;
		
		try {
			conn = DBConnection.getConnection();
			psmt = conn.prepareStatement(sql);
			psmt.setInt(1, seq);
			
			count = psmt.executeUpdate();
		}catch (SQLException e) {
			e.printStackTrace();
		}finally {
			DBClose.close(psmt, conn, null);
		}
		
		return count > 0 ? true : false;
	}
	
	//부모글의 sequence, 답글의 Object
	public boolean answer(int seq, BbsDto bbs) {
		// update (미뤄주기 작업)
		String sql1 = "UPDATE BBS SET STEP = STEP + 1 WHERE REF = (SELECT REF FROM (SELECT REF FROM BBS WHERE SEQ = ?)A) AND STEP > (SELECT STEP FROM (SELECT STEP FROM BBS WHERE SEQ = ?)B)";
		
		// INSER
		String sql2 = "INSERT INTO BBS (ID, REF, STEP, DEPTH, TITLE, CONTENT, WDATE, DEL, READCOUNT) VALUES (?, (SELECT REF FROM BBS A WHERE SEQ = ?),(SELECT STEP FROM BBS B WHERE SEQ = ?) + 1,(SELECT DEPTH FROM BBS C WHERE SEQ = ?) + 1, ?, ?, ?, 0, 0)";
		
		Connection conn = null;
		PreparedStatement psmt = null;
		int count = 0;
		
		try {
			conn = DBConnection.getConnection();
			conn.setAutoCommit(false);
			// 하나라도 제대로 작동하지 않을시 각각을 따로 commit or rollback 할수있게 설정.
			System.out.println("1/6 answer success");
			
			//update
			psmt = conn.prepareStatement(sql1);
			psmt.setInt(1, seq);
			psmt.setInt(2, seq);
			System.out.println("2/6 answer success");
			
			count = psmt.executeUpdate();
			System.out.println("3/6 answer success");
			
			//psmt초기화.
			psmt.clearParameters();
			
			//insert
			psmt = conn.prepareStatement(sql2);
			psmt.setString(1, bbs.getId());
			psmt.setInt(2, seq);
			psmt.setInt(3, seq);
			psmt.setInt(4, seq);
			psmt.setString(5, bbs.getTitle());
			psmt.setString(6, bbs.getContent());
			psmt.setString(7, getDate());
			System.out.println("4/6 answer success");
			
			count = psmt.executeUpdate();
			System.out.println("5/6 answer success");
			conn.commit();
		}catch (SQLException e) {
			System.out.println("answer fail");
			
			try {
				conn.rollback();
			}catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}finally {
			try {
				conn.setAutoCommit(true);
			}catch (SQLException e) {
				e.printStackTrace();
			}
			DBClose.close(psmt, conn, null);
			System.out.println("6/6 answer success");
		}
		
		return count > 0 ? true : false;
	}
	
}
