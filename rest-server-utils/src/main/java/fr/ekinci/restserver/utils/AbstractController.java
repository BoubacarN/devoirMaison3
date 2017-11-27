package fr.ekinci.restserver.utils;

import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.Serializable;
import java.util.List;
import java.util.Optional;

/**
 * A generic controller that handle the following routes:
 *      GET    resources/id
 *      GET    resources
 *      GET    resources?page={pageIndex}&size={size}
 *      POST   resources
 *      PUT    resources/id
 *      DELETE resources/id
 *
 * @author Gokan EKINCI
 */
public abstract class AbstractController<DTO, ID extends Serializable, ABSTRACT_DAO extends AbstractDAO<DTO, ?, ID>> {
	protected final ABSTRACT_DAO dao;

	public AbstractController(ABSTRACT_DAO dao) {
		this.dao = dao;
	}

	@RequestMapping(path = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<DTO> get(@PathVariable ID id) {
		final Optional<DTO> dtoOpt = dao.get(id);
		return (dtoOpt.isPresent()) ?
			new ResponseEntity<>(dtoOpt.get(), HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	/**
	 * If page and size request parameters are filled, return a page. Otherwise, return a list of all elements.
	 *
	 * @param page      Page index, starts with 0
	 * @param size      Page size
	 * @return          Can return a {@link org.springframework.data.domain.Page} OR a {@link List} of DTO
	 */
	@RequestMapping(path = "", method = RequestMethod.GET)
	public ResponseEntity<?> get(
		@RequestParam(value = "page", required = false) Integer page,
		@RequestParam(value = "size", required = false) Integer size
	) {
		// Pagination
		if (page != null && size != null) {
			final PageRequest pageRequest = new PageRequest(page, size);
			return new ResponseEntity<>(dao.getPagination(pageRequest), HttpStatus.OK);
		}

		final List<DTO> dtoListOpt = dao.getAll();
		return (!dtoListOpt.isEmpty()) ?
			new ResponseEntity<>(dtoListOpt, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@RequestMapping(path = "", method = RequestMethod.POST)
	public ResponseEntity<DTO> create(@Valid @RequestBody DTO dto) {
		return new ResponseEntity<>(dao.add(dto), HttpStatus.OK);
	}

	@RequestMapping(path = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<?> update(@PathVariable ID id, @Valid @RequestBody DTO dto) {
		dao.update(id, dto);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@RequestMapping(path = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<?> delete(@PathVariable ID id) {
		dao.delete(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
