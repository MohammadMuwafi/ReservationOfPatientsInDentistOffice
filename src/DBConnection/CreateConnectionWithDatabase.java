package DBConnection;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class CreateConnectionWithDatabase {
	private static Connection _connection;
	private static String _dataBaseURL;
	private static String _dataBaseUsername;
	private static String _dataBasePassword;
	private static String _dataBaseName;
	private static String _URL, _port;
	private static CreateConnectionWithDatabase obj;
	
	private CreateConnectionWithDatabase(String URL, String port, String dataBaseName, String dataBaseUsername, String dataBasePassword) {
		_URL = URL;
		_port = port;
		_dataBaseName = dataBaseName;
		_dataBaseUsername = dataBaseUsername;
		_dataBasePassword = dataBasePassword;
	}
	
	public static CreateConnectionWithDatabase getInstance(String URL, String port, String dataBaseName, String dataBaseUsername, String dataBasePassword) {
		if (obj == null) {
			obj = new CreateConnectionWithDatabase(URL, port, dataBaseName, dataBaseUsername, dataBasePassword);
		}
		return obj;
	}

	public Connection makeConnections() throws ClassNotFoundException, SQLException {
		_dataBaseURL = "jdbc:mysql://" + _URL + ":" + _port + "/" + _dataBaseName + "?useUnicode=true&characterEncoding=UTF-8&zeroDateTimeBehavior=ConvertToNull&serverTimezone=GMT";
		Properties p = new Properties();
		p.setProperty("user", _dataBaseUsername);
		p.setProperty("password", _dataBasePassword);
		p.setProperty("useSSL", "false");
		p.setProperty("autoReconnect", "true");
		Class.forName("com.mysql.jdbc.Driver");
		_connection = DriverManager.getConnection(_dataBaseURL, p);
		return _connection;
	}
}