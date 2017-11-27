package fr.ekinci.dataaccess.user.services;

import fr.ekinci.dataaccess.user.entities.UserEntity;
import fr.ekinci.client.models.user.UserDto;
import fr.ekinci.dataaccess.user.repositories.UserRepository;
import fr.ekinci.restserver.utils.AbstractDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.dozer.Mapper;

import java.util.List;

@Service
public class UserDAO extends AbstractDAO<UserDto, UserEntity, Long> {

	private final UserRepository userRepository;

	@Autowired
	public UserDAO(Mapper dozer, UserRepository userRepository) {
		super(dozer, UserDto.class, UserEntity.class, userRepository);
		this.userRepository = userRepository;
	}

	/**
	 * We are only updating the user address and phone numbers
	 * @param id		User id
	 * @param user		User content
	 */
	public void patch(String id, UserDto user) {
		final Long entityId = Long.parseLong(id);
		final UserEntity userEntity = repository.findOne(entityId);
		userEntity.setAddress(user.getAddress());
		userEntity.setCellPhone(user.getCellPhone());
		userEntity.setHomePhone(user.getHomePhone());
		repository.save(userEntity);
	}

	/**
	 * Find user by given name
	 *
	 * @param name		User last name or first name
	 * @param size		Size is the results limit for response
	 * @return
	 */
	public List<UserDto> findByName(String name, int size) {
		return userRepository.findByName(name, new PageRequest(0, size))
			.map(u -> dozer.map(u, UserDto.class))
			.getContent();
	}
}
