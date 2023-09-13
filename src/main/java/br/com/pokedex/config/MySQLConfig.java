package br.com.pokedex.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

import org.jboss.logging.Logger;

public class MySQLConfig {
	
	static Logger log = Logger.getLogger(MySQLConfig.class);
	
	public static Connection getConnection() {
		Properties props = new Properties();
		Connection conn = null;
		
		try {
			props.put("user", "davisin69");
			props.put("password", "Fabiola01#");
			conn = DriverManager.getConnection("jdbc:mysql://"
			+ "localhost:3306/pokedex_?allowPublicKeyRetrieval=true"
			+ "&userTimezone=true&serverTimezone=UTC&"
			+ "zeroDateTimeBehavior=convertToNull", props);
			conn.setAutoCommit(false);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return conn;
	}
}
