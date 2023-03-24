package com.paymybuddy.PayMyBuddy.repository;

import com.paymybuddy.PayMyBuddy.model.Wallet;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface WalletRepository extends CrudRepository<Wallet, Integer> {
}
