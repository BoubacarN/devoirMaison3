package fr.ekinci.dataaccess.account.controllers;

import fr.ekinci.client.models.account.AccountDto;
import fr.ekinci.client.models.user.UserDto;
import fr.ekinci.dataaccess.account.exceptions.AccountTypeAlreadyExistsForClientException;
import fr.ekinci.dataaccess.account.exceptions.AdultTryToCreateLivreJeuneException;
import fr.ekinci.dataaccess.account.services.AccountDAO;
import fr.ekinci.dataaccess.user.services.UserDAO;
import fr.ekinci.restserver.exceptions.ExceptionMessage;
import fr.ekinci.restserver.utils.AbstractController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author Gokan EKINCI
 */
@RestController
@RequestMapping(path = "/accounts")
public class AccountController extends AbstractController<AccountDto, Long, AccountDAO> {

	private final AccountDAO accountDAO;

	@Autowired
	public AccountController(AccountDAO dao) {
		super(dao);
		this.accountDAO = dao;
	}

	@Override
	@RequestMapping(path = "", method = RequestMethod.POST)
	public ResponseEntity<AccountDto> create(@RequestBody AccountDto dto) {
		// Check if account exists for given user
		accountDAO.checkIfAccountExists(dto);

		// Check if user is an adult
		accountDAO.checkIfAdultTryToCreateLivreJeune(dto);

		return super.create(dto);
	}

	@ExceptionHandler(AccountTypeAlreadyExistsForClientException.class)
	public ResponseEntity<ExceptionMessage> accountTypeAlreadyExistsForClientExceptionHandler(
		AccountTypeAlreadyExistsForClientException exception
	) {
		return new ResponseEntity<>(
			ExceptionMessage.builder().message(exception.getMessage()).build(),
			HttpStatus.NOT_ACCEPTABLE
		);
	}

	@ExceptionHandler(AdultTryToCreateLivreJeuneException.class)
	public ResponseEntity<ExceptionMessage> adultTryToCreateLivreJeuneExceptionHandler(
			AccountTypeAlreadyExistsForClientException exception
	) {
		return new ResponseEntity<>(
			ExceptionMessage.builder().message(exception.getMessage()).build(),
			HttpStatus.NOT_ACCEPTABLE
		);
	}
}
