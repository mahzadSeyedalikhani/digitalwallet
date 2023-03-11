package com.digipay.digitalwallet.model.entity;

import com.digipay.digitalwallet.model.WalletStatus;
import jakarta.persistence.*;
import lombok.*;


@Setter
@Getter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@Entity
@Table(name = "WALLET")
public class Wallet {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "wallet_id")
    private String walletId;

    private String name;

    @Enumerated(EnumType.STRING)
    private WalletStatus status;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user")
    private User user;

    private double balance;

    public Wallet(String walletId, String name, WalletStatus status, double balance) {
        this.walletId = walletId;
        this.name = name;
        this.status = status;
        this.balance = balance;
    }
}
