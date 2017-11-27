package fr.ekinci.restserver.utils;

import org.dozer.Mapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * A generic class for transforming Entity classes to DTO
 *
 * @author Gokan EKINCI
 */
public abstract class AbstractDAO<DTO, ENTITY, ENTITY_ID extends Serializable> {
	protected final Mapper dozer;
	protected final Class<DTO> dtoClass;
	protected final Class<ENTITY> entityClass;
	protected final PagingAndSortingRepository<ENTITY, ENTITY_ID> repository;

	protected AbstractDAO(
		Mapper dozer,
		Class<DTO> dtoClass,
		Class<ENTITY> entityClass,
		PagingAndSortingRepository<ENTITY, ENTITY_ID> repository
	) {
		this.dozer = dozer;
		this.dtoClass = dtoClass;
		this.entityClass = entityClass;
		this.repository = repository;
	}

	public Optional<DTO> get(ENTITY_ID id) {
		Objects.nonNull(id);
		final ENTITY entity = repository.findOne(id);
		return (entity == null) ? Optional.empty() : Optional.of(dozer.map(entity, dtoClass));
	}

	public List<DTO> getAll() {
		final List<DTO> result = new ArrayList<>();
		repository.findAll()
			.forEach(sourceEntity -> result.add(dozer.map(sourceEntity, dtoClass)));
		return result;
	}

	public Page<DTO> getPagination(Pageable pageable) {
		Objects.nonNull(pageable);
		return repository.findAll(pageable).map(sourceEntity -> dozer.map(sourceEntity, dtoClass));
	}

	public DTO add(DTO dto) {
		Objects.nonNull(dto);
		return dozer.map(
			repository.save(
				dozer.map(dto, entityClass)
			),
			dtoClass
		);
	}

	public void update(ENTITY_ID id, DTO dto) {
		Objects.nonNull(id);
		Objects.nonNull(dto);
		repository.save(dozer.map(dto, entityClass));
	}

	public void delete(ENTITY_ID id) {
		Objects.nonNull(id);
		repository.delete(id);
	}
}
