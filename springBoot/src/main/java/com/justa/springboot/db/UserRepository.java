package com.justa.springboot.db;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.justa.springboot.model.User;

//This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
//CRUD refers Create, Read, Update, Delete

//   You need to create the repository that holds user records
public interface UserRepository extends CrudRepository<User, Integer> {
	
	@Query("select u from User u where u.name like ?1 and u.position = ?2")  
	// ?2 means the second parameter, you don't need to implement it because CrudRepository automatically create it
    //   	raw sql:name like = ? and position = ?
	List<User> getUsersByNameAndPosition(String name,  PositionEnum position);

}
