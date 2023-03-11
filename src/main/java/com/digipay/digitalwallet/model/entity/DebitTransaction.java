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
@Table(name = "DEBIT_TRANSTACTION")
public class DebitTransaction {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "debit_transaction_id")
    private String debitTransactionId;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "wallet_transaction")
    private WalletTransaction walletTransaction;

    @Column(name = "transaction_amount")
    private Long transactionAmount;

    @Enumerated(EnumType.STRING)
    @Column(name = "transaction_type")
    private TransactionType transactionType;

    @Column(name = "transaction_date")
    private Date transactionDate;

    private String iban;

    public DebitTransaction(String debitTransactionId, WalletTransaction walletTransaction,
                            Long transactionAmount, TransactionType transactionType,
                            Date transactionDate, String iban) {
        this.debitTransactionId = debitTransactionId;
        this.walletTransaction = walletTransaction;
        this.transactionAmount = transactionAmount;
        this.transactionType = transactionType;
        this.transactionDate = transactionDate;
        this.iban = iban;
    }
}
