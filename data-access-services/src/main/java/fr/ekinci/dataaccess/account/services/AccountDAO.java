package fr.ekinci.dataaccess.account.services;

import fr.ekinci.client.models.account.AccountDto;
import fr.ekinci.client.models.account.AccountTypeDto;
import fr.ekinci.client.models.user.UserDto;
import fr.ekinci.dataaccess.account.entities.AccountEntity;
import fr.ekinci.dataaccess.account.entities.AccountTypeEntity;
import fr.ekinci.dataaccess.account.exceptions.AccountTypeAlreadyExistsForClientException;
import fr.ekinci.dataaccess.account.exceptions.AdultTryToCreateLivreJeuneException;
import fr.ekinci.dataaccess.account.repositories.AccountRepository;
import fr.ekinci.dataaccess.account.repositories.AccountTypeRepository;
import fr.ekinci.dataaccess.configurations.dozer.LocalDate_Date_DozerConverter;
import fr.ekinci.dataaccess.user.entities.UserEntity;
import fr.ekinci.dataaccess.user.repositories.UserRepository;
import fr.ekinci.restserver.utils.AbstractDAO;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

/**
 * @author Gokan EKINCI
 */
@Service
public class AccountDAO extends AbstractDAO<AccountDto, AccountEntity, Long> {

	private final AccountRepository accountRepository;
	private final AccountTypeRepository accountTypeRepository;
	private final UserRepository userRepository;

	@Autowired
	public AccountDAO(
		Mapper dozer,
		AccountRepository accountRepository,
		AccountTypeRepository accountTypeRepository,
		UserRepository userRepository
	) {
		super(dozer, AccountDto.class, AccountEntity.class, accountRepository);
		this.accountRepository = accountRepository;
		this.accountTypeRepository = accountTypeRepository;
		this.userRepository = userRepository;
	}

	@Override
	public AccountDto add(AccountDto dto) {
		final AccountDto eagerAccountDto = super.add(dto);
		eagerAccountDto.setUser(dozer.map(
			userRepository.findOne(Long.parseLong(eagerAccountDto.getUser().getId())),
			UserDto.class
		));
		eagerAccountDto.setType(dozer.map(
			accountTypeRepository.findOne(Long.parseLong(eagerAccountDto.getType().getId())),
			AccountTypeDto.class
		));
		return eagerAccountDto;
	}

	public void checkIfAccountExists(AccountDto dto) {
		final Long userId = Long.parseLong(dto.getUser().getId());
		final Long typeId = Long.parseLong(dto.getType().getId());

		if (accountRepository.checkIfAccountExists(userId, typeId).size() > 0) {
			throw new AccountTypeAlreadyExistsForClientException();
		}
	}

	public void checkIfAdultTryToCreateLivreJeune(AccountDto dto) {
		AccountTypeEntity accountTypeEntity = accountTypeRepository.findOne(Long.parseLong(dto.getType().getId()));
		if ("J".equals(accountTypeEntity.getShortName())) {
			final Long userId = Long.parseLong(dto.getUser().getId());
			final UserEntity userEntity = userRepository.findOne(userId);

			LocalDate birthDate = new LocalDate_Date_DozerConverter().convertFrom(userEntity.getBirthDate(), null);
			if (ChronoUnit.YEARS.between(birthDate, LocalDate.now()) > 18) {
				throw new AdultTryToCreateLivreJeuneException();
			}
		}
	}
}
