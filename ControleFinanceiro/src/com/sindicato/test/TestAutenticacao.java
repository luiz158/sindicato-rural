package com.sindicato.test;

import javax.persistence.EntityManager;

import org.junit.BeforeClass;
import org.junit.Test;

import com.sindicato.dao.EntityManagerFactorySingleton;
import com.sindicato.dao.MenuDAO;
import com.sindicato.dao.PerfilDAO;
import com.sindicato.dao.UsuarioDAO;
import com.sindicato.dao.impl.MenuDAOImpl;
import com.sindicato.dao.impl.PerfilDAOImpl;
import com.sindicato.dao.impl.UsuarioDAOImpl;
import com.sindicato.entity.autenticacao.Menu;
import com.sindicato.entity.autenticacao.Perfil;
import com.sindicato.entity.autenticacao.Usuario;

public class TestAutenticacao {

	private EntityManager em = EntityManagerFactorySingleton.getInstance()
			.createEntityManager();

	
	@BeforeClass
	public static void popula() {
		EntityManager em = EntityManagerFactorySingleton.getInstance()
				.createEntityManager();
		
		MenuDAO menuDAO = new MenuDAOImpl(em);

		Menu menupai = new Menu();
		menupai.setDescricao("Cadastros");
		menupai.setUrl("cadastros");
		menuDAO.insert(menupai);
		
		Menu menu = new Menu();
		menu.setDescricao("Cadastrar cliente");
		menu.setUrl("cadCliente");
		menu.setMenuPai(menupai);
		menuDAO.insert(menu);
		
		PerfilDAO perfilDAO = new PerfilDAOImpl(em);

		Perfil perfil = new Perfil();
		perfil.setDescricao("Administrador");
		perfil.getMenus().add(menupai);
		perfil.getMenus().add(menu);
		perfilDAO.insert(perfil);
		
		UsuarioDAO usuarioDAO = new UsuarioDAOImpl(em);
		
		Usuario usuario = new Usuario();
		usuario.setNome("Admin");
		usuario.setEmail("admin@admin.com.br");
		usuario.setSenha("123456");
		usuario.getPerfis().add(perfil);
		usuarioDAO.insert(usuario);
		
		
	}

	
	@Test
	public void teste(){
		
	}
}
