package br.com.pokedex.dao.sql;

public abstract class UpdateSql {
	public static final String UPDATE_POKEDEX
	= "UPDATE pokedex_.pokemon "
	+ "SET "
	+ "dex_number = ?, "
	+ "name = ?, "
	+ "type_id = ?, "
	+ "second_type_id = ?, "
	+ "dthr_update = ? "
	+ "WHERE id = ?";
}
