package fr.ekinci.dataaccess.account.exceptions;


/**
 * @author Gokan EKINCI
 */
public class AccountTypeAlreadyExistsForClientException extends RuntimeException {

	public AccountTypeAlreadyExistsForClientException() {
		super("Cannot create same account more than once for one user");
	}
}
