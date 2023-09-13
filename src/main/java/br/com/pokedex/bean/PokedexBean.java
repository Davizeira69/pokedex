package br.com.pokedex.bean;

import java.io.Serializable;
import java.util.Objects;

public class PokedexBean implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private int id;
	private int dexNumber;
	private String name;
	private int typeId;
	private int secondTypeId;
	private String dthrCreate;
	private String dthrUpdate;
	
	public PokedexBean(int id, int dexNumber, String name, int typeId, int secondTypeId) {
		super();
		this.id = id;
		this.dexNumber = dexNumber;
		this.name = name;
		this.typeId = typeId;
		this.secondTypeId = secondTypeId;
	}
	
	public PokedexBean() {}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getDexNumber() {
		return dexNumber;
	}
	public void setDexNumber(int dexNumber) {
		this.dexNumber = dexNumber;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getTypeId() {
		return typeId;
	}
	public void setTypeId(int typeId) {
		this.typeId = typeId;
	}
	public int getSecondTypeId() {
		return secondTypeId;
	}
	public void setSecondTypeId(int secondTypeId) {
		this.secondTypeId = secondTypeId;
	}
	public String getDthrCreate() {
		return dthrCreate;
	}
	public void setDthrCreate(String dthrCreate) {
		this.dthrCreate = dthrCreate;
	}
	public String getDthrUpdate() {
		return dthrUpdate;
	}
	public void setDthrUpdate(String dthrUpdate) {
		this.dthrUpdate = dthrUpdate;
	}

	@Override
	public int hashCode() {
		return Objects.hash(dexNumber, dthrCreate, dthrUpdate, id, name, secondTypeId, typeId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PokedexBean other = (PokedexBean) obj;
		return dexNumber == other.dexNumber && Objects.equals(dthrCreate, other.dthrCreate)
				&& Objects.equals(dthrUpdate, other.dthrUpdate) && id == other.id && Objects.equals(name, other.name)
				&& secondTypeId == other.secondTypeId && typeId == other.typeId;
	}
	
	
}
