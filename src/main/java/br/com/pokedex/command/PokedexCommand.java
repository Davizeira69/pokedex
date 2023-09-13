package br.com.pokedex.command;

import java.sql.Connection;
import java.util.List;

import br.com.pokedex.bean.PokedexBean;
import br.com.pokedex.config.MySQLConfig;
import br.com.pokedex.dao.IPokedexDAO;
import br.com.pokedex.dto.PokedexDto;
import io.quarkus.logging.Log;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class PokedexCommand {

	@Inject
	IPokedexDAO pokedexDAO;

	public int create(PokedexBean bean) throws Exception {
		Connection conn = MySQLConfig.getConnection();
		int id = 0;

		try {
			id = pokedexDAO.insert(conn, bean);
			conn.commit();
		} catch (Exception e) {
			Log.error(e.getMessage());
			conn.rollback();
			throw e;
		} finally {
			conn.close();
		}
		return id;
	}
	
	public List<PokedexDto> findAll() throws Exception {
		Connection conn = MySQLConfig.getConnection();
		List<PokedexDto> pokedexDtos = null;
		
		try {
			pokedexDtos = pokedexDAO.findAll(conn);
		} catch (Exception e) {
			Log.error(e.getMessage());
			conn.rollback();
			throw e;
		} finally {
			conn.close();
		}
		return pokedexDtos;
	}

	public PokedexDto findById(int id) throws Exception {
		Connection conn = MySQLConfig.getConnection();
		PokedexDto dto;

		try {
			dto = pokedexDAO.findById(conn, id);
		} catch (Exception e) {
			Log.error(e.getMessage());
			conn.rollback();
			throw e;
		} finally {
			conn.close();
		}
		return dto;
	}
	
	public int update(PokedexBean bean) throws Exception {
		Connection conn = MySQLConfig.getConnection();
		int affectedRows = 0;

		try {
			affectedRows = pokedexDAO.update(conn, bean);
			conn.commit();
		} catch (Exception e) {
			Log.error(e.getMessage());
			conn.rollback();
			throw e;
		} finally {
			conn.close();
		}
		return affectedRows;
	}
	
	public int delete(int id) throws Exception {
		Connection conn = MySQLConfig.getConnection();
		int affectedRows = 0;
		try {
			affectedRows = pokedexDAO.delete(conn, id);
			conn.commit();
		} catch (Exception e) {
			Log.error(e.getMessage());
			conn.rollback();
			throw e;
		} finally {
			conn.close();
		}
		return affectedRows;
	}
}