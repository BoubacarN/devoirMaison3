package fr.ekinci.dataaccess.account.repositories;

import fr.ekinci.dataaccess.account.entities.AccountEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

/**
 * @author Gokan EKINCI
 */
public interface AccountRepository extends PagingAndSortingRepository<AccountEntity, Long> {

	@Query("SELECT a FROM AccountEntity a WHERE a.user.id = ?1 AND a.type.id = ?2")
	List<AccountEntity> checkIfAccountExists(Long userId, Long typeId);
}
