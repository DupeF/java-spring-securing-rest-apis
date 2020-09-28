package io.jzheaux.springsecurity.resolutions;

import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ResolutionInitializer implements SmartInitializingSingleton {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private ResolutionRepository resolutionRepository;

	@Override
	public void afterSingletonsInstantiated() {
		this.resolutionRepository.save(new Resolution("Read War and Peace", "user"));
		this.resolutionRepository.save(new Resolution("Free Solo the Eiffel Tower", "user"));
		this.resolutionRepository.save(new Resolution("Hang Christmas Lights", "user"));
		
		User user = new User("user", "{bcrypt}$2a$10$MywQEqdZFNIYnx.Ro/VQ0ulanQAl34B5xVjK2I/SDZNVGS5tHQ08W");
		user.grantAuthority("resolution:read");
		user.grantAuthority("resolution:write");
		this.userRepository.save(user);
		
		User hasread = new User();
		hasread.setUsername("hasread");
		hasread.setPassword("{bcrypt}$2a$10$MywQEqdZFNIYnx.Ro/VQ0ulanQAl34B5xVjK2I/SDZNVGS5tHQ08W");
		hasread.grantAuthority("resolution:read");
		this.userRepository.save(hasread);
		
		User haswrite = new User();
		haswrite.setUsername("haswrite");
		haswrite.setPassword("{bcrypt}$2a$10$MywQEqdZFNIYnx.Ro/VQ0ulanQAl34B5xVjK2I/SDZNVGS5tHQ08W");
		haswrite.grantAuthority("resolution:write");
		this.userRepository.save(haswrite);
		
		User admin = new User("admin","{bcrypt}$2a$10$bTu5ilpT4YILX8dOWM/05efJnoSlX4ElNnjhNopL9aPoRyUgvXAYa");
		admin.grantAuthority("ROLE_ADMIN");
		this.userRepository.save(admin);
	}
}
