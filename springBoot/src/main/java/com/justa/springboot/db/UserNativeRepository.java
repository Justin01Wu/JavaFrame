package com.justa.springboot.db;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.justa.springboot.db.converter3.PositionEnumExtendedBeanPropertyRowMapper;
import com.justa.springboot.model.User;

@Repository
public class UserNativeRepository {

    private final JdbcTemplate jdbcTemplate;

    public UserNativeRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<User> getUsersByPosition(PositionEnum position) {

        String sql = "select * from User where position like '%s' order by id desc";

        sql = String.format(sql, position.getShortName());

        // PositionEnumConverter doesn't work on JDBCTemplate, it will get conversion exception on Position column:             
          //BeanPropertyRowMapper<User> rowMapper = new BeanPropertyRowMapper<>(User.class);
       
        // so have to use customized mapper UserRowMapper, which is ugly and heavy:
        //RowMapper<User> rowMapper = new UserRowMapper();
        
        // for details, please see below:
        // (1) https://stackoverflow.com/questions/25536969/spring-jdbc-postgres-sql-java-8-conversion-from-to-localdate
        // (2) https://stackoverflow.com/questions/2751733/map-enum-in-jpa-with-fixed-values
        // (3) https://stackoverflow.com/questions/34239585/how-to-register-custom-converters-in-spring-boot        
        
        //ExtendedBeanPropertyRowMapper<User> rowMapper = new ExtendedBeanPropertyRowMapper<>(User.class);  // this also works
        PositionEnumExtendedBeanPropertyRowMapper<User> rowMapper = new PositionEnumExtendedBeanPropertyRowMapper<>(User.class);
        
        return jdbcTemplate.query(sql, rowMapper);
    }
    
    
	private class UserRowMapper implements RowMapper<User> {
		public User mapRow(ResultSet rs, int rowNum) throws SQLException {
			Integer id = rs.getInt("id");
			String email = rs.getString("email");
			String username = rs.getString("name");
			String position = rs.getString("position");
			User user = new User();
			user.setId(id);
			user.setName(username);
			user.setEmail(email);
			PositionEnum p = PositionEnum.fromShortName(position);
			user.setPosition(p);
			return user;
		}
	}

}
