package br.com.pokedex.dao.sql;

public abstract class InsertSql {
	public static final String INSERT_POKEDEX
	= "INSERT INTO pokemon "
	+ "(dex_number, "
	+ "name, "
	+ "type_id, "
	+ "second_type_id, "
	+ "dthr_create, "
	+ "dthr_update) "
	+ "VALUES "
	+ "(?, ?, ?, ?, ?, ?)";
}
