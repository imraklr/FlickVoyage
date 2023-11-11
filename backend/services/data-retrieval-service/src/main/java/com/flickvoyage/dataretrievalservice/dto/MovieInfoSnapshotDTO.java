package com.flickvoyage.dataretrievalservice.dto;

import java.util.List;

public record MovieInfoSnapshotDTO(
    long movieId,
    String title,
    String overview,
    String posterLink,
    String language,
    boolean adult,
    double popularityScore,
    double averageVote,
    long totalVoteCount,
    int releaseYear,
    List<String> genreList
) {}