package br.com.pokedex.dto;

import java.io.Serializable;

public class ResponseDto<T> implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private T data;
	private String errors;
	
	public ResponseDto(T data, String errors) {
		this.data = data;
		this.errors = errors;
	}
	
	public ResponseDto() {}
	
	public T getData() {
		return data;
	}
	public void setData(T data) {
		this.data = data;
	}
	public String getErrors() {
		return errors;
	}
	public void setErrors(String errors) {
		this.errors = errors;
	}
}
