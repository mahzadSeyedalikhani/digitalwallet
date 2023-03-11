package com.digipay.digitalwallet.repository;

import com.digipay.digitalwallet.model.entity.DebitTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DebitTransactionRepository extends JpaRepository<DebitTransaction, Long> {

}
