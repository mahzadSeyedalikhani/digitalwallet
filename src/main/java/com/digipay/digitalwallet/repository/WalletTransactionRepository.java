package com.digipay.digitalwallet.repository;

import com.digipay.digitalwallet.model.entity.WalletTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface WalletTransactionRepository extends JpaRepository<WalletTransaction, Long> {

    @Query("from WalletTransaction wtr join wtr.sourceWallet w where w.walletId=?1 order by wtr.transactionDate desc ")
    List<WalletTransaction> findWalletTransactionsBySourceWalletId(String walletId);

}
