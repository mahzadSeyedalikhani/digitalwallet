package com.digipay.digitalwallet.model.entity;

import com.digipay.digitalwallet.model.TransactionType;
import jakarta.persistence.*;
import lombok.*;
import java.util.Date;


@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@Entity
@Table(name = "WALLET_TRANSACTION")
public class WalletTransaction {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "wallet_transaction_id")
    private String walletTransactionId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "source_wallet")
    private Wallet sourceWallet;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "destination_wallet")
    private Wallet destinationWallet;

    @Enumerated(EnumType.STRING)
    @Column(name = "transaction_type")
    private TransactionType transactionType;

    @Column(name = "transaction_amount")
    private Long transactionAmount;

    @Column(name = "transaction_date")
    private Date transactionDate;

    public WalletTransaction(String walletTransactionId, Wallet sourceWallet,
                             Wallet destinationWallet, TransactionType transactionType,
                             Long transactionAmount, Date transactionDate) {
        this.walletTransactionId = walletTransactionId;
        this.sourceWallet = sourceWallet;
        this.destinationWallet = destinationWallet;
        this.transactionType = transactionType;
        this.transactionAmount = transactionAmount;
        this.transactionDate = transactionDate;
    }

    public WalletTransaction(String walletTransactionId, Wallet sourceWallet,
                             TransactionType transactionType,
                             Long transactionAmount, Date transactionDate) {
        this.walletTransactionId = walletTransactionId;
        this.sourceWallet = sourceWallet;
        this.transactionType = transactionType;
        this.transactionAmount = transactionAmount;
        this.transactionDate = transactionDate;
    }
}
