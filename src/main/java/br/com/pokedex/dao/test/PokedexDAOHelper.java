package br.com.pokedex.dao.test;

import java.sql.ResultSet;

import br.com.pokedex.dto.PokedexDto;

public abstract class PokedexDAOHelper {
	
	protected PokedexDto getFieldsFromDb(ResultSet rs) throws Exception{
		PokedexDto pokedexDto = new PokedexDto();
		
		pokedexDto.setId(rs.getInt("id"));
		pokedexDto.setDexNumber(rs.getInt("dex_number"));
		pokedexDto.setName(rs.getString("name"));
		pokedexDto.setTypeId(rs.getInt("type_id"));
		pokedexDto.setSecondTypeId(rs.getInt("second_Type_id"));
		
		return pokedexDto;
	}
}
