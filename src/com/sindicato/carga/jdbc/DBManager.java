package com.sindicato.carga.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBManager {
	
	public static Connection obterConexao() {
		
		Connection connection = null;
		
		String usuario = "postgres";
		String senha = "Controle321";
		
		try {
			Class.forName("org.postgresql.Driver");
//			connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/CorrecaoMensalidades", usuario, senha);
			connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/ControleFinanceiro", usuario, senha);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch(SQLException e) {
			e.printStackTrace();
		}

		return connection;
	}
	
}
