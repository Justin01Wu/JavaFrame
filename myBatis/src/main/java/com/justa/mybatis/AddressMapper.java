package com.justa.mybatis;

import org.apache.ibatis.annotations.Insert;

public interface AddressMapper {
	
	@Insert("Insert into address( streetAddress, personId) values (#{streetAddress}, #{personId})")
    public Integer save(Address address);
}
