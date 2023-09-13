package br.com.pokedex.dao.sql;

public abstract class DeleteSql {
	public static final String DELETE_POKEDEX
	= "DELETE FROM pokedex_.pokemon "
	+ "WHERE id = ?";
}
