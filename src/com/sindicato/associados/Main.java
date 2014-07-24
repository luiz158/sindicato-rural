package com.sindicato.associados;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map.Entry;

import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

import com.sindicato.jdbc.DBManager;

public class Main {

	public static void main(String[] args) throws BiffException, IOException,
			SQLException {

		HashMap<Integer, Integer> comoDeveSerMap = getComoDeveSer();
		HashMap<Integer, Integer> comoEstaMap = getComoEsta();

		Connection conexao = null;
		String strInsert = "INSERT INTO debitoservico(id, valor, debito_id, servico_id) VALUES (nextval('seq_debitoservico'), ?, ?, ?);";
		//String strDelete = "DELETE FROM debitoservico WHERE id in (?)";

		try {
			conexao = DBManager.obterConexao();

			conexao.setAutoCommit(false);
			for (Entry<Integer, Integer> comoDeveSerItem : comoDeveSerMap
					.entrySet()) {

				int matricula = comoDeveSerItem.getKey();
				int comoDeveSer = comoDeveSerItem.getValue();
				int comoEsta = 0;
				if (comoEstaMap.get(comoDeveSerItem.getKey()) == null) {
					continue;
				} else {
					comoEsta = comoEstaMap.get(comoDeveSerItem.getKey());
				}

				System.out.print("Matrícula: " + matricula);
				System.out.print("; Como deve ser: " + comoDeveSer);
				System.out.print("; Como esta: " + comoEsta);

				// ja esta correto
				if (comoEsta == comoDeveSer) {
					System.out.println();
					continue;
				}

				// adicionar mensalidade
				if (comoEsta < comoDeveSer) {
					int mensalidadesParaIncluir = comoDeveSer - comoEsta;
					int debito = getIdDebito(conexao, matricula);

					for (int i = 0; i < mensalidadesParaIncluir; i++) {
						PreparedStatement ps = conexao
								.prepareStatement(strInsert);
						ps.setInt(1, 20);
						ps.setInt(2, debito);
						ps.setInt(3, 21);
						ps.executeUpdate();
					}

					System.out.print("; Adicionar: " + mensalidadesParaIncluir);
				}

				// remover mensalidade
				else if (comoEsta > comoDeveSer) {
					int mensalidadesParaExcluir = comoEsta - comoDeveSer;

					String debitoServico = getIdDebitoServico(conexao,
							matricula, mensalidadesParaExcluir);

					if(matricula == 1679){
						continue;
					}
					
					PreparedStatement ps = conexao.prepareStatement("DELETE FROM debitoservico WHERE id in ("+debitoServico+")");
					ps.executeUpdate();
					System.out.print("; Excluir: " + mensalidadesParaExcluir);
				}

				System.out.println();
			}
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
				conexao.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	private static HashMap<Integer, Integer> getComoEsta() throws IOException,
			BiffException {
		Workbook wb = Workbook
				.getWorkbook(new File("Associados_como_esta.xls"));
		Sheet sheet = wb.getSheet(0);
		HashMap<Integer, Integer> comoEsta = new HashMap<Integer, Integer>();

		for (int i = 0; i < sheet.getRows(); i++) {
			int matricula = Integer.parseInt(sheet.getCell(0, i).getContents());
			int situacao = Integer.parseInt(sheet.getCell(1, i).getContents());
			comoEsta.put(matricula, situacao);
		}
		return comoEsta;
	}

	private static HashMap<Integer, Integer> getComoDeveSer()
			throws IOException, BiffException {
		Workbook wb = Workbook.getWorkbook(new File(
				"Associados_como_deve_ser.xls"));
		Sheet sheet = wb.getSheet(0);
		HashMap<Integer, Integer> comoDeveSer = new HashMap<Integer, Integer>();

		for (int i = 0; i < sheet.getRows(); i++) {
			int matricula = Integer.parseInt(sheet.getCell(0, i).getContents());
			int situacao = Integer.parseInt(sheet.getCell(1, i).getContents());
			comoDeveSer.put(matricula, situacao);
		}
		return comoDeveSer;
	}

	private static int getIdDebito(Connection conexao, int matricula) {

		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			String sql = "select id from debito where cliente_id = ? "
					+ " order by id LIMIT 1 ";
			pstmt = conexao.prepareStatement(sql);
			pstmt.setInt(1, matricula);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				return rs.getInt("id");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	private static String getIdDebitoServico(Connection conexao,
			int matricula, int limit) {

		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			String sql = " select ds.id from debito d "
					+ " Inner Join debitoservico ds on ds.debito_id = d.id "
					+ " where cliente_id = ? and ds.servico_id = 21 "
					+ " order by ds.id LIMIT ? ";
			pstmt = conexao.prepareStatement(sql);
			pstmt.setInt(1, matricula);
			pstmt.setInt(2, limit);
			rs = pstmt.executeQuery();

			StringBuilder retorno = new StringBuilder();
			
			boolean primeiro = true;
			
			while(rs.next()){
				if(!primeiro){
					retorno.append(",");
				}
				retorno.append(rs.getString("id"));
				primeiro = false;
			}
			
			return retorno.toString();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
