package com.justa.springboot.db;

import org.springframework.data.repository.CrudRepository;

import com.justa.springboot.model.TestBean;

public interface BeanRepository extends CrudRepository<TestBean, Integer> {

}
