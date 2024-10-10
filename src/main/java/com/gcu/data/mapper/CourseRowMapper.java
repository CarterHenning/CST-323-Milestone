package com.gcu.data.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.gcu.data.entity.CourseEntity;

/**
 * RowMapper implementation for mapping a ResultSet to a CourseEntity object.
 */
public class CourseRowMapper implements RowMapper<CourseEntity> {

	/**
	 * Maps a single row of the ResultSet to a CourseEntity object.
	 *
	 * @param rs     The ResultSet to map.
	 * @param rowNum The number of the current row.
	 * @return A CourseEntity object mapped from the ResultSet.
	 * @throws SQLException If a database access error occurs or this method is
	 *                      called on a closed result set.
	 */
	@Override
	public CourseEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
		return new CourseEntity(
			rs.getInt("id"),
				rs.getString("title"),
				rs.getString("description"),
				rs.getInt("rating"));
	}
}
