package com.assemblette.assemblette_backend.dto;

import com.assemblette.assemblette_backend.entity.Ballot;
import com.assemblette.assemblette_backend.entity.Deputy;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VoteDto {
    private Long id;
    private Ballot ballot;
    private Deputy deputy;
    private String state;
}
