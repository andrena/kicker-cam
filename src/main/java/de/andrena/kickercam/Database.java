package de.andrena.kickercam;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public interface Database {
	Statement createStatement() throws SQLException;

	PreparedStatement createPreparedStatement(String sql) throws SQLException;
}
