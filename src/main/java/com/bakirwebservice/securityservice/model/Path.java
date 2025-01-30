package com.bakirwebservice.securityservice.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "path")

public class Path {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "path", unique = true)
    private String path;

    @ManyToMany
    @JoinTable(
            name = "role_paths",
            joinColumns = @JoinColumn(name = "path_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles; //The roles who have permission to access this path...

}
