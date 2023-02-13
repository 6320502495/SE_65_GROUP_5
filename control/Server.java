package control;

import java.sql.*;

public class Server {
	private static Connection connection = null;

	public static Connection connection() {
		return connection;
	}

	public static boolean isConnected() {
		return connection != null;
	}
	
	Server() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public static void connect(String serverName, String database, String userName, String password) throws SQLException {
		String url = "jdbc:mysql://" + serverName + "/" + database;
		connection = DriverManager.getConnection(url, userName, password);
	}

	public static void disconnect() throws SQLException {
		connection.close();
	}

	public static boolean execute(String query) throws SQLException {
		return connection.createStatement().execute(query);
	}


	public static ResultSet query(String query) throws SQLException {
		return connection.createStatement().executeQuery(query);
	}
}
