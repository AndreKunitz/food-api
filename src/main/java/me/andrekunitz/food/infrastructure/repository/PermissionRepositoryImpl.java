package me.andrekunitz.food.infrastructure.repository;

import me.andrekunitz.food.domain.model.Permission;
import me.andrekunitz.food.domain.repository.PermissionRepository;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Component
public class PermissionRepositoryImpl implements PermissionRepository {
	@PersistenceContext
	private EntityManager manager;

	@Override
	public List<Permission> findAll() {
		return manager.createQuery("from Permission", Permission.class)
				.getResultList();
	}

	@Override
	public Permission findById(Long id) {
		return manager.find(Permission.class, id);
	}

	@Override
	public Permission save(Permission permission) {
		return manager.merge(permission);
	}

	@Override
	public void remove(Permission permission) {
		permission = findById(permission.getId());
		manager.remove(permission);
	}
}
