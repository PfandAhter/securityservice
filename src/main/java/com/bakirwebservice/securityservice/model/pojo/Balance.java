package com.bakirwebservice.securityservice.model.pojo;

import jakarta.persistence.*;

public class Balance {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "balance_id")
    private String balanceId;

    @Column(name = "username")
    private String username;

    @Column(name = "amount")
    private Long amount;

    //TODO : BURAYI BAGLA DIGERIYLE...

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "money_code")
    private String money_code;
}