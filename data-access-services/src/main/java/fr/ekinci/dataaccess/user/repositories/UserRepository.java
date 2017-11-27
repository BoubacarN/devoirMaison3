package fr.ekinci.dataaccess.user.repositories;

import fr.ekinci.dataaccess.user.entities.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface UserRepository extends PagingAndSortingRepository<UserEntity, Long> {
	List<UserEntity> findAll();

	@Query("SELECT u FROM UserEntity u JOIN FETCH u.accounts WHERE u.id = ?1")
	UserEntity eagerFetchUserById(Long id);

	@Query("SELECT u FROM UserEntity u WHERE u.lastName LIKE '?1%' OR u.firstName LIKE '?1%'")
	Page<UserEntity> findByName(String name, Pageable pageable);
}
