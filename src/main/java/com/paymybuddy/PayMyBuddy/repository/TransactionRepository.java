package com.paymybuddy.PayMyBuddy.repository;

import com.paymybuddy.PayMyBuddy.model.Transaction;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends CrudRepository<Transaction, Integer> {

    /**
     * Retourne une liste de transaction à partir de l'id du wallet creditor
     *
     * @param id
     * @return
     */
    @Query(value = "select distinct * from money_transaction where wallet_creditor_id=?1", nativeQuery = true)
    List<Transaction> findAllByWalletCreditorId(int id);


    /**
     * Retourne une liste de transaction à partir de l'id du wallet debtor
     *
     * @param id
     * @return
     */
    @Query(value = "select distinct * from money_transaction where wallet_debtor_id=?1", nativeQuery = true)
    List<Transaction> findAllByWalletDebtorId(int id);
}
