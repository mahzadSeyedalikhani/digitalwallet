package com.digipay.digitalwallet.repository;

import com.digipay.digitalwallet.model.entity.User;
import com.digipay.digitalwallet.model.entity.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface WalletRepository extends JpaRepository<Wallet, Long> {

    Wallet findWalletByWalletId(String walletId);

    List<Wallet> findWalletsByUser(User user);

    Wallet findWalletByUserNationalCodeAndName(String nationalCode, String walletName);
}
