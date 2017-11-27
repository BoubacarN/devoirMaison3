package fr.ekinci.dataaccess.user.controllers;

import fr.ekinci.client.models.user.UserDto;
import fr.ekinci.dataaccess.user.services.UserDAO;
import fr.ekinci.restserver.utils.AbstractController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * @author Gokan EKINCI
 */
@RestController
@RequestMapping(path = "/users")
public class UserController extends AbstractController<UserDto, Long, UserDAO> {
	private final UserDAO userDAO;

	@Autowired
	public UserController(UserDAO userDAO) {
		super(userDAO);
		this.userDAO = userDAO;
	}

	/**
	 * For client-management-service
	 */
	@RequestMapping(path = "/{id}", method = RequestMethod.PATCH)
	public ResponseEntity<?> patch(@PathVariable("id") String id, @RequestBody UserDto user) {
		userDAO.patch(id, user);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	/**
	 * For client-management-service
	 */
	@RequestMapping(path = "/research", method = RequestMethod.GET)
	public ResponseEntity<List<UserDto>> findByName(
		@RequestParam("name") String name
	) {
		final List<UserDto> foundUsers = userDAO.findByName(name, 20);
		return (!foundUsers.isEmpty()) ?
			new ResponseEntity<>(foundUsers, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}
