package fr.ekinci.restclient;

import fr.ekinci.client.models.account.AccountDto;
import fr.ekinci.client.models.user.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * @author Gokan EKINCI
 */
@Service
public class ClientManagementService implements IClientManagementService {

	// @Value
	private String url;

	@Override
	public UserDto create(UserDto user) {
		return null; // restTemplate.exchange();
	}

	@Override
	public void updateInfo(String userId, String address, String homePhone, String cellPhone) {

	}

	@Override
	public UserDto getUserById(String userId) {
		return null;
	}

	@Override
	public List<UserDto> getUsersByLastName(String lastName) {
		return null;
	}

	@Override
	public AccountDto addAccount(String userId, AccountDto account) {
		return null;
	}
}
