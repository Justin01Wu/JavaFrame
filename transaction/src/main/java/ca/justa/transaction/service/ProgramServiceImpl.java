package ca.justa.transaction.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.hibernate.Query;
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

	@Transactional
	public void addProgram(Program program) {
		class InsertProgram implements Work {
			private Program program;
			public InsertProgram(Program program){
				this.program =  program;
			}

			public void execute(Connection conn) throws SQLException {
				ProgramJDBCImpl.insert(conn, program);
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
				ProgramJDBCImpl.insert(conn, program);
			}
		};
		
		InsertProgram work = new InsertProgram(program);
		Session session = sessionFactory.getCurrentSession();		
		session.doWork(work);
		
		contract.setProgramId(program.getId());
		session.saveOrUpdate(contract);
	}	
	
	@Transactional(propagation = Propagation.SUPPORTS)
	public Program getProgramById(Integer id) {
		class QueryProgram implements Work {
			private Integer id;
			private Program program;
			public QueryProgram(Integer id){
				this.id =  id;
			}

			public void execute(Connection conn) throws SQLException {
				program = ProgramJDBCImpl.findById(conn, id);
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
	
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<Contract> getContractsByProgramId(Integer id) {
		
		
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery("from Contract where programId = :programId ");
		query.setParameter("programId", id);
		
		@SuppressWarnings("unchecked")
		List<Contract> list = query.list();

		return list;
	}		

	// spring will use this method to inject sessionFactory
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
}
