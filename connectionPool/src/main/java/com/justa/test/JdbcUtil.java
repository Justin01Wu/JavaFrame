package com.justa.test;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class JdbcUtil {
	
	public static void setContext(Connection con) throws SQLException{
		
		//rivate static final String CALL_SP = "{call sp_set_session_context('user_id', ?)}";
		
		// since sql server 2012, it has sp_set_session_context, which can be used in RLS(Row level security) function 
		CallableStatement cs = con.prepareCall("{call sp_set_session_context('user_id', ?)}");  
		// call sp_set_session_context to set current user info which will be used by RLS function
		int userId =  13;
		cs.setInt(1, userId);
		boolean success  = cs.execute();
		if(success){
			System.out.println("has result");			
		}else{
			System.out.println("no result");
		}
	}

	
	public static void  readSession(Connection con) throws SQLException {
		
		String SELECT_CONTEXT = "SELECT SESSION_CONTEXT(N'user_id')";
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		ps = con.prepareStatement(SELECT_CONTEXT);
		rs = ps.executeQuery();
		if (rs.next()) {
			System.out.println( rs.getInt(1));
		}else{
			System.out.println("not found");
		}		
	}
}
