package com.auth0.samples.authapi.user;

import com.auth0.samples.authapi.security.SecurityUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

import static com.auth0.samples.authapi.security.SecurityUtils.HEADER_STRING;
import static com.auth0.samples.authapi.security.SecurityUtils.TOKEN_PREFIX;

@RestController
@RequestMapping("/users")
public class UserController {

	private ApplicationUserRepository applicationUserRepository;
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	public UserController(ApplicationUserRepository applicationUserRepository,
						  BCryptPasswordEncoder bCryptPasswordEncoder) {
		this.applicationUserRepository = applicationUserRepository;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
	}

	@PostMapping("/sign-up")
	public void signUp(@RequestBody ApplicationUser user, HttpServletResponse res) {
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		ApplicationUser newUser = applicationUserRepository.save(user);
		res.addHeader(HEADER_STRING, TOKEN_PREFIX + " " + SecurityUtils.generateToken(newUser.getId(), newUser.getUsername()));
	}

	@GetMapping
	public String rootCall() {
		return "Worked";
	}

//	@PostMapping("/login")
//	public void login(@RequestBody ApplicationUser user, HttpServletResponse response) {
//		ApplicationUser appUser = applicationUserRepository.findByUsername(user.getUsername());
//
//		response.addHeader(HEADER_STRING, TOKEN_PREFIX + " " + SecurityUtils.generateToken(user.getUsername()));
//	}
}
