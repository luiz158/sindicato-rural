package com.sindicato.test;

import java.util.List;

import javax.persistence.EntityManager;

import junit.framework.Assert;

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
import com.sindicato.result.ResultOperation;
import com.sindicato.seguranca.PasswordManager;

public class TestAutenticacao {

	private EntityManager em = EntityManagerFactorySingleton.getInstance()
			.createEntityManager();
	
	UsuarioDAO userDAO = new UsuarioDAOImpl(em);

	
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
		usuario.setSenha(PasswordManager.generated("123456"));
		usuario.getPerfis().add(perfil);
		usuarioDAO.insert(usuario);
	}
	
	@Test
	public void autenticacaoUsuario(){
		ResultOperation result;
		
		String userName = "teste";
		String senha = "teste";
		result = userDAO.autenticar(userName, senha);
		Assert.assertEquals(result.isSuccess(), false);
		Assert.assertEquals(result.getMessage(), "Usu�rio n�o existe");
		Assert.assertNull(userDAO.getUsuarioAutenticado());
		
		userName = "admin@admin.com.br";
		senha = "teste";
		result = userDAO.autenticar(userName, senha);
		Assert.assertEquals(result.isSuccess(), false);
		Assert.assertEquals(result.getMessage(), "Senha de usu�rio n�o confere");
		Assert.assertNull(userDAO.getUsuarioAutenticado());
		
		Usuario usuario = userDAO.getAll().get(0);
		result = userDAO.autenticar(usuario.getEmail(), "123456");
		Assert.assertEquals(result.isSuccess(), true);
		Assert.assertEquals(result.getMessage(), "Usu�rio autenticado");
		Assert.assertNotNull(userDAO.getUsuarioAutenticado());
	}
	
	@Test
	public void getMenusPermitidos(){
		Usuario usuario = userDAO.getAll().get(0);
		ResultOperation result = userDAO.autenticar(usuario.getEmail(), "123456");
		Assert.assertEquals(result.isSuccess(), true);
		
		List<Menu> menusPermitidos = userDAO.getMenusPermitidos();
		Assert.assertNotNull(menusPermitidos);
		Assert.assertEquals(menusPermitidos.size(), 2);
	}
	
}
