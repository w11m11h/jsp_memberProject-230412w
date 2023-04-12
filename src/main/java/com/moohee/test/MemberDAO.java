package com.moohee.test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class MemberDAO {
	private String driverName = "com.mysql.jdbc.Driver";
	private String url = "jdbc:mysql://localhost:3306/jspdb";
	private String userName = "root";
	private String password = "1234";
	
	public int insertMember(MemberDTO dto) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		String sql = "INSERT INTO members(id, pass, email) VALUES (?,?,?)";
		
		int dbFlag = 0;
		
		try {
			Class.forName(driverName);
			conn = DriverManager.getConnection(url, userName, password);
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, dto.getId());
			pstmt.setString(2, dto.getPass());
			pstmt.setString(3, dto.getEmail());			
			
			dbFlag = pstmt.executeUpdate();
			
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			try {				
				if(pstmt != null) {
					pstmt.close();
				}
				if(conn != null) {
					conn.close();
				}
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
		return dbFlag;
	}
	public int loginCheck(String id, String pass) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		String sql = "SELECT * FROM members WHERE id = ? and pass = ?";
		
		int dbFlag = 0;
		
		try {
			Class.forName(driverName);
			conn = DriverManager.getConnection(url, userName, password);
			
			pstmt = conn.prepareStatement(sql);	
			pstmt.setString(1, id);
			pstmt.setString(2, pass);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				dbFlag = 1; //로그인성공				
			} else {
				 dbFlag = 0; //로그인실패
			}
			
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			try {	
				if(rs != null) {
					rs.close();
				}
				if(pstmt != null) {
					pstmt.close();
				}
				if(conn != null) {
					conn.close();
				}
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
		return dbFlag;
	}
	public int deleteMember(String id) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		String sql = "DELETE FROM members WHERE id = ?";
		
		int dbFlag = 0;
		
		try {
			Class.forName(driverName);
			conn = DriverManager.getConnection(url, userName, password);
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);		
			
			dbFlag = pstmt.executeUpdate();
			
			if(dbFlag == 1) {
				System.out.println("회원탈퇴 성공!!");
			} else {
				System.out.println("회원탈퇴 실패!!");				
			}
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			try {				
				if(pstmt != null) {
					pstmt.close();
				}
				if(conn != null) {
					conn.close();
				}
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
		return dbFlag;
	}
	
}