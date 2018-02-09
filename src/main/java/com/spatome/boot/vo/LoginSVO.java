package com.spatome.boot.vo;

import java.io.Serializable;

public class LoginSVO implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long id;
	private String token;

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
}
