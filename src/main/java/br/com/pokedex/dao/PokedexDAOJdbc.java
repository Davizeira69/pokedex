package br.com.pokedex.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

import br.com.pokedex.bean.PokedexBean;
import br.com.pokedex.dao.sql.DeleteSql;
import br.com.pokedex.dao.sql.InsertSql;
import br.com.pokedex.dao.sql.SelectSql;
import br.com.pokedex.dao.sql.UpdateSql;
import br.com.pokedex.dto.PokedexDto;
import br.com.pokedex.exceptions.ConflictException;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class PokedexDAOJdbc extends PokedexDAOHelper implements IPokedexDAO {

	@Override
	public int insert(Connection conn, PokedexBean pokedexBean) throws Exception {
		PreparedStatement pst = null;
		ResultSet rs = null;
		int id = 0;
		try {
			pst = conn.prepareStatement(
				InsertSql.INSERT_POKEDEX, PreparedStatement.RETURN_GENERATED_KEYS
			);
			
			pst.setInt(1, pokedexBean.getDexNumber());
			pst.setString(2, pokedexBean.getName());
			pst.setInt(3, pokedexBean.getTypeId());
			pst.setInt(4, pokedexBean.getSecondTypeId());
			pst.setObject(5, pokedexBean.getDthrCreate());
			pst.setObject(6, pokedexBean.getDthrUpdate());
			pst.execute();
			rs = pst.getGeneratedKeys();
			
			if (rs.next()) {
				id = rs.getInt(1);
			}
			
			pst.close();
			rs.close();
		} catch (SQLIntegrityConstraintViolationException e) {
			throw new ConflictException(e.getMessage());
		} catch (Exception e) {
			throw e;
		}
		return id;
	}

	@Override
	public List<PokedexDto> findAll(Connection conn) throws Exception {
		PreparedStatement pst = null;
		ResultSet rs = null;
		List<PokedexDto> pokedexDtos = new ArrayList<>();
		
		try {
			pst = conn.prepareStatement(SelectSql.SELECT_POKEDEX);
			rs = pst.executeQuery();
			
			while (rs.next()) {
				pokedexDtos.add(getFieldsFromDb(rs));
			}
			
			pst.close();
			rs.close();
			
		} catch (Exception e) {
			throw e;
		}
		return pokedexDtos;
	}

	@Override
	public PokedexDto findById(Connection conn, int id) throws Exception {
		PreparedStatement pst = null;
		ResultSet rs = null;
		PokedexDto pokedexDto = new PokedexDto();
		
		try {
			pst = conn.prepareStatement(SelectSql.SELECT_POKEDEX_BY_ID);
			pst.setInt(1, id);
			rs = pst.executeQuery();
			
			if (rs.next()) {
				pokedexDto = getFieldsFromDb(rs);
			}
			
			pst.close();
			rs.close();
			
		} catch (Exception e) {
			throw e;
		}
		return pokedexDto;
	}

	@Override
	public int update(Connection conn, PokedexBean pokedexBean) throws Exception {
		PreparedStatement pst = null;
		int affectedRows = 0;
		try {
			pst = conn.prepareStatement(UpdateSql.UPDATE_POKEDEX);
			
			pst.setInt(1, pokedexBean.getDexNumber());
			pst.setString(2, pokedexBean.getName());
			pst.setInt(3, pokedexBean.getTypeId());
			pst.setInt(4, pokedexBean.getSecondTypeId());
			pst.setObject(5, pokedexBean.getDthrUpdate());
			pst.setLong(6, pokedexBean.getId());
			affectedRows = pst.executeUpdate();

			pst.close();
		} catch (SQLIntegrityConstraintViolationException e) {
			throw new ConflictException(e.getMessage());
		} catch (Exception e) {
			throw e;
		}
		return affectedRows;		
	}

	@Override
	public int delete(Connection conn, int id) throws Exception {
		PreparedStatement pst = null;
		int affectedRows = 0;
		try {
			pst = conn.prepareStatement(DeleteSql.DELETE_POKEDEX);
			
			pst.setInt(1, id);
			affectedRows = pst.executeUpdate();

			pst.close();
		} catch (Exception e) {
			throw e;
		}
		return affectedRows;
	}

}
