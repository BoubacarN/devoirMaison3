package fr.ekinci.dataaccess.usertransaction.services;

import fr.ekinci.client.models.account.AccountDto;
import fr.ekinci.client.models.usertransaction.TransactionDto;
import fr.ekinci.client.models.usertransaction.TransactionType;
import fr.ekinci.dataaccess.account.entities.AccountEntity;
import fr.ekinci.dataaccess.account.repositories.AccountRepository;
import fr.ekinci.dataaccess.usertransaction.entities.TransactionEntity;
import fr.ekinci.dataaccess.usertransaction.exceptions.NotAuthorizedAccountTypeException;
import fr.ekinci.dataaccess.usertransaction.repositories.TransactionRepository;
import fr.ekinci.restserver.utils.AbstractDAO;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

/**
 * @author Gokan EKINCI
 */
@Service
public class UserTransactionDAO extends AbstractDAO<TransactionDto, TransactionEntity, Long> {

	private final TransactionRepository transactionRepository;
	private final AccountRepository accountRepository;
	private final List<String> authorizedAccountTypes = Arrays.asList("CPP", "A", "J", "LDD", "LEP");

	@Autowired
	public UserTransactionDAO(
		Mapper dozer,
		TransactionRepository transactionRepository,
		AccountRepository accountRepository
	) {
		super(dozer, TransactionDto.class, TransactionEntity.class, transactionRepository);
		this.transactionRepository = transactionRepository;
		this.accountRepository = accountRepository;
	}

	@Override
	public TransactionDto add(TransactionDto dto) {
		final TransactionDto eagerTransactionDto = super.add(dto);
		eagerTransactionDto.setAccount(dozer.map(
			accountRepository.findOne(Long.parseLong(eagerTransactionDto.getAccount().getId())),
			AccountDto.class
		));
		return eagerTransactionDto;
	}

	@Transactional
	public TransactionDto credit(TransactionDto transactionDto) {
		final Long accountId = Long.parseLong(transactionDto.getAccount().getId());
		final AccountEntity accountEntity = accountRepository.findOne(accountId);
		checkAuthorizedAccountType(accountEntity.getType().getShortName());

		final Long amountAfterTransaction = accountEntity.getAmount() + transactionDto.getAmount();
		accountEntity.setAmount(amountAfterTransaction);
		accountRepository.save(accountEntity);

		transactionDto.setType(TransactionType.CREDIT);
		transactionDto.setDate(LocalDateTime.now());
		transactionDto.setAmountAfterTransaction(amountAfterTransaction);
		return super.add(transactionDto);
	}

	@Transactional
	public TransactionDto debit(TransactionDto transactionDto) {
		checkIfUnder800();

		final Long accountId = Long.parseLong(transactionDto.getAccount().getId());
		final AccountEntity accountEntity = accountRepository.findOne(accountId);
		checkAuthorizedAccountType(accountEntity.getType().getShortName());

		final Long amountAfterTransaction = accountEntity.getAmount() - transactionDto.getAmount();
		accountEntity.setAmount(amountAfterTransaction);
		accountRepository.save(accountEntity);

		transactionDto.setType(TransactionType.DEBIT);
		transactionDto.setDate(LocalDateTime.now());
		transactionDto.setAmountAfterTransaction(amountAfterTransaction);
		return super.add(transactionDto);
	}

	private void checkAuthorizedAccountType(String shortName) {
		if(!authorizedAccountTypes.contains(shortName)) {
			throw new NotAuthorizedAccountTypeException();
		}
	}

	private void checkIfUnder800() {
		// TODO
	}
}
