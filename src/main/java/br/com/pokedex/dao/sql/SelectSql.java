package br.com.pokedex.dao.sql;

public abstract class SelectSql {
	public static final String SELECT_POKEDEX
	= "SELECT "
	+ "id, "
	+ "dex_number, "
	+ "name, "
	+ "type_id, "
	+ "second_type_id "
	+ "FROM pokedex_.pokemon";
	
	public static final String SELECT_POKEDEX_BY_ID
	= SELECT_POKEDEX + " WHERE id = ?";
}
