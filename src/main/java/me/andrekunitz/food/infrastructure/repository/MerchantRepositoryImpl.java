package me.andrekunitz.food.infrastructure.repository;

import me.andrekunitz.food.domain.model.Merchant;
import me.andrekunitz.food.domain.repository.MerchantRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Component
public class MerchantRepositoryImpl implements MerchantRepository {
    @PersistenceContext
    private EntityManager manager;

    @Override
    public List<Merchant> findAll() {
        return manager.createQuery("from Merchant", Merchant.class)
                .getResultList();
    }

    @Override
    public Merchant findById(final Long id) {
        return manager.find(Merchant.class, id);
    }

    @Transactional
    @Override
    public Merchant save(final Merchant merchant) {
        return manager.merge(merchant);
    }

    @Transactional
    @Override
    public void remove(Merchant merchant) {
        merchant = findById(merchant.getId());
        manager.remove(merchant);
    }
}
