package fr.ekinci.dataaccess.account.repositories;

import fr.ekinci.dataaccess.account.entities.AccountTypeEntity;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * @author Gokan EKINCI
 */
public interface AccountTypeRepository extends PagingAndSortingRepository<AccountTypeEntity, Long> {
}
