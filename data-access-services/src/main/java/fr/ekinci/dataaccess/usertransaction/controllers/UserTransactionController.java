package fr.ekinci.dataaccess.usertransaction.controllers;

import fr.ekinci.client.models.usertransaction.TransactionDto;
import fr.ekinci.dataaccess.usertransaction.exceptions.NotAuthorizedAccountTypeException;
import fr.ekinci.dataaccess.usertransaction.services.UserTransactionDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author Gokan EKINCI
 */
@RestController
@RequestMapping(path = "/transactions")
public class UserTransactionController {

	private final UserTransactionDAO userTransactionDAO;

	@Autowired
	public UserTransactionController(UserTransactionDAO userTransactionDAO) {
		this.userTransactionDAO = userTransactionDAO;
	}

	/**
	 * For client-service
	 */
	@RequestMapping(path = "/user/{id}", method = RequestMethod.GET)
	public ResponseEntity<Page<TransactionDto>> getTransactions() {
		return new ResponseEntity<>(
			userTransactionDAO.getPagination(
				new PageRequest(0, 20, new Sort(Sort.Direction.DESC, "date")))
				, HttpStatus.OK
			);
	}

	/**
	 * For client-service
	 */
	@RequestMapping(path = "/credit/users/{id}", method = RequestMethod.POST)
	public ResponseEntity<TransactionDto> credit(@RequestBody TransactionDto transactionDto) {
		return new ResponseEntity<>(userTransactionDAO.credit(transactionDto), HttpStatus.OK);
	}

	/**
	 * For client-service
	 */
	@RequestMapping(path = "/debit/users/{id}", method = RequestMethod.POST)
	public ResponseEntity<TransactionDto> debit(@RequestBody TransactionDto transactionDto) {
		return new ResponseEntity<>(userTransactionDAO.debit(transactionDto), HttpStatus.OK);
	}

	@ExceptionHandler(NotAuthorizedAccountTypeException.class)
	public ResponseEntity<?> notAuthorizedAccountTypeExceptionHandler(NotAuthorizedAccountTypeException exception) {
		return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
	}
}
