package ca.justa.transaction.service;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Session;
import org.hibernate.jdbc.Work;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import ca.justa.transaction.bean.Contract;
import ca.justa.transaction.bean.Program;

@Repository
public class ProgramServiceImpl implements ProgramService{
	
	@PersistenceContext
	private EntityManager entityManager;

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
		Session session = entityManager.unwrap(Session.class);
//		Session session = sessionFactory.getCurrentSession();
		
		session.doWork(work);
	}
	
	@Transactional
	public void addProgramAndContract(Program program, Contract contract) throws IOException {
		
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
		Session session = entityManager.unwrap(Session.class);
//		Session session = sessionFactory.getCurrentSession();		
		session.doWork(work);
		
		if(program.getId()==5555555){
			throw new RuntimeException("test roll back");
		}else if(program.getId()==4444444){
			throw new IOException("test half commit");
		}else if(program.getId()==6666666){
			return ;
		}
		
		contract.setProgramId(program.getId());
		//session.saveOrUpdate(contract);
		entityManager.persist(contract);

	}	

	/**
	 * this method warp addProgramAndContract to different transaction behavior
	 */
	@Transactional(rollbackFor={IOException.class,IllegalAccessException.class})
	public void addProgramAndContract2(Program program, Contract contract) throws IOException {
		addProgramAndContract(program, contract);
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
		Session session = entityManager.unwrap(Session.class);
//		Session session = sessionFactory.getCurrentSession();
		session.doWork(work);
		return work.getProgram();
	}	
	
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<Contract> getContractsByProgramId(Integer id) {
		
		javax.persistence.TypedQuery<Contract> query = entityManager.createQuery( "SELECT c FROM Contract c WHERE c.programId =  :programId", Contract.class);
		query.setParameter("programId", id);
		
		List<Contract> list = query.getResultList();

		return list;
	}		
	
}
