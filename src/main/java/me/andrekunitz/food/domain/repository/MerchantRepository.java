package me.andrekunitz.food.domain.repository;

import me.andrekunitz.food.domain.model.Merchant;

import java.util.List;

public interface MerchantRepository {
    List<Merchant> findAll();

    Merchant findById(Long id);

    Merchant save(Merchant merchant);

    void remove(Merchant merchant);
}
