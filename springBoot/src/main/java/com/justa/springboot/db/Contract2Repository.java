package com.justa.springboot.db;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.justa.springboot.model.Contract2;
import com.justa.springboot.model.ContractInfoExt;

public interface Contract2Repository extends CrudRepository<Contract2, Integer> {
	
	@Query("select c from Contract2 c where c.programId =  ?1 ")  
	// ?1 means the first parameter, you don't need to implement it because CrudRepository automatically create it
    //   	raw sql: programId = ?
	List<Contract2> getContractsByProgramId(Integer programId);
	
	
	// get entity from a view and join
	// comes from https://stackoverflow.com/questions/53508168/jpa-springboot-repository-for-database-view-not-table	
	@Query(nativeQuery = true, value = "SELECT c.*, v.fullName FROM VW_CONTRACT v inner join contract2 c on c.id = v.id where c.programId = ?1")
	List<ContractInfoExt> getFullContractsByProgramId(Integer programId);

}
