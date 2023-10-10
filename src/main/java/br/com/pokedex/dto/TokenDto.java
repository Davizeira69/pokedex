package br.com.pokedex.dto;

import java.io.Serializable;
import java.util.Objects;

public class TokenDto implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String userName;
	private String password;
	private String accessToken;
	
	public TokenDto(String userName, String password) {
		this.userName = userName;
		this.password = password;
	}
	
	public TokenDto() {};
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getAccessToken() {
		return accessToken;
	}
	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}
	@Override
	public int hashCode() {
		return Objects.hash(accessToken, password, userName);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TokenDto other = (TokenDto) obj;
		return Objects.equals(accessToken, other.accessToken) && Objects.equals(password, other.password)
				&& Objects.equals(userName, other.userName);
	}
}
