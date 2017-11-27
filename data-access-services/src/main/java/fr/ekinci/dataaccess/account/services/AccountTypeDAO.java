package fr.ekinci.dataaccess.account.services;

import fr.ekinci.client.models.account.AccountTypeDto;
import fr.ekinci.dataaccess.account.entities.AccountTypeEntity;
import fr.ekinci.dataaccess.account.repositories.AccountTypeRepository;
import fr.ekinci.restserver.utils.AbstractDAO;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Gokan EKINCI
 */
@Service
public class AccountTypeDAO extends AbstractDAO<AccountTypeDto, AccountTypeEntity, Long> {

	@Autowired
	public AccountTypeDAO(Mapper dozer, AccountTypeRepository accountTypeRepository) {
		super(dozer, AccountTypeDto.class, AccountTypeEntity.class, accountTypeRepository);
	}
}
