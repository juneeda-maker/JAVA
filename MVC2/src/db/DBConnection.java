package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

		public static void initConnection() {
			try {
				Class.forName("com.mysql.jdbc.Driver");
				
				System.out.println("Driver Loading Success!");
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
		public static Connection getConnection() {
			Connection conn = null;
			try {
				conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/MVCBOARD", "root", "4940");
				
				System.out.println("DB Connection Success!");
			}catch (SQLException e) {
				e.printStackTrace();
			}
			return conn;
		}
}
