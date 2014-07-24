package com.sindicato.carga;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;

import com.sindicato.jdbc.DBManager;

public class OrganizacaoMensalidades {

	public OrganizacaoMensalidades(){
		conexao = DBManager.obterConexao();
		
		dataBase = Calendar.getInstance();
		dataBase.set(2012, 06, 01);
	}
	
	private Connection conexao;
	private Calendar dataBase;

	public void organizaMensalidade20(){
		
		PreparedStatement pstmt = null;
		ResultSet   rs = null;
		
		try {
			conexao = DBManager.obterConexao();

			String sql = "select ds.* from debitoservico ds "+
						"Inner Join debito d on d.id = ds.debito_id "+
						"where "+ 
						"ds.servico_id = 21  " + 
						"and d.database < '2012-06-01' " + 
						"and ds.valor > 20 " +
					//	"and ds.id = 325 " +
						"order by d.id";
			
			pstmt = conexao.prepareStatement(sql);
			
			rs = pstmt.executeQuery();
			
			/*
			 - pegar todas as mensalidades > 20 até 2005-06-01
			 - dividir o valor do servico por 20 e fazer um loop do resultado
			 - cada iteração vou inserir um debitoservico identico com o valor de 20 reais
			 - na ultima iteracao eu faço um update no debito principal mudando o valor para 20
			*/
			String strInsert = "INSERT INTO debitoservico(id, valor, debito_id, servico_id) VALUES (nextval('seq_debitoservico'), ?, ?, ?);";
			String strUpdate = "UPDATE debitoservico set valor = ? where id = ?";
			int contador = 1;
			
			conexao.setAutoCommit(false);
			while (rs.next()) {
				contador++;
				int valor = rs.getInt("valor");
				int divisao = Integer.divideUnsigned(valor, 20);
				int debito = rs.getInt("debito_id");
				int idDebitoServico = rs.getInt("id");
				
				for (int i = 1; i <= divisao; i++) {
					
					if(i == divisao){
						System.out.println("UPDATE");
						System.out.print("id: " + idDebitoServico);
						System.out.print("debito: " + debito);
						System.out.print("valor: " + 20);
						System.out.print("servico: " + 21);
						
						PreparedStatement ps = conexao.prepareStatement(strUpdate);
						ps.setInt(1, 20);
						ps.setInt(2, idDebitoServico);
						ps.executeUpdate();
					} else {
						System.out.println("INSERT");
						System.out.print("debito: " + debito);
						System.out.print("valor: " + 20);
						System.out.print("servico: " + 21);
						
						PreparedStatement ps = conexao.prepareStatement(strInsert);
						ps.setInt(1, 20);
						ps.setInt(2, debito);
						ps.setInt(3, 21);
						ps.executeUpdate();
					}
				}
				
				System.out.println();
				//System.out.println(rs.getInt("id"));
				
			}
			conexao.commit();
			System.out.println(contador);
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				conexao.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		} finally {
			try {
				pstmt.close();
				rs.close();
				conexao.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}	

	}
	
	
	public void organizaMensalidade30(){
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			conexao = DBManager.obterConexao();

			String sql = "select ds.* from debitoservico ds "+
						"Inner Join debito d on d.id = ds.debito_id "+
						"where "+ 
						"ds.servico_id = 21 " + 
						"and d.database >= '2012-06-01' " + 
						"and ds.valor > 30 " +
					//	"and ds.id = 325 " +
						"order by d.id";
			
			pstmt = conexao.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			/*
			 - pegar todas as mensalidades > 20 até 2005-06-01
			 - dividir o valor do servico por 20 e fazer um loop do resultado
			 - cada iteração vou inserir um debitoservico identico com o valor de 20 reais
			 - na ultima iteracao eu faço um update no debito principal mudando o valor para 20
			*/
			String strInsert = "INSERT INTO debitoservico(id, valor, debito_id, servico_id) VALUES (nextval('seq_debitoservico'), ?, ?, ?);";
			String strUpdate = "UPDATE debitoservico set valor = ? where id = ?";
			
			int valorMensalidade = 30;
			int contador = 1;
			
			conexao.setAutoCommit(false);
			while (rs.next()) {
				contador++;
				int valor = rs.getInt("valor");
				int divisao = Integer.divideUnsigned(valor, valorMensalidade);
				int debito = rs.getInt("debito_id");
				int idDebitoServico = rs.getInt("id");
				
				for (int i = 1; i <= divisao; i++) {
					
					if(i == divisao){
						/*System.out.println("UPDATE");
						System.out.print("id: " + idDebitoServico);
						System.out.print("debito: " + debito);
						System.out.print("valor: " + valorMensalidade);
						System.out.print("servico: " + 21);*/
						
						PreparedStatement ps = conexao.prepareStatement(strUpdate);
						ps.setInt(1, valorMensalidade);
						ps.setInt(2, idDebitoServico);
						ps.executeUpdate();
					} else {
						/*System.out.println("INSERT");
						System.out.print("debito: " + debito);
						System.out.print("valor: " + valorMensalidade);
						System.out.print("servico: " + 21);*/
						
						PreparedStatement ps = conexao.prepareStatement(strInsert);
						ps.setInt(1, valorMensalidade);
						ps.setInt(2, debito);
						ps.setInt(3, 21);
						ps.executeUpdate();
					}
				}
				
				//System.out.println();
				//System.out.println(rs.getInt("id"));
			}
			System.out.println(contador);
			conexao.commit();
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				conexao.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		} finally {
			try {
				pstmt.close();
				rs.close();
				conexao.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}	

	}
	
}
