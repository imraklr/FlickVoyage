package com.flickvoyage.dataretrievalservice.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;

/**
 * @author Rakesh Kumar
 */
@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@Table(name = "cast")
public class Cast {
    @JsonIgnore
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cast_id")
    private long castId;

    @JsonIgnore
    @Column(name = "movie_id")
    private long movieId;

    @JsonIgnore
    @Column(name = "department_id")
    private int departmentId;

    @Column(name = "name")
    private String name;

    @Column(name = "character")
    private String character;

    @ToString.Exclude
    @JsonManagedReference
    @ManyToMany(
        cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.DETACH, CascadeType.REFRESH},
        fetch = FetchType.LAZY
    )
    @JoinTable(
        name = "cast_department", // the linking table for the cast and department
        joinColumns = @JoinColumn(name = "cast_id"),
        inverseJoinColumns = @JoinColumn(name = "department_id")
    )
    private List<Department> departmentList;


    // Argument with constructor with 1 String arugment
    public Cast(int departmentId, String name, String character) {
        this.departmentId = departmentId;
        this.name = name;
        this.character = character;
    }
}
