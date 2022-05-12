package aiss.model;

import java.security.SecureRandom;

public class User {
	
	private String id;
	private String name;
	private String email;
	private String pasword;
	private String token;
	private String address;
	
	public class SecureToken {
		
		public static final String CHARACTERS = "/qwertyuiopasdfghjkl?zxcvbnmQWERTYUIOPASDFGHJKL@ZXCVBNM1234567890!";
		
		public static final int SECURE_TOKEN_LENGTH = 20;
		
		private final SecureRandom random = new SecureRandom();
		
		private final char[] symbols = CHARACTERS.toCharArray();
		
		private final char[] buf = new char[SECURE_TOKEN_LENGTH];
		
		public String nextToken() {
			for(int idx=0; idx < buf.length; idx++) {
				buf[idx] = symbols[random.nextInt(symbols.length)];
			}
			return new String(buf);
		}
	}
	
	public User() {
	}

	public User(String name, String email, String pasword, String token, String address) {
		super();
		this.name = name;
		this.email = email;
		this.pasword = pasword;
		SecureToken tokenAux = new SecureToken();
		this.token = tokenAux.nextToken();
		this.address = address;
	}

	public User(String id, String name, String email, String pasword, String token, String address) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
		this.pasword = pasword;
		SecureToken tokenAux = new SecureToken();
		this.token = tokenAux.nextToken();
		this.address = address;
	}



	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPasword() {
		return pasword;
	}

	public void setPasword(String pasword) {
		this.pasword = pasword;
	}

	public String getToken() {
		return token;
	}

	public void   setToken(String token) {
		this.token = token;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
	

}