package com.flickvoyage.dataretrievalservice.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

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
@Table(name = "keyword")
public class Keyword {
    @JsonIgnore
    @Id
    @Column(name = "keyword_id")
    private long keywordId;

    @Column(name = "keyword")
    private String keyword;

    @ToString.Exclude
    @JsonBackReference
    @ManyToMany(
        cascade = {
            CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH
    },fetch = FetchType.LAZY)
    @JoinTable(
            name = "movie_keyword", // the linking table for keyword
            joinColumns = @JoinColumn(name = "keyword_id"), // the foreign key reference to the keyword table
            inverseJoinColumns = @JoinColumn(name = "movie_id") // the foreign key reference to the movies table
    )
    private List<Movie> movieList;


    public Keyword(String keyword) {
        this.keyword = keyword;
    }
}
