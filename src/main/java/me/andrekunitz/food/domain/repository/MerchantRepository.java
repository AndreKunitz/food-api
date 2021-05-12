package me.andrekunitz.food.domain.repository;

import me.andrekunitz.food.domain.model.Merchant;

import java.util.List;

public interface MerchantRepository {
    public List<Merchant> findAll();

    public Merchant findById(Long id);

    public Merchant save(Merchant merchant);

    public void remove(Merchant merchant);
}
