package model;

import java.sql.Connection;
import java.sql.DriverManager;

public class DAO {
	
	private String driver = "com.mysql.cj.jdbc.Driver";
	private String url = "jdbc:mysql://10.26.45.102:3306/dbcarrinho";
	private String user = "dba";
	private String password = "Senac@123";

	/**
	 * Conexão com o Banco de Dados
	 * 
	 * @return
	 */

	public Connection conectar() {
		
		Connection con = null;
		
		try {
			
			Class.forName(driver);
			con = DriverManager.getConnection(url, user, password);
			return con;

		} catch (Exception e) {
			System.out.print(e);
			return null;

		}

	}
}
