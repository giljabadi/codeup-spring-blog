package com.codeup.springblog.models;

import javax.persistence.*;
import java.util.List;

public class AdCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String name;

    @ManyToMany(mappedBy = "categories")
    private List<Ad> ads;
}
