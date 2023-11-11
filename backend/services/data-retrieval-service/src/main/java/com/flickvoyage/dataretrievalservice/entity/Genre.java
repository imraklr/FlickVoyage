package com.flickvoyage.dataretrievalservice.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;

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
@Table(name = "genre")
public class Genre {
    @JsonIgnore
    @Id
    @Column(name = "genre_id")
    private int genreId;

    @Column(name = "genre")
    private String genre;

    @ToString.Exclude
    @JsonBackReference
    @ManyToMany(cascade = {
            CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH
    },fetch = FetchType.LAZY)
    @JoinTable(
            name = "movie_genre", // the linking table for keyword
            joinColumns = @JoinColumn(name = "genre_id"), // the foreign key reference to the keyword table
            inverseJoinColumns = @JoinColumn(name = "movie_id") // the foreign key reference to the movies table
    )
    private List<Movie> movieList;

    
    // Constructor with 1 String argument
    public Genre(String genre) {
        this.genre = genre;
    }
}
