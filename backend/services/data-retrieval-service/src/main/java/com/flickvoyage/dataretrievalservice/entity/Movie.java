package com.flickvoyage.dataretrievalservice.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
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
import jakarta.persistence.OneToMany;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

/**
 * @author Rakesh Kumar
 */
@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@Table(name = "movie")
public class Movie {
    @Id
    @Column(name = "movie_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long movieId;

    @Column(name = "adult")
    private boolean adult;

    @Column(name = "language")
    private String language;

    @Column(name = "title")
    private String title;

    @Column(name = "overview")
    private String overview;

    @Column(name = "popularity")
    private float popularityScore;

    @Column(name = "poster_path")
    private String posterLink;

    @Column(name = "vote_average")
    private float averageVote;

    @Column(name = "vote_count")
    private long totalVoteCount;

    @Column(name = "release_year")
    private int releaseYear;

    @ToString.Exclude
    @JsonManagedReference
    @ManyToMany(cascade = {
            CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH
    },fetch = FetchType.LAZY)
    @JoinTable(
            name = "movie_keyword", // the linking table for keywords
            joinColumns = @JoinColumn(name = "movie_id"),
            inverseJoinColumns = @JoinColumn(name = "keyword_id")
    )
    private List<Keyword> keywordList;

    @ToString.Exclude
    @JsonManagedReference
    @ManyToMany(
        cascade = {
            CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH
    },fetch = FetchType.LAZY)
    @JoinTable(
            name = "movie_genre", // the linking table for genres
            joinColumns = @JoinColumn(name = "movie_id"),
            inverseJoinColumns = @JoinColumn(name = "genre_id")
    )
    private List<Genre> genresList;

    @ToString.Exclude
    @JsonManagedReference
    @OneToMany(
        cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH },
        fetch = FetchType.LAZY // we might need to remove this
    ) // movie table has one-to-many relationship with the casts table
    @JoinColumn(name = "movie_id")
    private List<Cast> castList;

    
    // Constructor with necessary arguments
    public Movie(boolean adult, String language, String title, String overview, float popularityScore,
            String posterLink, float averageVote, long totalVoteCount, int releaseYear) {
        this.adult = adult;
        this.language = language;
        this.title = title;
        this.overview = overview;
        this.popularityScore = popularityScore;
        this.posterLink = posterLink;
        this.averageVote = averageVote;
        this.totalVoteCount = totalVoteCount;
        this.releaseYear = releaseYear;
    }
}
