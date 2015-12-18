package ca.justa.transaction.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import ca.justa.transaction.bean.Program;

public class ProgramJDBCImpl {
	
	public static void insert(Connection conn, Program program) throws SQLException {

		String sql = "INSERT INTO program (ID, NAME) VALUES (?, ?)";
	
		try (PreparedStatement ps = conn.prepareStatement(sql)) {  // new style of try, will automatically close ps 
			ps.setInt(1, program.getId());
			ps.setString(2, program.getName());
			ps.executeUpdate();
		}
	}
	
	public static Program findById(Connection conn, int id) throws SQLException {

		String sql = "SELECT * FROM program WHERE ID = ?";

		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = conn.prepareStatement(sql);
			ps.setInt(1, id);
			Program program = null;
			rs = ps.executeQuery();
			if (rs.next()) {
				program = new Program();
				program.setId(rs.getInt("ID"));
				program.setName(rs.getString("NAME"));

			}
			rs.close();
			ps.close();
			return program;

		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
				}
			}
			if (ps != null) {
				try {
					ps.close();
				} catch (SQLException e) {
				}
			}

		}
	}

}
