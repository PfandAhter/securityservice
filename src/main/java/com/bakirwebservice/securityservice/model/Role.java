package com.bakirwebservice.securityservice.model;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "role")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    private String name;

    @ManyToMany
    @JoinTable(
            name = "role_hierarchy",
            joinColumns = @JoinColumn(name = "role_id"),
            inverseJoinColumns = @JoinColumn(name = "parent_role_id")
    )
    private Set<Role> parentRoles;

    @ManyToMany(mappedBy = "parentRoles")
    private Set<Role> childRoles;
}
