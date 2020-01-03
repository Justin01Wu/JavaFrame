package com.justa.mybatis;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;

// comes from https://stackoverflow.com/questions/10219253/mybatis-enum-usage
public class StatusTypeHandler implements TypeHandler<StatusEnum> {
	
	public static StatusEnum getEnum(Integer v) throws SQLException {
		if(v== null) {
			return null;
		}
		StatusEnum e = StatusEnum.getEnum(v);
		if(e == null) {
			throw new SQLException("unexpected value:" + v);
		}
		return e;
	}

	@Override
	public StatusEnum getResult(ResultSet rs, String param) throws SQLException {
		Integer v = rs.getInt(param);
		return getEnum(v);
	}

	@Override
	public StatusEnum getResult(CallableStatement cs, int col) throws SQLException {
		Integer v = cs.getInt(col);
		return getEnum(v);
	}

	@Override
	public void setParameter(PreparedStatement ps, int paramInt, StatusEnum paramType, JdbcType jdbctype)
			throws SQLException {
		ps.setInt(paramInt, paramType.getValue());
	}

	@Override
	public StatusEnum getResult(ResultSet rs, int columnIndex) throws SQLException {
		Integer v = rs.getInt(columnIndex);
		return getEnum(v);
	}
}