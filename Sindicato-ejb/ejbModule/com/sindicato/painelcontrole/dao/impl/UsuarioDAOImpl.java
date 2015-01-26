package com.sindicato.painelcontrole.dao.impl;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateful;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import com.sindicato.dao.impl.DAOImpl;
import com.sindicato.painelcontrole.dao.UsuarioDAO;
import com.sindicato.painelcontrole.entity.Acao;
import com.sindicato.painelcontrole.entity.Menu;
import com.sindicato.painelcontrole.entity.Modulo;
import com.sindicato.painelcontrole.entity.Perfil;
import com.sindicato.painelcontrole.entity.Usuario;
import com.sindicato.result.ResultOperation;
import com.sindicato.util.PasswordManager;
import com.uaihebert.factory.EasyCriteriaFactory;
import com.uaihebert.model.EasyCriteria;

@Stateful
public class UsuarioDAOImpl extends DAOImpl<Usuario, Integer> implements UsuarioDAO {

	private Usuario usuarioAutenticado = null;
	
	@Override
	public void insert(Usuario usuario){
		usuario.setSenha(PasswordManager.generated(usuario.getSenha()));
		super.insert(usuario);
	}
	
	@Override
	public ResultOperation autenticar(String usuario, String senha) {
		EasyCriteria<Usuario> buscaUsuario = EasyCriteriaFactory.createQueryCriteria(em, Usuario.class);
		buscaUsuario.andEquals("email", usuario);
		
		ResultOperation result = new ResultOperation();
		try {
			usuarioAutenticado = buscaUsuario.getSingleResult();
			if(!usuarioAutenticado.isAtivo()){
				result.setMessage("Usuário desativado");
				result.setSuccess(false);
			}else if(usuarioAutenticado.getSenha().equals(PasswordManager.generated(senha))){
				result.setMessage("Usuário autenticado");
				result.setSuccess(true);
				
				this.carregaDadosAcesso();
			}else{
				result.setMessage("Senha de usuário não confere");
				result.setSuccess(false);
				usuarioAutenticado = null;
			}
			return result;
		} catch (NoResultException e) {
			result.setMessage("Usuário não existe");
			result.setSuccess(false);
		}
		return result;
	}
	private void carregaDadosAcesso() {
		EasyCriteria<Perfil> query = EasyCriteriaFactory.createQueryCriteria(em, Perfil.class);
		query.setDistinctTrue();
		query.innerJoinFetch("usuario");
		query.andEquals("usuario.id", usuarioAutenticado.getId());
		List<Perfil> perfis = query.getResultList();
		
		for (Perfil perfil : perfis) {
			// carrega os menus do perfil
			EasyCriteria<Menu> buscaMenus = EasyCriteriaFactory.createQueryCriteria(em, Menu.class);
			buscaMenus.setDistinctTrue();
			buscaMenus.innerJoinFetch("perfil");
			buscaMenus.andEquals("perfil.id", perfil.getId());
			perfil.setMenus(buscaMenus.getResultList());
			
			// carrega as acoes do perfil
			EasyCriteria<Acao> buscaAcoes = EasyCriteriaFactory.createQueryCriteria(em, Acao.class);
			buscaAcoes.setDistinctTrue();
			buscaAcoes.innerJoinFetch("perfil");
			buscaAcoes.andEquals("perfil.id", perfil.getId());
			perfil.setAcoes(buscaAcoes.getResultList());
		}
		usuarioAutenticado.setPerfis(perfis);
	}

	
	@Override
	public Usuario getUsuarioAutenticado() {
		return usuarioAutenticado;
	}
	
	@Override
	public List<Menu> getMenusPermitidos() {
		if(usuarioAutenticado == null){
			return null;
		}
		String strQuery = "select p from Perfil p " + 
			" join fetch p.menus " +
			" where p in (:perfis)";
		TypedQuery<Perfil> query = em.createQuery(strQuery, Perfil.class);
		query.setParameter("perfis", usuarioAutenticado.getPerfis());
		try {
			List<Menu> menus = new ArrayList<Menu>();
			List<Perfil> perfis = query.getResultList();
			for (Perfil perfil : perfis) {
				menus.addAll(perfil.getMenus());
			}
			return menus;
		} catch (NoResultException e) {
			return null;
		}
	}

	@Override
	public List<Modulo> extraiModulosPermitidos(List<Perfil> perfis){
		List<Modulo> modulos = new ArrayList<Modulo>();
		for (Perfil perfil : perfis) {
			for (Menu menu : perfil.getMenus()) {
				if(modulos.contains(menu.getModulo()) == false){
					modulos.add(menu.getModulo());
				}
			}
		}
		return modulos;
	}

	@Override
	public List<Acao> getAcoesPermitidas() {
		
		
		
		return null;
	}

	
	
}