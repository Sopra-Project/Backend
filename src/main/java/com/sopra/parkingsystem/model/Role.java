package com.sopra.parkingsystem.model;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

@Entity
@Data
@Table(name = "roles")
public class Role implements GrantedAuthority {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private int id;
    @Column(name = "name")
    private String authority;

    protected Role(int id, String authority) {
        this.id = id;
        this.authority = authority;
    }

    public Role() {
    }

    public static final Role USER = new Role(1, "USER");
    public static final Role ADMIN = new Role(2, "ADMIN");
    public static final Role INSPECTOR = new Role(3, "INSPECTOR");

}
