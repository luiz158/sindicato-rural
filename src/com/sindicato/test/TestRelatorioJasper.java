package com.sindicato.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManager;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import com.sindicato.dao.DebitoDAO;
import com.sindicato.dao.EntityManagerFactorySingleton;
import com.sindicato.dao.impl.DebitoDAOImpl;
import com.sindicato.entity.Debito;

public class TestRelatorioJasper {

	public static void main(String[] args) throws JRException {
		new TestRelatorioJasper().gerarRelatorio();
	}

	public void gerarRelatorio(){
		EntityManager em = EntityManagerFactorySingleton.getInstance()
				.createEntityManager();
		DebitoDAO debidoDAO = new DebitoDAOImpl(em);
		Debito debito = debidoDAO.searchByID(1);

		File file = new File("E:/Alysson/Projetos/Sindicato/ControleFinanceiro/reports/notaCobranca.jasper");  
        
		InputStream inputStream = null;
        try {  
            // apontei para o arquivo  
            inputStream = new FileInputStream(file);  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
		
		JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(
				debito.getDebitoServicos());

		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("teste", "123456");
		parameters.put("teste2", "987654");

		try {
			JasperPrint print = JasperFillManager.fillReport(inputStream, parameters,
					beanColDataSource);
			
			JasperExportManager.exportReportToPdfFile(print, "E:/Alysson/Projetos/Sindicato/ControleFinanceiro/reports/notaCobranca.pdf");
			
		} catch (JRException e) {
			e.printStackTrace();
		}
	}
	
}
