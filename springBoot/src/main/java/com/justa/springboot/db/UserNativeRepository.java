package com.justa.springboot.db;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.justa.springboot.model.User;

@Repository
public class UserNativeRepository {

    private final JdbcTemplate jdbcTemplate;

    private RowMapper<User> rowMapper = new UserRowMapper();
    
    public UserNativeRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<User> getUsersByPosition(PositionEnum position) {

        String sql = "select * from User where position like '%s' order by id desc";

        sql = String.format(sql, position.getShortName());

        //BeanPropertyRowMapper<User> rowMapper = new BeanPropertyRowMapper<>(User.class);
        return jdbcTemplate.query(sql, rowMapper);
    }
    
    
    // for unknown reason, PositionEnumConverter doesn't work on JDBCTemplate
    // so have to use customized mapper, TODO find root cause
    // for fixing, please see below:
    // https://stackoverflow.com/questions/25536969/spring-jdbc-postgres-sql-java-8-conversion-from-to-localdate
    // https://stackoverflow.com/questions/2751733/map-enum-in-jpa-with-fixed-values
    // https://stackoverflow.com/questions/34239585/how-to-register-custom-converters-in-spring-boot
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
