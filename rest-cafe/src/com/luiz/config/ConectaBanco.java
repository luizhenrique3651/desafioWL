package com.luiz.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConectaBanco {
	
	public static Connection getConnection() throws SQLException, ClassNotFoundException {
		Class.forName("com.mysql.cj.jdbc.Driver");
		return DriverManager.getConnection("jdbc:mysql://192.168.0.59:3306/db_cafe?useSSL=true", "luiz", "luiz123");
	}

}
