package ca.justa.transaction.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.jdbc.Work;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import ca.justa.transaction.bean.Program;

public class ProgramService {

	@Autowired
	private SessionFactory sessionFactory;

	@Transactional(propagation = Propagation.SUPPORTS)
	public void insert(Connection conn, Program program) {

		String sql = "INSERT INTO program (ID, NAME) VALUES (?, ?, ?)";

		PreparedStatement ps = null;
		try {
			ps = conn.prepareStatement(sql);
			ps.setInt(1, program.getId());
			ps.setString(2, program.getName());
			ps.executeUpdate();
			ps.close();

		} catch (SQLException e) {
			throw new RuntimeException(e);

		} finally {
			if (ps != null) {
				try {
					ps.close();
				} catch (SQLException e) {
				}
			}
		}
	}

	public Program findById(Connection conn, int id) {

		String sql = "SELECT * FROM EMPLOYEE WHERE ID = ?";

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
		} catch (SQLException e) {
			throw new RuntimeException(e);
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

	@Transactional
	public void testInsert() {
		Work work = new Work() {

			public void execute(Connection conn) throws SQLException {
				String sql = "INSERT INTO program (ID, NAME) VALUES (?, ?, ?)";
				PreparedStatement ps = conn.prepareStatement(sql);
				ps.setInt(1, 1234);
				ps.setString(2, "from work");

				ps.executeUpdate();

			}
		};

		Session session = sessionFactory.getCurrentSession();
		session.doWork(work);
	}

}
