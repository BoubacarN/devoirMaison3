package fr.ekinci.dataaccess.configurations.dozer.modelconverters;

import fr.ekinci.client.models.account.AccountDto;
import fr.ekinci.client.models.account.AccountTypeDto;
import fr.ekinci.client.models.user.UserDto;
import fr.ekinci.dataaccess.account.entities.AccountEntity;
import fr.ekinci.dataaccess.account.entities.AccountTypeEntity;
import fr.ekinci.dataaccess.user.entities.UserEntity;
import org.dozer.DozerBeanMapper;
import org.dozer.DozerConverter;
import org.dozer.Mapper;
import org.springframework.stereotype.Component;

/**
 * A {@link DozerConverter} class :
 * * Must have default constructor
 * * Cannot be a Spring Component
 *
 * @author Gokan EKINCI
 */
public class AccountDto_AccountEntity_DozerConverter extends DozerConverter<AccountDto, AccountEntity> {

	private static final Mapper basicDozer = new DozerBeanMapper();

	public AccountDto_AccountEntity_DozerConverter() {
		super(AccountDto.class, AccountEntity.class);
	}

	@Override
	public AccountEntity convertTo(AccountDto dto, AccountEntity entity) {
		// Account Id
		final AccountEntity accountEntity = new AccountEntity();
		final String accountId = dto.getId();

		// Case when we add a new account, id is null
		if (accountId != null) {
			accountEntity.setId(Long.parseLong(accountId));
		}

		// User
		if (dto.getUser() != null) {
			final UserEntity userEntity = new UserEntity();
			userEntity.setId(Long.parseLong(dto.getUser().getId()));
			accountEntity.setUser(userEntity);
		}

		// Account Type
		if (dto.getType() != null) {
			final AccountTypeEntity accountTypeEntity = new AccountTypeEntity();
			accountTypeEntity.setId(Long.parseLong(dto.getType().getId()));
			accountEntity.setType(accountTypeEntity);
		}

		// Amount
		accountEntity.setAmount(dto.getAmount());

		return accountEntity;
	}

	@Override
	public AccountDto convertFrom(AccountEntity entity, AccountDto dto) {
		return AccountDto.builder()
			.id(String.valueOf(entity.getId()))
			.user((entity.getUser() != null) ? basicDozer.map(entity.getUser(), UserDto.class) : null)
			.type((entity.getType() != null) ? basicDozer.map(entity.getType(), AccountTypeDto.class) : null)
			.amount(entity.getAmount())
			.build();
	}
}