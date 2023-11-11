package com.flickvoyage.dataretrievalservice.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * @author Rakesh Kumar
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
@Entity
@Table(name = "department")
public class Department {
    @JsonIgnore
    @Id
    @Column(name = "department_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int departmentId;

    @Column(name = "name")
    private String departmentName;

    @ToString.Exclude
    @JsonBackReference
    @ManyToMany(
        cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.DETACH, CascadeType.REFRESH},
        fetch = FetchType.LAZY
    )
    @JoinTable(
        name = "cast_department", // the linking table for cast and department
        joinColumns = @JoinColumn(name = "department_id"),
        inverseJoinColumns = @JoinColumn(name = "cast_id")
    )
    private List<Cast> castList;

    public Department(String departmentName) {
        this.departmentName = departmentName;
    }
}
