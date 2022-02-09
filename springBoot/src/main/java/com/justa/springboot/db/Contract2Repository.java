package com.justa.springboot.db;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.justa.springboot.model.Contract2;

public interface Contract2Repository extends CrudRepository<Contract2, Integer> {
	
	@Query("select c from Contract2 c where c.programId =  ?1 ")  
	// ?1 means the first parameter, you don't need to implement it because CrudRepository automatically create it
    //   	raw sql: programId = ?
	List<Contract2> getContractsByProgramId(Integer programId);



}
