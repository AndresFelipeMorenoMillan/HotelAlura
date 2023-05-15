package test;

import java.sql.Connection;
import java.sql.SQLException;

import factory.ConnectionFactory;

public class testConnection {
	public static void main(String[] args) throws SQLException {
		ConnectionFactory conexion = new ConnectionFactory();
		Connection con = conexion.conectar();
		
		System.out.println("Conexión abierta");
		con.close();
		System.out.println("Conexión cerrada");
	}

}
