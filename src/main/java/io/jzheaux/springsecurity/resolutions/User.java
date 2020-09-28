package io.jzheaux.springsecurity.resolutions;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Set;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity(name="users")
public class User implements Serializable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID id;
	
	@Column
	private String username;
	
	@Column
	private String password;
	
	@Column
	private boolean enabled = true;

    @OneToMany(mappedBy = "user", fetch=FetchType.EAGER, cascade=CascadeType.ALL)
	private Collection<UserAuthority> userAuthorities = new ArrayList<UserAuthority>();
	
	public User() {}
	
	public User(String username, String password) {
		this.username = username;
		this.password = password;
	}
	
	public User(User user) {
		this.id = user.id;
		this.username = user.username;
		this.password = user.password;
		this.userAuthorities = user.userAuthorities;
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	
	public Collection<UserAuthority> getUserAuthorities() {
		return Collections.unmodifiableCollection(this.userAuthorities);
	}
	
	public void grantAuthority(String authority) {
		UserAuthority userAuthority = new UserAuthority(this, authority);
		this.userAuthorities.add(userAuthority);
	}
}
