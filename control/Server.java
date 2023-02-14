package control;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.rowset.serial.SerialException;

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

	public static final String FileToHexString(File file) throws IOException, SerialException, SQLException {
		byte[] bytes = new byte[(int) file.length()];
		FileInputStream fileInputs = null;
		try {
			fileInputs = new FileInputStream(file);
			fileInputs.read(bytes);
		} finally {
			if (fileInputs != null)
				fileInputs.close();
		}
		short[] bin = new short[bytes.length];
		for (int i = 0; i < bytes.length; i++)
			bin[i] = (short) (bytes[i] & 0xFF);
		byte[] hex = new byte[bin.length * 2];
		for (int i = 0; i < bin.length; i++) {
			short number[] = new short[2];
			number[1] = (short) (bin[i] % 16);
			number[0] = (short) Math.floorDiv(bin[i], 16);
			switch (number[0]) {
			case 10:
				hex[2*i] = 'a';
				break;
			case 11:
				hex[2*i] = 'b';
				break;
			case 12:
				hex[2*i] = 'c';
				break;
			case 13:
				hex[2*i] = 'd';
				break;
			case 14:
				hex[2*i] = 'e';
				break;
			case 15:
				hex[2*i] = 'f';
				break;
			default:
				hex[2*i] = (byte) (number[0] + '0');
				break;
			}
			switch (number[1]) {
			case 10:
				hex[2*i+1] = 'a';
				break;
			case 11:
				hex[2*i+1] = 'b';
				break;
			case 12:
				hex[2*i+1] = 'c';
				break;
			case 13:
				hex[2*i+1] = 'd';
				break;
			case 14:
				hex[2*i+1] = 'e';
				break;
			case 15:
				hex[2*i+1] = 'f';
				break;
			default:
				hex[2*i+1] = (byte) (number[1] + '0');
				break;
			}
		}
		return "0x" + new String(hex);
	}

	public static final File blobToFile(Blob blob, BlobType blobType, String saveName)
			throws IOException, SQLException {
		InputStream inputs = blob.getBinaryStream();
		OutputStream outputs = new FileOutputStream(MPainter.imagePath() + saveName);
		byte[] buffer = null;
		switch (blobType) {
		case TINY:
			buffer = new byte[255];
			break;
		case NORMAL:
			buffer = new byte[65535];
			break;
		case MEDIUM:
			buffer = new byte[16777215];
			break;
		case LONG:
			buffer = new byte[1048576 * 4194304 - 1];
			break;
		}
		int length = 0;
		while ((length = inputs.read(buffer)) != -1)
			outputs.write(buffer, 0, length);
		outputs.close();
		return new File(MPainter.imagePath() + saveName);
	}
}
