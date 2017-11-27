package fr.ekinci.restclient;

import fr.ekinci.client.models.account.AccountDto;
import fr.ekinci.client.models.user.UserDto;

import java.util.List;

/**
 * @author Gokan EKINCI
 */
public interface IClientManagementService {

	/**
	 * Create a new user (costumer) in the database
	 * @param user
	 * @return
	 */
	UserDto create(UserDto user);

	/**
	 * Update some information for given user id
	 *
	 * @param userId
	 * @param address
	 * @param homePhone
	 * @param cellPhone
	 */
	void updateInfo(String userId, String address, String homePhone, String cellPhone);

	/**
	 * Get user by id
	 *
	 * @param userId
	 * @return
	 */
	UserDto getUserById(String userId);

	/**
	 * Get users by name
	 *
	 * @param lastName
	 * @return
	 */
	List<UserDto> getUsersByLastName(String lastName);

	/**
	 * Create a new account for user
	 *
	 * @param userId
	 * @param account
	 * @return
	 */
	AccountDto addAccount(String userId, AccountDto account);
}
