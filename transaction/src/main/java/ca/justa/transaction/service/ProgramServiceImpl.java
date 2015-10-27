package ca.justa.transaction.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.jdbc.Work;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import ca.justa.transaction.bean.Contract;
import ca.justa.transaction.bean.Program;

@Repository
public class ProgramServiceImpl implements ProgramService{
	
	@Autowired
	private SessionFactory sessionFactory;

	@Transactional(propagation = Propagation.SUPPORTS)
	public void insert(Connection conn, Program program) {

		String sql = "INSERT INTO program (ID, NAME) VALUES (?, ?)";

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

	@Transactional(propagation = Propagation.SUPPORTS)
	public Program findById(Connection conn, int id) {

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
	public void addProgram(Program program) {
		class InsertProgram implements Work {
			private Program program;
			public InsertProgram(Program program){
				this.program =  program;
			}

			public void execute(Connection conn) throws SQLException {
				insert(conn, program);
			}
		};
		
		InsertProgram work = new InsertProgram(program);
		Session session = sessionFactory.getCurrentSession();
		session.doWork(work);
	}
	
	@Transactional
	public void addProgramAndContract(Program program, Contract contract) {
		class InsertProgram implements Work {
			private Program program;
			public InsertProgram(Program program){
				this.program =  program;
			}

			public void execute(Connection conn) throws SQLException {
				insert(conn, program);
			}
		};
		
		InsertProgram work = new InsertProgram(program);
		Session session = sessionFactory.getCurrentSession();
		contract.setProgramId(program.getId());
		session.saveOrUpdate(contract);
		session.doWork(work);
	}	
	
	//@Transactional(propagation = Propagation.SUPPORTS) // this one should work, but fail: No Session found for current thread
	// TODO investigate 
	@Transactional
	public Program getProgramById(Integer id) {
		class QueryProgram implements Work {
			private Integer id;
			private Program program;
			public QueryProgram(Integer id){
				this.id =  id;
			}

			public void execute(Connection conn) throws SQLException {
				program = findById(conn, id);
			}
			
			public Program getProgram(){
				return program;
			}
		};
		
		QueryProgram work = new QueryProgram(id);
		Session session = sessionFactory.getCurrentSession();
		session.doWork(work);
		return work.getProgram();
	}	

	// spring will use this method to inject sessionFactory
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
}
