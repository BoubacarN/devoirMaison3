package fr.ekinci.dataaccess.account.controllers;

import fr.ekinci.client.models.account.AccountTypeDto;
import fr.ekinci.dataaccess.account.services.AccountTypeDAO;
import fr.ekinci.restserver.utils.AbstractController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Gokan EKINCI
 */
@RestController
@RequestMapping(path = "/account-types")
public class AccountTypeController extends AbstractController<AccountTypeDto, Long, AccountTypeDAO> {

	@Autowired
	public AccountTypeController(AccountTypeDAO dao) {
		super(dao);
	}
}
