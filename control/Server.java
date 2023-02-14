package control;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.*;

public class Server {
	private static Connection connection = null;

	public static final Connection connection() {
		return connection;
	}

	public static final boolean isConnected() {
		return connection != null;
	}

	Server() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public static final void connect(String serverName, String database, String userName, String password)
			throws SQLException {
		String url = "jdbc:mysql://" + serverName + "/" + database;
		connection = DriverManager.getConnection(url, userName, password);
	}

	public static final void disconnect() throws SQLException {
		connection.close();
	}

	public static final boolean execute(String query) throws SQLException {
		return connection.createStatement().execute(query);
	}

	public static final ResultSet query(String query) throws SQLException {
		return connection.createStatement().executeQuery(query);
	}

	public enum BlobType {
		TINY, NORMAL, MEDIUM, LONG
	}

	public static final File blobToFile(Blob blob, BlobType blobType, String saveName)
			throws IOException, SQLException {
		InputStream in = blob.getBinaryStream();
		OutputStream out = new FileOutputStream(MPainter.imagePath() + saveName);
		byte[] buff = null;
		switch (blobType) {
		case TINY:
			buff = new byte[255];
			break;
		case NORMAL:
			buff = new byte[65535];
			break;
		case MEDIUM:
			buff = new byte[16777215];
			break;
		case LONG:
			buff = new byte[1048576 * 4194304];
			break;
		}
		int len = 0;
		while ((len = in.read(buff)) != -1) {
			out.write(buff, 0, len);
		}
		out.close();
		return new File(MPainter.imagePath() + saveName);
	}
}
