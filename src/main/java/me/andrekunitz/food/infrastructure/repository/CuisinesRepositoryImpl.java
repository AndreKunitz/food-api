package me.andrekunitz.food.infrastructure.repository;

import me.andrekunitz.food.domain.model.Cuisine;
import me.andrekunitz.food.domain.repository.CuisinesRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Component
public class CuisinesRepositoryImpl implements CuisinesRepository {
    @PersistenceContext
    private EntityManager manager;

    @Override
    public List<Cuisine> findAll() {
        return manager.createQuery("from Cuisine", Cuisine.class)
                .getResultList();
    }

    @Override
    public Cuisine findById(final Long id) {
        return manager.find(Cuisine.class, id);
    }

    @Transactional
    @Override
    public Cuisine save(final Cuisine cuisine) {
        return manager.merge(cuisine);
    }

    @Transactional
    @Override
    public void remove(Cuisine cuisine) {
        cuisine = findById(cuisine.getId());
        manager.remove(cuisine);
    }
}
