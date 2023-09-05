package com.testproject.blogapp;

import com.testproject.blogapp.model.Role;
import com.testproject.blogapp.model.UserAccountStatus;
import com.testproject.blogapp.model.UserEntity;
import com.testproject.blogapp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

@SpringBootApplication
@RequiredArgsConstructor
public class BlogAppApplication implements CommandLineRunner {
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;

	public static void main(String[] args) {
		SpringApplication.run(BlogAppApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Optional<UserEntity> optionalUserEntity = userRepository.findByEmail("admin@gmail.com");
		if(optionalUserEntity.isEmpty())
		{
			UserEntity userEntity = new UserEntity();
			userEntity.setEmail("admin@gmail.com");
			userEntity.setName("Admin");
			userEntity.setPassword(passwordEncoder.encode("admin"));
			userEntity.setRole(Role.ROLE_ADMIN);
			userEntity.setStatus(UserAccountStatus.VERIFIED);
			userRepository.save(userEntity);
		}
	}
}
