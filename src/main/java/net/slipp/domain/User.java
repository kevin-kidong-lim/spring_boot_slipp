package net.slipp.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class User {
	@Id
	@GeneratedValue  // auto sequence
	private Long id;
	@Column(nullable=false, length=20)
	private String userId;

	private String password;
	private String name;
	private String email;
	
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	@Override
	public String toString() {
		return "User [userId=" + userId + ", password=" + password + ", name=" + name + ", email=" + email + "]";
	}
	public void update(User updateUser) {
		// TODO Auto-generated method stub
		this.password = updateUser.password;
		this.name = updateUser.name;
		this.email = updateUser.email;
		
	}
	
	
}
