package com.digipay.digitalwallet.model.entity;

import jakarta.persistence.*;
import lombok.*;


@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@Entity
@Table(name = "USER")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "user_id")
    private String userId;

    private String username;

    @Column(unique = true)
    private String nationalCode;

    @Column(unique = true)
    private String iban;

    public User(String userId, String username, String nationalCode, String iban) {
        this.userId = userId;
        this.username = username;
        this.nationalCode = nationalCode;
        this.iban = iban;
    }
}
