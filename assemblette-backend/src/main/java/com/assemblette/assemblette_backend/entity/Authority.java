package com.assemblette.assemblette_backend.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "authorities")
@Builder
public class Authority {
    @Id
    private String id;

    @Column(nullable = false)
    private String authorityType;

    @Column(nullable = false)
    private String label;

    @Column(nullable = false)
    private String labelAbbreviated;

    private String associatedColor;
}
