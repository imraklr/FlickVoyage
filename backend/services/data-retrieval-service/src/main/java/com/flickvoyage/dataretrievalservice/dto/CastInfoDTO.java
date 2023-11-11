package com.flickvoyage.dataretrievalservice.dto;

import java.util.List;

public record CastInfoDTO(
    String name, String character, List<String> departmentList
) {}
