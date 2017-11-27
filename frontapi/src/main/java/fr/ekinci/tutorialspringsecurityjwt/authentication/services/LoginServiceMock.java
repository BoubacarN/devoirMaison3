package fr.ekinci.tutorialspringsecurityjwt.authentication.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import fr.ekinci.dataaccess.user.entities.UserEntity;
import fr.ekinci.dataaccess.user.repositories.UserRepository;
import fr.ekinci.tutorialspringsecurityjwt.authentication.models.LoginRequest;
import fr.ekinci.tutorialspringsecurityjwt.authentication.models.LoginResponse;
import fr.ekinci.tutorialspringsecurityjwt.commons.models.Profile;
import fr.ekinci.tutorialspringsecurityjwt.security.models.Session;
import fr.ekinci.tutorialspringsecurityjwt.security.services.IJwtService;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * TODO : Create a real LoginService
 *
 * @author Gokan EKINCI
 */
@Primary
@Service
public class LoginServiceMock implements ILoginService {
	private final static String LOG_HEADER = "[LOGIN][SERVICE]";

	UserRepository userRepository;

	public LoginServiceMock(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	private  String host;
	private  RestTemplate restTemplate;
	private  Mapper dozer;
	private  ObjectMapper mapper;
	private  IJwtService jwtService;

	public LoginServiceMock(
			// String host,
			RestTemplate restTemplate,
			Mapper dozer,
			ObjectMapper mapper,
			IJwtService jwtService


	) {
		this.host = null;
		this.restTemplate = restTemplate;
		this.dozer = dozer;
		this.mapper = mapper;
		this.jwtService = jwtService;
	}

	@Override
	public LoginResponse login(LoginRequest loginRequest, String id) {

		UserEntity userEntity= userRepository.eagerFetchUserById(Long.parseLong(id));
		LoginResponse loginResponse;

		//j'essai de le connecté avec un user existant
		if(userEntity.getId()!=null){

			loginResponse = LoginResponse.builder()
					.guid(Long.toString(userEntity.getId()))
					.passwordExpired(false)
					.profile(
							Profile.builder()
									.firstName(userEntity.getFirstName())
									.lastName(userEntity.getLastName())
									.email("toto.lennon@gmail.com")
									//.role(userEntity.getRole())
									.build()
					)
					.build();
		}
//mock si le client n'existe pas
		else {
			loginResponse = LoginResponse.builder()
					.guid("MOCK_GUID")
					.passwordExpired(false)
					.profile(
							Profile.builder()
									.email("john.lennon@gmail.com")
									.firstName("John")
									.lastName("LENNON")
									.role("cons")
									.build()
					)
					.build();
		}
		try {
			loginResponse.setToken(
					jwtService.sign(
							mapper.writeValueAsString(
									dozer.map(loginResponse, Session.class)
							)
					)
			);
		} catch (JsonProcessingException e) {
			throw new RuntimeException(
					String.format("%s An error happened during transforming object to JSON", LOG_HEADER),
					e
			);
		}

		return loginResponse;
	}



}
