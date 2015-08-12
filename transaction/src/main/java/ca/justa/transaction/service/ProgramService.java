package ca.justa.transaction.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import ca.justa.transaction.bean.Program;


public class ProgramService {
	
	private DataSource dataSource;

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	
	@Transactional(propagation = Propagation.SUPPORTS)
	public void insert(Program program) {

		String sql = "INSERT INTO program (ID, NAME) VALUES (?, ?, ?)";
		Connection conn = null;

		try {
			conn = dataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, program.getId());
			ps.setString(2, program.getName());
			ps.executeUpdate();
			ps.close();

		} catch (SQLException e) {
			throw new RuntimeException(e);

		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
				}
			}
		}
	}

	public Program findById(int id) {

		String sql = "SELECT * FROM EMPLOYEE WHERE ID = ?";

		Connection conn = null;

		try {
			conn = dataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, id);
			Program program = null;
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				program = new Program();
				program.setId(rs.getInt("ID"));
				program.setName(rs.getString("NAME"));				
				
			}
			rs.close();
			ps.close();
			return program;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
				}
			}
		}
	}

}
