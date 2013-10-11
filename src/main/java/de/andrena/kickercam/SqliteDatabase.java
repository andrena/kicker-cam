package de.andrena.kickercam;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class SqliteDatabase implements Database {

	private Connection connection;

	public SqliteDatabase(File workingDirectory) {
		try {
			Class.forName("org.sqlite.JDBC");
			connection = DriverManager.getConnection("jdbc:sqlite:" + workingDirectory.getAbsolutePath()
					+ "/Kicker.db");
		} catch (Exception e) {
			throw new RuntimeException("Couldn't create database connection", e);
		}
	}

	@Override
	public Statement createStatement() throws SQLException {
		return connection.createStatement();
	}

	@Override
	public PreparedStatement createPreparedStatement(String sql) throws SQLException {
		return connection.prepareStatement(sql);
	}

}
