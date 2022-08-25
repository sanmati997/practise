package scm_acat.serviceValidator;

import java.sql.*;
import java.util.*;


public class DBOperation {
	static String DB_URL;
	static String DB_USER_ID;
	static String DB_PASSWORD;
	static String tableName;

	ServiceProvider provider = new  ServiceProvider();
	Properties properties = provider.readProp();
	Connection conn=null;
	Statement st=null;


	public   Connection getConnection() throws SQLException  {
		DB_URL = properties.getProperty("dbHost");
		DB_USER_ID = properties.getProperty("dbUserID");
		DB_PASSWORD = properties.getProperty("dbPassword");

		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return DriverManager.getConnection(DB_URL, DB_USER_ID, DB_PASSWORD);
	}
}
