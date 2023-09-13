package br.com.pokedex.dto;

import java.io.Serializable;

public class PokedexDto implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private int id;
	private Integer dexNumber;
	private String name;
	private Integer typeId;
	private Integer secondTypeId;
	
	public PokedexDto() {}
	
	public PokedexDto(int id, Integer dexNumber, String name, Integer typeId, Integer secondTypeId) {
		super();
		this.id = id;
		this.dexNumber = dexNumber;
		this.name = name;
		this.typeId = typeId;
		this.secondTypeId = secondTypeId;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Integer getDexNumber() {
		return dexNumber;
	}
	public void setDexNumber(Integer dexNumber) {
		this.dexNumber = dexNumber;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getTypeId() {
		return typeId;
	}
	public void setTypeId(Integer typeId) {
		this.typeId = typeId;
	}
	public Integer getSecondTypeId() {
		return secondTypeId;
	}
	public void setSecondTypeId(Integer secondTypeId) {
		this.secondTypeId = secondTypeId;
	}
}
