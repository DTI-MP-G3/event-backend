package com.event.event.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import java.time.OffsetDateTime;
//import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;


//    id SERIAL PRIMARY KEY,
//    password VARCHAR(255),
//    name VARCHAR(100),
//    email VARCHAR(150),
//    pin INTEGER,
//    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
//    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
//    deleted_at TIMESTAMP

@Getter
@Setter
@Entity
@Table(name="users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "users_id_gen")
    @SequenceGenerator(name="users_id_gen", sequenceName = "users_id_seq", allocationSize = 1)
    @Column(name="id",nullable = false)
    private Long id;

    @NotNull
    @Column(name = "password", nullable = false, length = 50)
    private String password;

    @Size(max=50)
    @NotNull
    @Column(name= "name", nullable = false,length = 50)
    private String name;

    @Size(max=50)
    @NotNull
    @Column(name= "email", nullable = false,length = 50)
    private String email;


    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    @NotNull
    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "created_at", nullable = false)
    private OffsetDateTime createdAt;

    @NotNull
    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "updated_at", nullable = false)
    private OffsetDateTime updatedAt;

    @Column(name = "deleted_at")
    private OffsetDateTime deletedAt;

    @Column (name= "referral_code")
    private String referralCode;

    @Column(name = "referred_by")
    private Long referredBy;

    @PrePersist
    protected void onCreate() {
        createdAt = OffsetDateTime.now();
        updatedAt = OffsetDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = OffsetDateTime.now();
    }

    @PreRemove
    protected void onRemove() {
        deletedAt = OffsetDateTime.now();
    }
//    private ZonedDateTime createdAt;
//    private ZonedDateTime updatedAt;
//    private ZonedDateTime deletedAt;

}