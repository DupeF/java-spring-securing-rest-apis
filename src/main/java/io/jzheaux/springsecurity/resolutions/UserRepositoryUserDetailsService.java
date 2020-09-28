package io.jzheaux.springsecurity.resolutions;

import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class UserRepositoryUserDetailsService implements UserDetailsService {
	
	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return this.userRepository.findByUsername(username)
				.map(BridgeUser::new)
				.orElseThrow(() -> new UsernameNotFoundException("invalid user"));
	}
	
	private static class BridgeUser extends User implements UserDetails {
		public BridgeUser(User user) {
			super(user);
		}

		@Override
		public Collection<? extends GrantedAuthority> getAuthorities() {
			return this.getUserAuthorities().stream()
					.map(UserAuthority::getAuthority)
					.map(SimpleGrantedAuthority::new)
					.collect(Collectors.toList());
		}

		@Override
		public boolean isAccountNonExpired() {
			return this.isEnabled();
		}

		@Override
		public boolean isAccountNonLocked() {
			return this.isEnabled();
		}

		@Override
		public boolean isCredentialsNonExpired() {
			return this.isEnabled();
		}
	}
	
}
