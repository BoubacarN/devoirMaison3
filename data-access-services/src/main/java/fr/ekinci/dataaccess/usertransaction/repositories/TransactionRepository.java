package fr.ekinci.dataaccess.usertransaction.repositories;

import fr.ekinci.dataaccess.usertransaction.entities.TransactionEntity;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * @author Gokan EKINCI
 */
public interface TransactionRepository extends PagingAndSortingRepository<TransactionEntity, Long> {

}
