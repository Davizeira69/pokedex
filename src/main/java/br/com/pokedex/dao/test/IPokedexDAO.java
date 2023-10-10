package br.com.pokedex.dao.test;

import java.sql.Connection;
import java.util.List;

import br.com.pokedex.bean.PokedexBean;
import br.com.pokedex.dto.PokedexDto;

public interface IPokedexDAO {

	int insert(Connection conn, PokedexBean pokedexBean) throws Exception;

	List<PokedexDto> findAll(Connection conn) throws Exception;

	PokedexDto findById(Connection conn, int id) throws Exception;

	int update(Connection conn, PokedexBean pokedexBean) throws Exception;

	int delete(Connection conn, int id) throws Exception;
}
